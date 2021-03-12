import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
// import { Button } from "react-bootstrap";
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "../../utils/fetch";

const Teams = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: teamNames } = useSWR(["/api/member/teams", getToken], fetchWithToken)
    
    const columns = [{
        dataField: 'teamName',
        text: 'Team'
    },
    {
        dataField: 'id',
        text: 'id'
    },
    {
        dataField: 'teamDescription',
        text: 'Description'
    }];

    return (
        <BootstrapTable keyField='teamName' data={teamNames || []} columns={columns} />
    );
};
export default Teams;
