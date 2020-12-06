import React, {useState} from 'react';
import { Jumbotron } from "react-bootstrap";
import {useAuth0} from "@auth0/auth0-react";
import { Redirect } from "react-router-dom";
import ChannelTable from "main/components/Channels/ChannelTable"
import useSWR from "swr";
import {fetchWithToken} from "main/utils/fetch";
import DatePicker from 'react-datepicker'
import 'react-datepicker/dist/react-datepicker.css'

const ChannelList = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: channels } = useSWR(["/api/members/channels", getToken], fetchWithToken);
    
    /*const [startDate, setStartDate] = useState(new Date());
    const [endDate, setEndDate] = useState(null);
    const onChange = dates => {
      const [start, end] = dates;
      setStartDate(start);
      setEndDate(end);
      console.log(startDate);
      console.log(endDate);
    };*/

    //console.log(startDate);
    //console.log(endDate);

    return (
            <ChannelTable channels={channels || []} />
              
    );
};

export default ChannelList;

