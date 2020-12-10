import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';

export default ({ messages = [] }) => {
    const columns = [{
        dataField: 'channel',
        text: 'Channel'
    },{
        dataField: 'text',
        text: 'Text'
    }
    ];

    return (
        <BootstrapTable keyField='ts' data={messages} columns={columns}/>
    );
};
