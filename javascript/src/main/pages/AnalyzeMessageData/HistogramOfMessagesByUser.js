import React, { useState, useEffect } from "react";
import Select from "react-select";
import useSWR from "swr";
import { fetchWithToken } from "../../utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import { XYPlot, VerticalBarSeries, XAxis, YAxis } from 'react-vis';
import BootstrapTable, { TableHeaderColumn } from 'react-bootstrap-table-next';
import { Button } from "react-bootstrap";

const data = [
    { x: 0, y: 8 },
    { x: 1, y: 5 },
    { x: 2, y: 4 },
    { x: 3, y: 9 },
    { x: 4, y: 1 },
    { x: 5, y: 7 },
    { x: 6, y: 6 },
    { x: 7, y: 3 },
    { x: 8, y: 2 },
    { x: 9, y: 0 }
];

const columns = [{
    dataField: 'text',
    text: 'Text',
}, {
    dataField: 'ts',
    text: 'Timestamp'
}
];

const HistogramOfMessagesByUser = () => {
    const [selectedUser, setSelectedUser] = useState('');
    const [userMessages, setUserMessages] = useState([]);
    const [displayHistogram, setDisplayHistogram] = useState(false);
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: slackUsers } = useSWR(["/api/slackUsers", getToken], fetchWithToken);
    const userOptions = [];
    const modifiedHistogramData = [];

    const handleSelectUserSubmit = async () => {
        setDisplayHistogram(false);
        const url = `/api/members/messages/usersearch?searchUser=${selectedUser}`;
        fetchWithToken(url, getToken, {
            method: 'GET'
        }).then(response => {
            console.log("Response", response);
            response.map(i => {
                const formatDate = new Date(parseInt(i.ts));
                modifiedHistogramData.push({
                    text: i.text,
                    ts: formatDate.toString()
                })
            })
            console.log(modifiedHistogramData);
            setDisplayHistogram(true);
        });
    }

    if (!slackUsers)
        return <div>Loading...</div>
    else {
        slackUsers.map(i => {
            userOptions.push({
                value: i.id,
                label: i.real_name
            })
        })
    }

    return (
        <div>
            <h1>Histogram of Messages By User</h1>
            <p>Select a user:</p>
            <Select
                options={userOptions}
                value={userOptions.filter(option => option.label === selectedUser)}
                onChange={event => setSelectedUser(event.label)}
                isSearchable
            />
            <Button onClick={handleSelectUserSubmit}>Go</Button>
            {displayHistogram && <p>Activity Histogram for {selectedUser}</p>}
            <Button onClick={() => console.log(modifiedHistogramData)}>Modified Histogram Data</Button>
            {/* {displayHistogram && <XYPlot height={400} width={400}>
                <VerticalBarSeries data={data} />
                <XAxis title="Week #" />
                <YAxis title="Message Count" />
            </XYPlot>} */}
            {displayHistogram && <BootstrapTable keyField='text' data={modifiedHistogramData || []} columns={columns} />}
        </div>);

};

export default HistogramOfMessagesByUser;
