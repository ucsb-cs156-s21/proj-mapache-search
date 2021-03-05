import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
// import useSWR from "swr";
// import {useAuth0} from "@auth0/auth0-react";
// import {fetchWithToken} from "../../utils/fetch";

const SearchedTerms = () => {
    
    console.log(SearchedTerms);
    const columns = [{
        dataField: 'term',
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
            <p>
                <font size = "5">
                    <b>Searched Terms</b>
                </font>
            </p>
            <BootstrapTable keyField='id' data={[]} columns={columns} />
        </>
    );
};
export default SearchedTerms;
