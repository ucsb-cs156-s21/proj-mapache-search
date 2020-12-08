import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import { Button } from "react-bootstrap";
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "../../utils/fetch";

const CountMessagesByUser = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: slackUsers } = useSWR(["/api/messages", getToken], fetchWithToken);
    console.log(slackUsers);
    const populateMsgCount = (cell,row ) => {
        return (
           0
        )
    }

    const columns = [{
        dataField: 'user_Profile.real_Name',
        text: 'user'
    }, {
        text: "MessageCount",
        isDummyField: true,
        dataField: "msgCount",
        formatter: (cell, row) => populateMsgCount(cell, row)
    }];

    return (
        <div>
        <h1>Count Messages By User</h1>
        <BootstrapTable keyField='id' data={slackUsers || []} columns={columns} />
        </div>
    );
};
export default CountMessagesByUser;