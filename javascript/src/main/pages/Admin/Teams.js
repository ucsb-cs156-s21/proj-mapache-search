import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import { Button } from "react-bootstrap";
import Table from 'react-bootstrap/Table'
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "../../utils/fetch";

const Teams = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    //const { data: slackUsers } = useSWR(["/api/slackUsers", getToken], fetchWithToken);
    
/*    const { data: slackUsers };
*/    // TODO: update with information from the CSV file, NOT from slackUsers
    /*const columns = [{
        dataField: 'name',
        text: 'Name'
    }, {
        dataField: 'team',
        text: 'Team'    
    }];*/

    
    return (
        //<BootstrapTable keyField='id' data={slackUsers || []} columns={columns} />
        <Table striped bordered hover>
            <thead>
                <tr>
                    <th>Name</th>
                    <th>Team</th>
                </tr>
            </thead>
            <tbody>
                <tr>
                    <td>PlaceHolder1</td>
                    <td>TeamPlaceHolder</td>
                </tr>
                <tr>
                    <td>PlaceHolder2</td>
                    <td>TeamPlaceHolder</td>
                </tr>
            </tbody>
        </Table>
    );
};
export default Teams;
