import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "main/utils/fetch";
import ReactDOMServer from "react-dom/server";

const GetUserName = ({userId}) => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const {data: slackUser} = useSWR([`/api/slackUsers/${userId}`, getToken], fetchWithToken);
    let result;
    if(slackUser == null || slackUser.length == 0) {
        result = <p>{userId}</p>;
        return result;
    }
    result = <p>{slackUser[0].real_name}</p>;
    return result;
}

const UserName = (userId) => {
    return (
        <GetUserName userId={userId} />
    );
}

const MessageContents = (text) => {
    return (
        <GetMessageContents text={text} />
    );
}

const GetMessageContents = ({text}) => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const {data: slackUsers} = useSWR([`/api/slackUsers`, getToken], fetchWithToken);
    return (
        <p>
        {text.replaceAll(/<@([A-Z0-9]{11})>/g, (_,userId) => {
        if(slackUsers != null) {
        for(let i = 0; i < slackUsers.length; i++) {
            if(slackUsers[i].id == userId) {
                return slackUsers[i].real_name;
            }
        }
        }
        return userId;
        })}
        </p>
    );
}


export default ({ messages }) => {
    const columns = [{
        isDummyField: true,
        formatter: (cell, row) => UserName(row.user),
        dataField: 'name',
        text: 'Username'
    },{
        isDummyField: true,
        formatter: (cell, row) => MessageContents(row.text),
        dataField: 'text',
        text: 'Contents'
    }
    ];

    return (
        <BootstrapTable keyField='ts' data={messages} columns={columns}/>
    );
};
