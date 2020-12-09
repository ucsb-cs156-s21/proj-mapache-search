import React, { useState, useEffect } from "react";
import Select from "react-select";
import useSWR from "swr";
import { fetchWithToken } from "../../utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import { XYPlot, VerticalBarSeries, XAxis, YAxis } from 'react-vis';
import BootstrapTable, { TableHeaderColumn } from 'react-bootstrap-table-next';
import { Row, Col, Button } from "react-bootstrap";

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
    const [sameUser, setSameUser] = useState(false); //Case for when the same user is selected as the currently selected one
    const [userMessages, setUserMessages] = useState([]);
    const [displayHistogram, setDisplayHistogram] = useState(false);
    const [dataIsFetched, setDataIsFetched] = useState(true);
    const [modifiedHistogramData, setModifiedHistogramData] = useState([]);
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: slackUsers } = useSWR(["/api/slackUsers", getToken], fetchWithToken);
    const userOptions = [];

    const handleSelectUserSubmit = () => {
        if (sameUser) return; //Removes the warning for duplicate keys in the BootstrapTable
        setDataIsFetched(false);
        setDisplayHistogram(true);
        setModifiedHistogramData([]);
        const url = `/api/members/messages/usersearch?searchUser=${selectedUser}`;
        fetchWithToken(url, getToken, {
            method: 'GET'
        }).then(response => {
            response.map(i => {
                const formatDate = new Date(parseInt(i.ts));
                setModifiedHistogramData(modifiedHistogramData => [
                    ...modifiedHistogramData,
                    {
                        text: i.text,
                        ts: formatDate.toString()
                    }
                ]);
            })
            setDataIsFetched(true);
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
            <h3>Histogram of Messages By User</h3>
            <p style={{ textAlign: "left" }}>Select a user</p>
            <Row>
                <Col>
                    <Select
                        options={userOptions}
                        value={userOptions.filter(option => option.label === selectedUser)}
                        onChange={event => {
                            if (event.label === selectedUser)
                                setSameUser(true);
                            else {
                                setSameUser(false);
                                setSelectedUser(event.label)
                            }
                        }}
                        isSearchable
                    />
                </Col>
                <Col xs={1}>
                    <Button outline onClick={handleSelectUserSubmit}>Go</Button>
                </Col>
            </Row>
            {displayHistogram &&
                (dataIsFetched ? <div>
                    <h3>Activity Histogram for {selectedUser}</h3>
                    <BootstrapTable keyField='text' data={modifiedHistogramData || []} columns={columns} />
                </div> : <div class="spinner-border text-primary" role="status">
                        <span class="sr-only">Loading...</span>
                    </div>)
            }
            {/* {displayHistogram && <XYPlot height={400} width={400}>
                <VerticalBarSeries data={data} />
                <XAxis title="Week #" />
                <YAxis title="Message Count" />
            </XYPlot>} */}
        </div >);

};

export default HistogramOfMessagesByUser;
