import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';

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


const textLinks = (text) => {
    var bracketRegEx = /<(http(?:.*?))>/g;
    return text.replace(bracketRegEx, '<a href = "$1" target = "_blank">$1</a>');
}

const createMarkup = (text) => {
    text = textLinks(text)
    text = formatBracketedText(text);
    return {
        __html: text
    }
}

export default ({ messages = []}) => {
    const columns = [{
        dataField: 'channel',
        text: 'Channel'
    },{
        dataField: 'text',
        text: 'Text',
        formatter: (cell) => <p dangerouslySetInnerHTML = {createMarkup(cell)}></p>
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
