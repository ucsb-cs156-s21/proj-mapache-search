import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import TimeFormatter from "./time"
const { SearchBar } = Search;


const filterLinks = (messages) => {
    // get bracketed text (links)
    let link_messages = messages.filter(message => (message.text.includes("<http") && message.text.includes(">")));
    let bracketRegEx = /<http(.*?)>/g;
    let links = [];
    // for each message, for each link in it, strip other text & add to links
    link_messages.forEach(function(element) {
        let found = element.text.match(bracketRegEx);
        found.forEach(function(part) {
            let new_message = { ...element };
            new_message.text = part;
            links.push(new_message);
        })
    });
    return links;
}

const formatBracketedText = (text) => {
    let bracketRegEx = /<(.*?)>/g;
    let found = text.match(bracketRegEx)
    if (found){
        for (let i=0; i<found.length; i++){
            let current = found[i].replace('<', '');
            current = current.replace('>', '');
            if (found[i].includes("|") && (found[i].includes("http"))){                                          // embedded links
                let links = current.split('|');
                text = text.replace(found[i], '<a href = ' + links[0] + ' target = "_blank">' + links[1] +'</a>')
            } else if (found[i].includes("http")){                                                               // unembedded links
                text = text.replace(found[i], '<a href = ' + current + ' target = "_blank">' + current + '</a>')
            }
        }
    }
    return text;
}

const createMarkup = (text) => {
    text = formatBracketedText(text)
    return {
        __html: text
    }
}

function nameFormatter(value, row) {
    value = row.user;
    return row.user_profile? row.user_profile.real_name:value;
}

export default ({ messages }) => {
    const links = filterLinks(messages);
    const columns = [{
        isDummyField: true,
        formatter: nameFormatter,
        dataField: 'name',
        text: 'Username'
    },{
        isDummyField: true,
        formatter: (_cell, row) => <p dangerouslySetInnerHTML = {createMarkup(row.text)}></p>,
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
