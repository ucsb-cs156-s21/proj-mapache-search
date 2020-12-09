import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';

export default ({ messages }) => {
    const columns = [{
        dataField: 'user',
        text: 'User'
    },{
        dataField: 'text',
        text: 'Contents'
    }
    ];

    return (
        <BootstrapTable keyField='ts' data={messages} columns={columns}/>
    );
};