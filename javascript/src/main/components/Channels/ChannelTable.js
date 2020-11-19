import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';

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
    }
    ];

    return (
        <BootstrapTable keyField='id' data={channels} columns={columns} />
    );
}