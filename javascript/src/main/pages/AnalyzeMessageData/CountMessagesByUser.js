import React, {useState, useEffect} from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import { Button } from "react-bootstrap";
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "../../utils/fetch";

const CountMessagesByUser = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: slackUsers } = useSWR(["/api/slackUsers", getToken], fetchWithToken);
    const{ data: messages} = useSWR(["/api/messages", getToken], fetchWithToken);
    useEffect(()=>{
        var i;
        for(i = 0; i<slackUsers.length;i++){
            var count = 0;
            var j;
            for(j=0; j < messages.length; j++) {
                if (messages[i].user_Profile.real_Name === slackUsers[i].real_name){
                    count++;
                }
            }
            slackUsers[i]["messageCount"]=count;
        }
    },[slackUsers, messages]); 

    const columns = [{
        dataField: 'real_name',
        text: 'user'
    } , {
        text: "MessageCount",
        dataField: "messageCount",
    } ];

    return (
        <div>
        <h1>Count Messages By User</h1>
        <BootstrapTable keyField='id' data={slackUsers || []} columns={columns} />
        </div>
    );
};
export default CountMessagesByUser;