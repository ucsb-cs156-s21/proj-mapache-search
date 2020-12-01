import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';

const channelLink=(channelName) => {
    const link = `/member/channels/${channelName}`
    return (

        < a href= {link} > click here </a>
    );

}

export default ({ channels }) => {

    const columns = [{
        dataField: 'name',
        text: 'Name'
    },{
        dataField: 'purpose.value',
        text: 'Purpose'
    },{
        dataField: 'topic.value',
        text: 'Topic'
    },{
        isDummyField: true,
        formatter: (cell, row) => channelLink(row.name),
        dataField: channelLink,
        text: 'Link'
    }
    ];

    return (
        <BootstrapTable keyField='id' data={channels} columns={columns} />
    );
}