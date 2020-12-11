import React, { useState, useEffect } from "react";
import Select from "react-select";
import useSWR from "swr";
import { fetchWithToken } from "../../utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import BootstrapTable from 'react-bootstrap-table-next';
import { Bar } from "react-chartjs-2";
import { MDBContainer } from "mdbreact";
import { Row, Col, Button } from "react-bootstrap";
import { menuListCSS } from "react-select/src/components/Menu";

const columns = [{
    dataField: 'week',
    text: 'Week'
},
{
    dataField: 'startDate',
    text: 'Start Date'
},
{
    dataField: 'endDate',
    text: 'End Date'
},
{
    dataField: 'count',
    text: 'Count'
}
];

const barChartOptions = {
    responsive: true,
    maintainAspectRatio: false,
    scales: {
        xAxes: [
            {
                barPercentage: 1,
                gridLines: {
                    display: false
                }
            }
        ],
        yAxes: [
            {
                gridLines: {
                    display: false
                },
                ticks: {
                    beginAtZero: true
                }
            }
        ]
    }
};



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

    const [dataBar, setDataBar] = useState({
        labels: [], //Start date - End date
        datasets: [
            {
                label: "",
                data: [], //Message counts
                backgroundColors: [
                    "rgba(255, 134,159,0.4)"
                ],
                borderWidth: 2,
                borderColor: [
                    "rgba(255, 134, 159, 1)"
                ]
            }
        ]
    });

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
            console.log(modifiedHistogramData);
            modifiedHistogramData.map((element) => {
                let tmp = modifiedHistogramData.filter((elem) => (elem.week === element.week) && (elem.year === element.year));
                let weekStartDate = moment().isoWeekYear(element.year).isoWeek(element.week).startOf('week').format('MM/DD/YYYY');
                let weekEndDate = moment().isoWeekYear(element.year).isoWeek(element.week).endOf('week').format('MM/DD/YYYY');
                setPerWeekHistogramData(perWeekHistogramData => [
                    ...perWeekHistogramData,
                    {
                        "week": element.week,
                        "startDate": weekStartDate,
                        "endDate": weekEndDate,
                        "count": tmp.length
                    }
                ])
                setPerWeekHistogramData(perWeekHistogramData => _.uniqWith(perWeekHistogramData, _.isEqual));
                setModifiedHistogramData(modifiedHistogramData => modifiedHistogramData.filter((week) => week !== element));
            })
            //Setting up the bar chart with past 10 weeks
            let currentWeek = moment().isoWeek();
            console.log(currentWeek)
            let firstWeek = currentWeek - 10;
            let weekDates = [];
            let weekCounts = [];
            for (let i = firstWeek; i <= currentWeek; i++) {
                let tmp = perWeekHistogramData.filter((element) => {
                    console.log(element.week);
                    return (element.week === i);
                });
                if (!tmp) //If no messages sent in that week
                    weekCounts.push(0);
                else
                    weekCounts.push(tmp.count);
                weekDates.push(tmp.startDate + " - " + tmp.endDate);
            }
            setDataBar(dataBar => ({
                labels: weekDates,
                datasets: [
                    ...dataBar.datasets,
                    dataBar.data: weekCounts
                ]
            }));
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
            <Button onClick={() => console.log(perWeekHistogramData)}>perWeekHistogramData</Button>
            <Button onClick={() => console.log(dataBar)}>dataBar</Button>
            {displayHistogram &&
                (dataIsFetched ? <div>
                    <h3>Activity for {selectedUser}</h3>
                    {/* <MDBContainer> */}
                    {/* <Bar data={dataBar} options={barChartOptions} /> */}
                    {/* </MDBContainer> */}
                    {/* <BootstrapTable keyField='week' data={perWeekHistogramData || []} columns={columns}></BootstrapTable> */}
                </div> : <div className="spinner-border text-primary" role="status">
                        <span className="sr-only">Loading...</span>
                    </div>)
            }
        </div >);

};

export default HistogramOfMessagesByUser;
