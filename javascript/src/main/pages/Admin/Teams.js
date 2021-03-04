import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
// import { Button } from "react-bootstrap";
import Table from 'react-bootstrap/Table'
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "../../utils/fetch";

const Teams = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: slackUsers } = useSWR(["/api/slackUsers", getToken], fetchWithToken);
    

    // TODO: update with information from the CSV file, NOT from slackUsers
    const data = [];
    const columns = [{
        dataField: 'name',
        text: 'Name'
    }, {
        dataField: 'teamName',
        text: 'Team'
    }];

    
    return (
        <BootstrapTable keyField='name' data={slackUsers || []} columns={columns} />
    );
};
export default Teams;
