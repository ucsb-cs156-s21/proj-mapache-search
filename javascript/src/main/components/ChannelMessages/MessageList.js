import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "main/utils/fetch";

const userName = (message) => {
    let userProfile = message.user_profile;
    let result = "";
    if(userProfile == null) {
        result += message.user;
    } else {
        result += userProfile.real_name;
    }
    return(
        <p>{result}</p>
    );
}

export default ({ messages }) => {
    const columns = [{
        isDummyField: true,
        formatter: (cell, row) => userName(row),
        dataField: 'name',
        text: 'Username'
    },{
        dataField: 'text',
        text: 'Contents'
    }
    ];

    return (
        <BootstrapTable keyField='ts' data={messages} columns={columns}/>
    );
};
