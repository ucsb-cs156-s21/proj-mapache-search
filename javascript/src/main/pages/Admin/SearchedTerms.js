import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "../../utils/fetch";

const SearchedTerms = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: searchedTerms } = useSWR(["/api/searchedTerms", getToken], fetchWithToken);

    console.log(SearchedTerms);
    const columns = [{
        dataField: 'searchTerm',
        text: 'Term',
        sort:true
    }, {
        dataField: 'id',
        text: 'ID',
        sort:true
    }, {
        dataField: 'count',
        text: 'Count',
        sort:true
    }];

    return (
        <>
             <h2>Searched Terms</h2>
            <BootstrapTable keyField='id' data={searchedTerms || []} columns={columns} />
        </>
    );
};
export default SearchedTerms;
