import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import {useAuth0} from "@auth0/auth0-react";
import useSWR from "swr";
import {fetchWithToken} from "main/utils/fetch";
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import TimeFormatter from "./time"
import MessageTableReaction from "../ChannelMessageReaction/MessageTableReaction";
const { SearchBar } = Search;

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

const filterLinks = (messages) => {
    // get bracketed text (links)
    var link_messages = messages.filter(message => (message.text.includes("<") && message.text.includes(">")));
    var bracketRegEx = /<(.*?)>/g;
    var links = [];
    var found;
    var new_message;
    // for each message, for each link in it, strip other text & add to links
    link_messages.forEach(function(element) {
        found = element.text.match(bracketRegEx);
        found.forEach(function(part, index) {
            new_message = { ...element };
            new_message.text = found[index];
            console.log(new_message.text);
            links.push(new_message);
        })
    });
    return links;
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
            } else if (found[i].includes("http") || found[i].includes("mailto") || found[i].includes("tel")){                            // unembedded links
                text = text.replace(found[i], '<a href = ' + current + ' target = "_blank">' + current + '</a>')
            } else if (found[i].includes("|")){                                                                                          // channel links
                links = current.split('|');
                text = text.replace(found[i], '<a href = /member/listViewChannels/' + links[1] + '>#' + links[1] + '</a>')
            } else if (found[i].includes("!")){                                                                                          // channel tags (ex: @channel)
                text = text.replace(found[i], '<strong> @' + current + '</strong>')
                text = text.replace("!", "")
            } else{
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

function timeUserFormatter(value, row) {
    return value + "-" + row.ts;
}

export default ({ messages }) => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const {data: slackUsers} = useSWR([`/api/slackUsers`, getToken], fetchWithToken);
    const links = filterLinks(messages);
    
    const columns = [{
        isDummyField: true,
        formatter: nameFormatter,
        dataField: 'name',
        text: 'Username'
    },{
        isDummyField: true,
        formatter: (_cell, row) => <p dangerouslySetInnerHTML = {createMarkup(row.text, slackUsers)}></p>,
        dataField: 'text',
        text: 'URL',
        sort: true
    },{
        dataField: 'ts',
        text: 'Time',
        sort: true,
        formatter: TimeFormatter,
        style: {
            width: "20%"
        }
    }
    ];

    return (
        <div style={{textAlign: "left"}}>
            <ToolkitProvider
                keyField="row.user"
                data={ links }
                columns={ columns }
                search={ { searchFormatted: true } }
                button
            >
                {
                    props => (
                        <div>
                            <SearchBar { ...props.searchProps } />
                            <hr />
                            <BootstrapTable { ...props.baseProps } />
                        </div>
                    )
                }
            </ToolkitProvider>
        </div>
    );
};
