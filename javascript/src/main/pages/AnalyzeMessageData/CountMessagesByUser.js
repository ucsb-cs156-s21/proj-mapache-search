import React, {useState, useEffect} from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import { Button } from "react-bootstrap";
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "../../utils/fetch";

const CountMessagesByUser = () => {
    const columns = [{
        dataField: 'name',
        text: 'user'
    } , {
        dataField: "count",
        text: 'Message Count'
    }];
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: slackUsers } = useSWR(["/api/slackUsers", getToken], fetchWithToken);
    const{ data: messages} = useSWR(["/api/members/messages/allmessages", getToken], fetchWithToken);

    const aggregateUserMessageCount = (agg_messages, agg_slackUsers) => {
        const userMessageCounts = [];

        var i;
        for(i = 0; i<agg_slackUsers.length;i++){
            var count = 0;
            var j;
            for(j=0; j < agg_messages.length; j++) {
                if (agg_messages[j].user_profile !== null && agg_messages[j].user_profile.real_name === agg_slackUsers[i].real_name){
                    count++;
                }
            }
            const userCountPair = {
                name: agg_slackUsers[i].real_name,
                count: count.toString()
            }
            userMessageCounts.push(userCountPair);
        }
        return userMessageCounts;
    }

    const userMessageCount = messages && slackUsers ? aggregateUserMessageCount(messages, slackUsers) : []
    
    return (
        <div>
        <h1>Count Messages By User</h1>
        <BootstrapTable keyField='id' data={userMessageCount} columns={columns} />
        </div>
    );
};
export default CountMessagesByUser;