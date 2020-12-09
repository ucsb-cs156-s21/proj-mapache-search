import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';

const userName = (userId, users) => {
    let result = ""
    for(let i = 0; i < users.length; i++) {
        if(users[i].id == userId) {
            result = users[i].real_name;
        }
    }
    if(result === "") {
        result = userId;
    }
    return <p> {result}</p>;
}
const messageContents = (text, users) => {
    return (
        <p>
        {text.replace(/<@([A-Z0-9]{11})>/g, (_,userId) => {
        for(let i = 0; i < users.length; i++) {
            if(users[i].id == userId) {
                return users[i].real_name;
            }
        }
        return userId;
        })}
        </p>
    );
}


export default ({ messages, users}) => {
    const columns = [{
        isDummyField: true,
        formatter: (cell, row) => userName(row.user,users),
        dataField: 'name',
        text: 'Username'
    },{
        isDummyField: true,
        formatter: (cell, row) => messageContents(row.text, users),
        dataField: 'text',
        text: 'Contents'
    }
    ];

    return (
        <BootstrapTable keyField='ts' data={messages} columns={columns}/>
    );
};
