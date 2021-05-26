import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';


const textLinks = (text) => {
    var bracketRegEx = /<(http(?:.*?))>/g;
    return text.replace(bracketRegEx, '<a href = "$1" target = "_blank">$1</a>');
}

const createMarkup = (text) => {
    text = textLinks(text)
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
