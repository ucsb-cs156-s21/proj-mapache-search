import React, { useEffect } from "react";
import useSWR from "swr";
import { Jumbotron } from "react-bootstrap";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "../../utils/fetch";
import Select from 'react-select';

const HistogramOfMessagesByUser = () => {
    // const { getAccessTokenSilently: getToken } = useAuth0();
    // const { data: slackUsers } = useSWR(["/api/slackUsers", getToken], fetchWithToken);

    // const options = [];

    // useEffect(() => {
    //     slackUsers.map(i => {
    //         options.push({
    //             value: i.getId(),
    //             label: i.getName()
    //         })
    //     });
    // }, [])

    return <Jumbotron>
        <h1>Histogram Of Messages By User</h1>
        <h3>Select a user to view: </h3>
        {/* <Select options={options} /> */}
        {/* <button onClick={() => console.log(slackUsers)}></button> */}
    </Jumbotron>

};

export default HistogramOfMessagesByUser;
