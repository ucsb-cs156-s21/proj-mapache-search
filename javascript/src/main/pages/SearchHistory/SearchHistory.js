import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import {Redirect} from 'react-router-dom';
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "main/utils/fetch";

const SearchHistory = () => {
    const columns = [{
        dataField: 'userID',
        text: 'UserID',
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
    
    const { data: usersearch } = useSWR(["/api/members/searchhistory/allusersearches", getToken], fetchWithToken);
    
     const { data: roleInfo } = useSWR(
        ["/api/myRole", getToken],
        fetchWithToken
      );
      const isAdmin = roleInfo && roleInfo.role.toLowerCase() === "admin";
      const isMember = roleInfo && roleInfo.role.toLowerCase() === "member";
   
    if(isMember){
         return (
            <div>
                <h1>Show Search History</h1>
                <Link to="/member/searchhistory/alluser" className="btn btn-primary">ViewAllUsers</Link>

                
        <BootstrapTable keyField='id' data={usersearch || []} columns={columns} />
            </div>
        );
    }
    return (
        <div>
            <h1>Show Search History</h1>
            <BootstrapTable keyField='id' data={usersearch || []} columns={columns} />
        </div>
    );
};
export default SearchHistory;
