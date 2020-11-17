import React from "react";
import { Jumbotron } from "react-bootstrap";
import {useAuth0} from "@auth0/auth0-react";
import { Redirect } from "react-router-dom";
import ChannelTable from "main/components/Channels/ChannelTable"

const ChannelList = () => {
    // const { isAuthenticated } = useAuth0();

    

    const channels = [
        { id: "fakeChannel1id", name: "fakeChannel1name"},
        { id: "fakeChannel2id", name: "fakeChannel2name"},
    ];

    return (
           <ChannelTable channels={channels} />
    );
};

export default ChannelList;
