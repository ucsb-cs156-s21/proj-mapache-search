import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "main/utils/fetch";
import aggregateUserMessageCount from "main/utils/aggregateUserMessageCount";

const CountMessagesByUser = () => {
    const columns = [{
        dataField: 'name',
        text: 'User',
        sort: true
    } , {
        dataField: "count",
        text: 'Message Count',
        sort: true
    }];

    const { getAccessTokenSilently: getToken } = useAuth0();
    
    const { data: slackUsers } = useSWR(["/api/slackUsers", getToken], fetchWithToken);
    const { data: messages } = useSWR(["/api/members/messages/allmessages", getToken], fetchWithToken);

    const userMessageCount = messages && slackUsers ? aggregateUserMessageCount(messages, slackUsers) : []

    return (
        <div>
            <h1>Count Messages By User</h1>
            <BootstrapTable keyField='name' data={userMessageCount} columns={columns} />
        </div>
    );
};
export default CountMessagesByUser;
