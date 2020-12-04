import React from "react";
import { Button } from "react-bootstrap";
import BootstrapTable from 'react-bootstrap-table-next';

const StudentTable = ({students,deleteStudent}) => {

    const renderDeleteButton = (id) => {
        return (
            <Button variant="danger" data-testid="delete-button" onClick={() => deleteStudent(id)}>Delete</Button>
        )
    }
    
    const columns = [{
        dataField: 'id',
        text: 'id'
    }, {
        dataField: 'email',
        text: 'Email'
    }, {
        dataField: 'teamName',
        text: 'Team Name'
    }];
    columns.push({
        text: "Delete",
        isDummyField: true,
        dataField: "delete",
        formatter: (cell, row) => renderDeleteButton(row.id)
    });
    return (
        <BootstrapTable keyField='id' data={students || []} columns={columns} />
    );
};
export default StudentTable;