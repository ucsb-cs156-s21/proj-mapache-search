import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import {useAuth0} from "@auth0/auth0-react";
import useSWR from "swr";
import {fetchWithToken} from "main/utils/fetch";

const GetMessageContents = (text, slackUsers) => {
    return text.replace(/<@([A-Z0-9]{11})>/g, (_,userId) => {
        if(slackUsers != null) {
            for(let i = 0; i < slackUsers.length; i++) {
                if(slackUsers[i].id === userId) {
                    return "@" + slackUsers[i].real_name;
                }
            }
        }
        return "@" + userId;
    })
}

const formatBracketedText = (text) => {
    let bracketRegEx = /<(.*?)>/g;
    let found = text.match(bracketRegEx)
    if (found){
        for (let i=0; i<found.length; i++){
            let current = found[i].replace('<', '');
            current = current.replace('>', '');
            if (found[i].includes("|") && (found[i].includes("mailto") || found[i].includes("http") || found[i].includes("tel"))){      // embedded links
                let links = current.split('|');
                text = text.replace(found[i], '<a href = ' + links[0] + ' target = "_blank">' + links[1] +'</a>')
            }else if (found[i].includes("http") || found[i].includes("mailto") || found[i].includes("tel")){                            // unembedded links
                text = text.replace(found[i], '<a href = ' + current + ' target = "_blank">' + current + '</a>')
            }else if (found[i].includes("|")){                                                                                          // channel links
                let links = current.split('|');
                text = text.replace(found[i], '<a href = /member/listViewChannels/' + links[1] + '>#' + links[1] + '</a>')
            }else if (found[i].includes("!")){                                                                                          // channel tags (ex: @channel)
                text = text.replace(found[i], '<strong> @' + current + '</strong>')
                text = text.replace("!", "")
            }else{
                text = text.replace(found[i], current)
            }
        }
    }
    return text;
}

const createMarkup = (text, slackUsers) => {
    text = GetMessageContents(text, slackUsers)
    text = formatBracketedText(text)
    return {
        __html: text
    }
}

function nameFormatter(value, row) {
    value = row.user;
    return row.user_profile? row.user_profile.real_name:value;
}

export default ({ messages=[],reaction }) => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const {data: slackUsers} = useSWR([`/api/slackUsers`, getToken], fetchWithToken);
    
    function subjectFormatter(_cell, row, _rowIndex) {
        const correctReaction = row.message_reactions.find((message_reaction) => {
            return message_reaction.name === reaction;
        });
        return correctReaction.count;
    }
    const columns = [{
        dataField: 'user',
        formatter: nameFormatter,
        text: 'Message Author'
    },{
        dataField: 'text',
        formatter: (_cell, row) => <p dangerouslySetInnerHTML = {createMarkup(row.text, slackUsers)}></p>,
        text: 'Contents'
    },{
        dataField: 'reactions',
        formatter: subjectFormatter,
        text: 'Count'
    }
    ];

    return (
        
        <BootstrapTable 
            bootstrap4={true}
            keyField='id' 
            data={messages} 
            columns={columns} 
        />
    )
};
