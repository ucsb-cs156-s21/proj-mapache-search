import React, { useState, useEffect } from "react";
import useSWR from "swr";
import { fetchWithToken } from "../../utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import BootstrapTable from 'react-bootstrap-table-next';
import { Bar } from "react-chartjs-2";
import { MDBContainer } from "mdbreact";
import { Row, Col, Button, Form } from "react-bootstrap";

const columns = [{
    dataField: 'week',
    text: 'Week'
},
{
    dataField: 'count',
    text: 'Message Count'
}
];

// const barChartOptions = {
//     responsive: true,
//     maintainAspectRatio: false,
//     scales: {
//         xAxes: [
//             {
//                 barPercentage: 1,
//                 gridLines: {
//                     display: false
//                 }
//             }
//         ],
//         yAxes: [
//             {
//                 gridLines: {
//                     display: false
//                 },
//                 ticks: {
//                     beginAtZero: true
//                 }
//             }
//         ]
//     }
// };

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

    // const [dataBar, setDataBar] = useState({
    //     labels: [], //Start date - End date
    //     datasets: [
    //         {
    //             label: "",
    //             data: [], //Message counts
    //             backgroundColors: [
    //                 "rgba(255, 134,159,0.4)"
    //             ],
    //             borderWidth: 2,
    //             borderColor: [
    //                 "rgba(255, 134, 159, 1)"
    //             ]
    //         }
    //     ]
    // });

    const handleSelectUserSubmit = () => {
        setDataIsFetched(false);
        setDisplayHistogram(true);
        setModifiedHistogramData([]);
        setPerWeekHistogramData([]);
        const url = `/api/members/messages/usersearch?searchUser=${selectedUser}`;
        fetchWithToken(url, getToken, {
            method: 'GET'
        }).then(response => {
            response.map(i => {
                const formatDate = moment.unix(i.ts).format('DD/MM/YYYY');
                const formatWeek = moment.unix(i.ts).isoWeek();
                const formatYear = moment.unix(i.ts).isoWeekYear();
                setModifiedHistogramData(modifiedHistogramData => [
                    ...modifiedHistogramData,
                    {
                        week: formatWeek,
                        year: formatYear
                    }
                ]);
            })
            //Setting up the bar chart with past 10 weeks
            // let currentWeek = moment().isoWeek();
            // console.log(currentWeek)
            // let firstWeek = currentWeek - 10;
            // let weekDates = [];
            // let weekCounts = [];
            // for (let i = firstWeek; i <= currentWeek; i++) {
            //     let tmp = perWeekHistogramData.filter((element) => {
            //         console.log(element.week);
            //         return (element.week === i);
            //     });
            //     if (!tmp) //If no messages sent in that week
            //         weekCounts.push(0);
            //     else
            //         weekCounts.push(tmp.count);
            //     weekDates.push(tmp.startDate + " - " + tmp.endDate);
            // }
            // setDataBar(dataBar => ({
            //     labels: weekDates,
            //     datasets: [
            //         ...dataBar.datasets,
            //         dataBar.data: weekCounts
            //     ]
            // }));
            setDataIsFetched(true);
        }).then(() => {
            modifiedHistogramData.map((element) => {
                let tmp = modifiedHistogramData.filter((elem) => (elem.week === element.week) && (elem.year === element.year));
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
                setPerWeekHistogramData(perWeekHistogramData => perWeekHistogramData.sort((a, b) => {
                    if (a.week < b.week)
                        return -1;
                    return 1;
                }))
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
            <Button onClick={() => console.log(modifiedHistogramData)}>modifiedHistogramData</Button>
            <Button onClick={() => console.log(perWeekHistogramData)}>perWeekHistogramData</Button>
            {/* <Button onClick={() => console.log(dataBar)}>dataBar</Button> */}
            {displayHistogram &&
                (dataIsFetched ? <div>
                    <h3>Activity for {selectedUser}</h3>
                    {/* <MDBContainer> */}
                    {/* <Bar data={dataBar} options={barChartOptions} /> */}
                    {/* </MDBContainer> */}
                    <BootstrapTable keyField='week' data={perWeekHistogramData || []} columns={columns}></BootstrapTable>
                </div> : <div className="spinner-border text-primary" role="status">
                        <span className="sr-only">Loading...</span>
                    </div>)
            }
        </div >);

};

export default HistogramOfMessagesByUser;
