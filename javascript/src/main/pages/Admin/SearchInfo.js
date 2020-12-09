import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import { Button } from "react-bootstrap";
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "../../utils/fetch";

const AppUsers = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: AppUsers } = useSWR(["/api/searchInfo", getToken], fetchWithToken);

    const columns = [{
        dataField: 'firstName',
        text: 'First Name'
    }, {
        dataField: 'lastName',
        text: 'Last Name'
    }, {
        dataField: 'searchRemain',
        text: 'Search Remaining'
    }, {
        dataField: 'email',
        text: 'Email'
    }];

    return (
        <>
            <p>
                <font size = "5">
                    <b>All Google Custom Search API Token Reset at 0:00 PST Everyday</b>
                </font>
            </p>
            <BootstrapTable keyField='id' data={AppUsers || []} columns={columns} />
        </>
    );
};
export default AppUsers;
