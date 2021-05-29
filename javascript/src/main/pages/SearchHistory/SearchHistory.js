import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "main/utils/fetch";

const SearchHistory = () => {
    const columns = [{
        dataField: 'count',
        text: 'count',
        sort: true
    } , {
        dataField: "searchTerm",
        text: 'Searchquery',
        sort: true
    }];

    const { getAccessTokenSilently: getToken } = useAuth0();
    
    const { data: usersearch,error } = useSWR(["/api/members/search/return", getToken], fetchWithToken);
    
    if (error) return <div>failed to load</div>
    return (
        <div>
            <h1>Show Search History</h1>
            <BootstrapTable keyField='id' data={usersearch} columns={columns} />
        </div>
    );
};
export default SearchHistory;
