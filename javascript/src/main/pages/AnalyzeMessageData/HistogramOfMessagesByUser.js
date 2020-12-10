import React, { useState, useEffect } from "react";
import Select from "react-select";
import useSWR from "swr";
import { fetchWithToken } from "../../utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import BootstrapTable from 'react-bootstrap-table-next';
import { Row, Col, Button } from "react-bootstrap";

const columns = [{
    dataField: 'week',
    text: 'Week'
},
{
    dataField: 'count',
    text: 'Count'
}
];

const HistogramOfMessagesByUser = () => {
    const [selectedUser, setSelectedUser] = useState('');
    const [sameUser, setSameUser] = useState(false); //Case for when the same user is selected as the currently selected one
    const [userMessages, setUserMessages] = useState([]);
    const [displayHistogram, setDisplayHistogram] = useState(false);
    const [dataIsFetched, setDataIsFetched] = useState(true);
    const [modifiedHistogramData, setModifiedHistogramData] = useState([]);
    const [perWeekHistogramData, setPerWeekHistogramData] = useState([]);
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: slackUsers } = useSWR(["/api/slackUsers", getToken], fetchWithToken);
    const [userOptions, setUserOptions] = useState([]);
    var _ = require('lodash');
    var moment = require('moment');

    useEffect(() => {
        if (!slackUsers) setUserOptions([])
        else {
            slackUsers.map(i => {
                setUserOptions(userOptions => [
                    ...userOptions,
                    {
                        value: i.id,
                        label: i.real_name
                    }
                ])
            })
        }
    }, [slackUsers])

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
                const formatDate = moment.unix(i.ts).format('DD/MM/YYYY');
                const formatWeek = moment.unix(i.ts).isoWeek()
                setModifiedHistogramData(modifiedHistogramData => [
                    ...modifiedHistogramData,
                    formatWeek
                ]);
            })
            modifiedHistogramData.map((element) => {
                let tmp = modifiedHistogramData.filter((week) => week === element);
                setPerWeekHistogramData(perWeekHistogramData => [
                    ...perWeekHistogramData,
                    {
                        "week": element,
                        "count": tmp.length
                    }
                ])
                setModifiedHistogramData(modifiedHistogramData => modifiedHistogramData.filter((week) => week !== element));
            })
            setDataIsFetched(true);
        });
    }

    return (
        <div>
            <h3>Histogram of Messages By User</h3>
            <p style={{ textAlign: "left" }}>Select a user</p>
            <Row>
                <Col>
                    <Select
                        isSearchable
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
                    />
                </Col>
                <Col xs={1}>
                    <Button outline id="submit-button" onClick={handleSelectUserSubmit}>Go</Button>
                </Col>
            </Row>
            <Button onClick={() => console.log(modifiedHistogramData)}>modifiedHistogramData</Button>
            <Button onClick={() => console.log(_.uniqWith(perWeekHistogramData, _.isEqual))}>perWeekHistogramData</Button>
            {displayHistogram &&
                (dataIsFetched ? <div>
                    <h3>Activity for {selectedUser}</h3>
                    <BootstrapTable keyField='week' data={_.uniqWith(perWeekHistogramData, _.isEqual) || []} columns={columns}></BootstrapTable>
                </div> : <div className="spinner-border text-primary" role="status">
                        <span className="sr-only">Loading...</span>
                    </div>)
            }
        </div >);

};

export default HistogramOfMessagesByUser;
