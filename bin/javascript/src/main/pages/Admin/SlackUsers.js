import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "../../utils/fetch";

const SlackUsers = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: slackUsers } = useSWR(["/api/slackUsers", getToken], fetchWithToken);

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
    }];

    return (
        <BootstrapTable keyField='id' data={slackUsers || []} columns={columns} />
    );
};
export default SlackUsers;
