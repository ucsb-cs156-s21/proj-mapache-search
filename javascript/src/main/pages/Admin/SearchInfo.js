import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "../../utils/fetch";

const SearchInfo = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: SearchInfo } = useSWR(["/api/searchInfo", getToken], fetchWithToken);

    const columns = [{
        dataField: 'firstName',
        text: 'First Name',
        sort: true
    }, {
        dataField: 'lastName',
        text: 'Last Name',
        sort: true
    }, {
        dataField: 'searchRemain',
        text: 'Search Remaining',
        sort: true
    }, {
        dataField: 'email',
        text: 'Email',
        sort: true
    }, {
        dataField: 'apiToken',
        text: 'Google Search API Token',
        sort: true
    },
    ];

    return (
        <>
            <p>
                <font size = "5">
                    <b>All Google Custom Search API Tokens Reset at 0:00 PST Everyday</b>
                </font>
            </p>
            <BootstrapTable 
                keyField='id' 
                bootstrap4={true} 
                data={SearchInfo || []} 
                columns={columns} />
        </>
    );
};
export default SearchInfo;
