import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import { Button } from "react-bootstrap";
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "../../utils/fetch";

const SlackUsersWithMessageCounts = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: slackUsers } = useSWR(["/api/slackUsersWithMessageCounts", getToken], fetchWithToken);


    const columns = [{
        dataField: 'id',
        text: 'id'
    }, {
        dataField: 'name',
        text: 'Slack Username'
    }, {
        dataField: 'real_name',
        text: 'Name'
    }, {
        dataField: 'profile.email',
        text: 'Email'
    },{
        dataField: 'messageCount',
        text: 'Message Count'
    }];

    return (
        <BootstrapTable keyField='id' data={slackUsers || []} columns={columns} />
    );
};
export default SlackUsersWithMessageCounts;
