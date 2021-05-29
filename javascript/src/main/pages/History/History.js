import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "../../utils/fetch";

const SearchHistory = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: slackUsers } = useSWR(["/api/slackUsers", getToken], fetchWithToken);

    const columns = [{
        dataField: 'id',
        text: 'Google Searches'
    }, {
        dataField: 'profile.email',
        text: 'Search Time'
    }];

    return (
        <BootstrapTable keyField='id' data={slackUsers || []} columns={columns} />
    );
};
export default SearchHistory;
