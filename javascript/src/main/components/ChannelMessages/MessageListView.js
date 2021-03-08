import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import {useAuth0} from "@auth0/auth0-react";
import useSWR from "swr";
import {fetchWithToken} from "main/utils/fetch";
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import TimeFormatter from "./time"
const { SearchBar } = Search;

const MessageContents = (text, slackUsers) => {
    return (
        <GetMessageContents text={text} slackUsers={slackUsers} />
    );
}

const GetMessageContents = ({text, slackUsers}) => {
    var length = (slackUsers)? slackUsers.length:0;
    return (
        <p>
            {text.replace(/<@([A-Z0-9]{11})>/g, (_,userId) => {
                for(let i = 0; i < length; i++) {
                    if(slackUsers[i].id === userId) {
                        return "@" + slackUsers[i].real_name;
                    }
                }
                return "@" + userId;
            })}
        </p>
    );
}

function nameFormatter(value, row) {
    value = row.user;
    return row.user_profile? row.user_profile.real_name:value;
}

function timeUserFormatter(value, row) {
    return value + "-" + row.ts;
}

export default ({ messages }) => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const {data: slackUsers} = useSWR([`/api/slackUsers`, getToken], fetchWithToken);
    const columns = [{
        isDummyField: true,
        formatter: nameFormatter,
        dataField: 'name',
        text: 'Username'
    },{
        isDummyField: true,
        formatter: (_cell, row) => MessageContents(row.text, slackUsers),
        dataField: 'text',
        text: 'Contents',
        sort: true
    },{
        dataField: 'ts',
        text: 'Time',
        sort: true,
        formatter: TimeFormatter,
        style: {
            width: "20%"
        }
    },{
        dataField: 'user',
        text: 'User + Time',
        formatter: timeUserFormatter,
        // ideally this would be hidden: true, but codecov is not able
        // to test this column + timeUserFormatter when it is hidden at the moment
        hidden: false
    }
    ];

    return (
        <div style={{textAlign: "left"}}>
            <ToolkitProvider
                keyField="row.user"
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
