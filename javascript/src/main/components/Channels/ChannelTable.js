import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';

const channelLink=(channelName) => {
    const link = `/member/channels/${channelName}`
    return (

        < a href= {link} > click here </a>
    );

}


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


export default ({ channels }) => {

    const sortCaret = (order, _column) => {
        const ascendingON = String.fromCharCode(0x25b2);
        const descendingON = String.fromCharCode(0x25bc);
        const ascendingOFF = String.fromCharCode(0x25b3);
        const descendingOFF = String.fromCharCode(0x25bd);

        if (!order) 
            return (<span data-testid="sort">{descendingOFF}{ascendingOFF}</span>);
        else if (order === 'asc') 
            return (<span data-testid="sort-asc">{descendingOFF}<font color="red">{ascendingON}</font></span>);
        else 
            return (<span data-testid="sort-desc"><font color="red">{descendingON}</font>{ascendingOFF}</span>);
        
    }

    const columns = [{
        dataField: 'name',
        text: 'Name',
        sort: true,
        sortCaret: sortCaret
    },{
        dataField: 'purpose.value',
        text: 'Purpose',
        formatter: (cell) => <p dangerouslySetInnerHTML = {createMarkup(cell)}></p>,
        sort: true,
        sortCaret: sortCaret
    },{
        dataField: 'topic.value',
        text: 'Topic',
        formatter: (cell) => <p dangerouslySetInnerHTML = {createMarkup(cell)}></p>,
        sort: true,
        sortCaret: sortCaret
    },{
        isDummyField: true,
        formatter: (_cell, row) => channelLink(row.name),
        dataField: 'channelLink',
        text: 'Link'
    }
    ];

    return (
        <BootstrapTable keyField='id' data={channels} columns={columns} />
    );
};
