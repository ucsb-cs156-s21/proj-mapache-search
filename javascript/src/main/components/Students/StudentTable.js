import React from "react";
import { Button } from "react-bootstrap";
import BootstrapTable from 'react-bootstrap-table-next';
import { useHistory } from "react-router-dom";

const StudentTable = ({students,deleteStudent}) => {
    const history = useHistory();

    const renderDeleteButton = (id) => {
        return (
            <Button variant="danger" data-testid="delete-button" onClick={() => deleteStudent(id)}>Delete</Button>
        )
    }

    const renderUpdateButton = (id) => {
        return (
            <Button data-testid="edit-button" onClick={() => { history.push(`/students/edit/${id}`) }}>Edit</Button>
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
        text: "Edit",
        isDummyField: true,
        dataField: "edit",
        formatter: (cell, row) => renderUpdateButton(row.id)
    });
    columns.push({
        text: "Delete",
        isDummyField: true,
        dataField: "delete",
        formatter: (cell, row) => renderDeleteButton(row.id)
    });
    return (
        <BootstrapTable keyField='id' data={students} columns={columns} />
    );
};
export default StudentTable;