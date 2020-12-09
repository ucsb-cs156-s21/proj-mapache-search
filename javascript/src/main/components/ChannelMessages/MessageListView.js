import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import ReactDOMServer from "react-dom/server";
import {useAuth0} from "@auth0/auth0-react";
import useSWR from "swr";
import {fetchWithToken} from "main/utils/fetch";
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import TimeFormatter from "./time"
const { SearchBar } = Search;


const GetUserName = ({userId, slackUsers}) => {
    for(let i = 0; i < slackUsers.length; i++) {
        if(slackUsers[i].id === userId) {
            return <p> {slackUsers[i].real_name} </p>;
        }
    }
    return <p>{userId}</p>;
}

const UserName = (userId, slackUsers) => {
    return (
        <GetUserName userId={userId} slackUsers={slackUsers} />
    );
}

const MessageContents = (text, slackUsers) => {
    return (
        <GetMessageContents text={text} slackUsers={slackUsers} />
    );
}

const GetMessageContents = ({text, slackUsers}) => {
    return (
        <p>
            {text.replace(/<@([A-Z0-9]{11})>/g, (_,userId) => {
                for(let i = 0; i < slackUsers.length; i++) {
                    if(slackUsers[i].id === userId) {
                        return "@" + slackUsers[i].real_name;
                    }
                }
                return "@" + userId;
            })}
        </p>
    );
}

export default ({ messages }) => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const {data: slackUsers} = useSWR([`/api/slackUsers`, getToken], fetchWithToken);
    const columns = [{
        isDummyField: true,
        formatter: (cell, row) => UserName(row.user, slackUsers),
        dataField: 'name',
        text: 'Username'
    },{
        isDummyField: true,
        formatter: (cell, row) => MessageContents(row.text, slackUsers),
        dataField: 'text',
        text: 'Contents',
        sort: true
    },
        {
            dataField: 'ts',
            text: 'Time',
            sort: true,
            formatter: TimeFormatter,
            style: {
                width: "20%"
            }
        }
    ];

    return (
        <div style={{textAlign: "left"}}>
            <ToolkitProvider
                keyField="ts"
                data={ messages }
                columns={ columns }
                search
                button
            >
                {
                    props => (
                        <div>
                            <SearchBar { ...props.searchProps } />
                            <hr />
                            <BootstrapTable { ...props.baseProps } />
                        </div>
                    )
                }
            </ToolkitProvider>
        </div>
    );
};
