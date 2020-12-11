import React, { useState, useEffect } from "react";
import { fetchWithToken } from "../../utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import BootstrapTable from 'react-bootstrap-table-next';
import { Row, Col, Button, Form } from "react-bootstrap";
import weekComparator from "../../utils/weekComparator";

const columns = [{
    dataField: 'week',
    text: 'Week'
},
{
    dataField: 'count',
    text: 'Message Count'
}
];

const HistogramOfMessagesByUser = () => {
    const [selectedUser, setSelectedUser] = useState('');
    const [userMessages, setUserMessages] = useState([]);
    const [displayHistogram, setDisplayHistogram] = useState(false);
    const [dataIsFetched, setDataIsFetched] = useState(true);
    const [modifiedHistogramData, setModifiedHistogramData] = useState([]);
    const [perWeekHistogramData, setPerWeekHistogramData] = useState([]);
    const { getAccessTokenSilently: getToken } = useAuth0();
    var _ = require('lodash');
    var moment = require('moment');

    const handleSelectUserSubmit = () => {
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
            setDataIsFetched(true);
        }).then(() => {
            modifiedHistogramData.map((element) => {
                let tmp = modifiedHistogramData.filter((week) => week === element);
                let weekStartDate = moment().isoWeekYear(element.year).isoWeek(element.week).startOf('week').format('MM/DD/YYYY');
                let weekEndDate = moment().isoWeekYear(element.year).isoWeek(element.week).endOf('week').format('MM/DD/YYYY');
                setPerWeekHistogramData(perWeekHistogramData => [
                    ...perWeekHistogramData,
                    {
                        "week": weekStartDate + " - " + weekEndDate,
                        "weekNum": element.week,
                        "count": tmp.length
                    }
                ])
                setPerWeekHistogramData(perWeekHistogramData => perWeekHistogramData.sort(weekComparator));
                setPerWeekHistogramData(perWeekHistogramData => _.uniqWith(perWeekHistogramData, _.isEqual));
                setModifiedHistogramData(modifiedHistogramData => modifiedHistogramData.filter((week) => week !== element));
            })
        })
    }

    return (
        <div>
            <h3>Histogram of Messages By User</h3>
            <Row>
                <Col>
                    <Form.Group controlId="form-select" className="pt-3" style={{ textAlign: "left" }}>
                        <Form.Label>Enter a user</Form.Label>
                        <Form.Control
                            onChange={val => setSelectedUser(val.target.value)}
                        />
                    </Form.Group>
                </Col>
                <Col xs={2} className="d-flex align-items-center" style={{ marginTop: "35px" }}>
                    <Button id="submit-button" onClick={handleSelectUserSubmit}>Go</Button>
                </Col>
            </Row>
            {displayHistogram &&
                (dataIsFetched ? <div>
                    <h3>Activity for {selectedUser}</h3>
                    <BootstrapTable keyField='week' data={perWeekHistogramData || []} columns={columns}></BootstrapTable>
                </div> : <div className="spinner-border text-primary" role="status">
                        <span className="sr-only">Loading...</span>
                    </div>)
            }
        </div >);

};

export default HistogramOfMessagesByUser;
