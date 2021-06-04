import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "main/utils/fetch";

const AllUser = () => {
    const columns = [{
        dataField: 'email',
        text: 'Email',
        sort: true
    } , {
        dataField: 'searchTerm',
        text: 'Search query',
        sort: true
    },{
        dataField: 'timestamp' ,
        text: 'Timestamp' ,
        sort: true
    }];

    const { getAccessTokenSilently: getToken } = useAuth0();
    
    const { data: usersearch } = useSWR(["/api/members/searchhistory/allusersearches", getToken], fetchWithToken);
    
    return (
        <div>
            <h1>Show Search History of All Users</h1>
            <BootstrapTable keyField='id' data={usersearch || []} columns={columns} />
        </div>
    );
};
export default AllUser;
