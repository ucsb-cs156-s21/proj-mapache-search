import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import { Link } from 'react-router-dom';
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "main/utils/fetch";

const SearchHistory = () => {
    const columns = [{
        dataField: 'email',
        text: 'Email',
        sort: true
    } , {
        dataField: 'searchTerm',
        text: 'Searchquery',
        sort: true
    },{
        dataField: 'timestamp' ,
        text: 'Timestamp' ,
        sort: true
    }];
    
 
        
    const { getAccessTokenSilently: getToken } = useAuth0();
    
    const { data: usersearch } = useSWR(["/api/members/searchhistory/specificuser", getToken], fetchWithToken);
    
     const { data: roleInfo } = useSWR(
        ["/api/myRole", getToken],
        fetchWithToken
      );
      const isAdmin = roleInfo && roleInfo.role.toLowerCase() === "admin";
      
    
    return (
        <div>
            <h1>Show Search History</h1>
        {isAdmin&&
            <Link to="/member/searchhistory/alluser" className="btn btn-primary">View All Users</Link>}
            <BootstrapTable keyField='id' data={usersearch || []} columns={columns} />
        </div>
    );
};
export default SearchHistory;
