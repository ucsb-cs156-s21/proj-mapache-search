import React, { useState } from "react";
import useSWR from "swr";
import { Jumbotron } from "react-bootstrap";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "../../utils/fetch";
import BootstrapTable from 'react-bootstrap-table-next';
import Select from 'react-select';

const columns = [{
    dataField: 'id',
    text: 'Id'
}, {
    dataField: 'type',
    text: 'Type'
}, {
    dataField: 'subtype',
    text: 'Subtype'
}, {
    dataField: 'ts',
    text: 'Timestamp'
}, {
    dataField: 'user',
    text: 'User'
}, {
    dataField: 'text',
    text: 'Text'
}, {
    dataField: 'channel',
    text: 'Channel'
}
];

const HistogramOfMessagesByUser = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const [searchUser, setSearchUser] = useState("");
    const { data: slackUsers } = useSWR(["/api/slackUsers", getToken], fetchWithToken);
    const { data: messageForUser } = useSWR([`/api/members/messages?user=U017218J9B3`, getToken], fetchWithToken);

    if (!slackUsers) return <div>Loading...</div>

    const options = slackUsers.map(i => {
        return {
            value: i.id,
            label: i.profile.display_name
        }
    });

    return <Jumbotron>
        <h1>Histogram Of Messages By User</h1>
        <h3>Select a user to view: </h3>
        <Select
            options={options}
            value={searchUser}
            onChange={value => setSearchUser(value)}
            isSearchable
        />
        <button onClick={() => console.log(searchUser.value)}>User selected</button>
        <button onClick={() => console.log(messageForUser)}>MessageForUser</button>
        <BootstrapTable keyField='id' data={messageForUser || []} columns={columns} />
    </Jumbotron>

};

export default HistogramOfMessagesByUser;
