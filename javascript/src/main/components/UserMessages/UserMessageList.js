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
        <BootstrapTable 
   bootstrap4={true}
   keyField='id' 
   data={messages} 
   columns={columns} 
/>
    );
};
