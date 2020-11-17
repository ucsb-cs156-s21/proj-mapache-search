import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';

export default ({channels}) => {
   
    const columns = [{
        dataField: 'id',
        text: 'id'
    }, {
        dataField: 'name',
        text: 'name'
    }];
    
    return (
        <BootstrapTable keyField='id' data={channels} columns={columns} />
    );
}