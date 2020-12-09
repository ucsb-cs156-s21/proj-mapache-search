import React, {useState, useEffect} from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import { Button } from "react-bootstrap";
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "../../utils/fetch";

const CountMessagesByUser = () => {
    const [columns, setColumns] = useState([{
        dataField: 'real_name',
        text: 'user'
    } , {
        text: 'Message Count'
    } ]);
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: slackUsers } = useSWR(["/api/slackUsers", getToken], fetchWithToken);
    const{ data: messages} = useSWR(["/api/members/messages/allmessages", getToken], fetchWithToken);
    useEffect(()=>{
        if(typeof messages !== 'undefined' && typeof slackUsers !=='undefined'){
            var i;
            for(i = 0; i<slackUsers.length;i++){
                var count = 0;
                var j;
                for(j=0; j < messages.length; j++) {
                    if (messages[j].user_profile !== null && messages[j].user_profile.real_name === slackUsers[i].real_name){
                        count++;
                    }
                }
                slackUsers[i]["messageCount"]=count.toString();
            }
            console.log(slackUsers);
            setColumns([{
                dataField: 'real_name',
                text: 'user'
            } , {
                dataField: 'messageCount',
                text: 'Message Count'
            } ]);
        }


    },[messages]);
    

    return (
        <div>
        <h1>Count Messages By User</h1>
        <BootstrapTable keyField='id' data={slackUsers || []} columns={columns} />
        </div>
    );
};
export default CountMessagesByUser;