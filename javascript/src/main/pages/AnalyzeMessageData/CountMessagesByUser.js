import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "main/utils/fetch";
import aggregateUserMessageCount from "main/utils/aggregateUserMessageCount";

const CountMessagesByUser = () => {
    const columns = [{
        dataField: 'name',
        text: 'user'
    } , {
        dataField: "count",
        text: 'Message Count'
    }];
    
    const { getAccessTokenSilently: getToken } = useAuth0();
    
    const { data: slackUsers } = useSWR(["/api/slackUsers", getToken], fetchWithToken);
    const { data: messages } = useSWR(["/api/members/messages/allmessages", getToken], fetchWithToken);


    
    console.log("slackUsers: ");
    console.log(slackUsers);
    console.log("messages: ");
    console.log(messages);

    const userMessageCount = messages && slackUsers ? aggregateUserMessageCount(messages, slackUsers) : []

    console.log(`usermessagecount: ${userMessageCount}`)
    

    return (
        <div>
            <h1>Count Messages By User</h1>
            <BootstrapTable keyField='id' data={userMessageCount} columns={columns} />
        </div>
    );
};
export default CountMessagesByUser;
