import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import {useAuth0} from "@auth0/auth0-react";
import useSWR from "swr";
import {fetchWithToken} from "../../utils/fetch";

const replaceMessage = (text, slackUsers) => {
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
    var bracketRegEx = /<(.*?)>/g;
    var found = text.match(bracketRegEx)
    if (found){
        for (let i=0; i<found.length; i++){
            var current = found[i].replace('<', '');
            current = current.replace('>', '');
            if (found[i].includes("|") && (found[i].includes("mailto") || found[i].includes("http") || found[i].includes("tel"))){      // embedded links
                var links = current.split('|');
                text = text.replace(found[i], '<a href = ' + links[0] + ' target = "_blank">' + links[1] +'</a>')
            }else if (found[i].includes("http") || found[i].includes("mailto") || found[i].includes("tel")){                            // unembedded links
                text = text.replace(found[i], '<a href = ' + current + ' target = "_blank">' + current + '</a>')
            }else if (found[i].includes("|")){                                                                                          // channel links
                links = current.split('|');
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
    text = replaceMessage(text, slackUsers);
    text = formatBracketedText(text);
    return {
        __html: text
    }
}

export default ({ messages = []}) => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const {data: slackUsers} = useSWR([`/api/slackUsers`, getToken], fetchWithToken);
    const columns = [{
        dataField: 'channel',
        text: 'Channel'
    },{
        dataField: 'text',
        text: 'Text',
        formatter: (_cell, row) => <p dangerouslySetInnerHTML = {createMarkup(row.text, slackUsers)}></p>
    }
    ];

    return (
        <BootstrapTable 
            bootstrap4={true}
            keyField='id' 
            data={messages} 
            columns={columns} 
            
        />

    );
};
