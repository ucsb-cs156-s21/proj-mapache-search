import React from "react";
import { Jumbotron } from "react-bootstrap";
import {useAuth0} from "@auth0/auth0-react";
import { Redirect } from "react-router-dom";
import useSWR from "swr";
import { useParams} from "react-router-dom";
import {fetchWithToken} from "main/utils/fetch";
import MessageList from "main/components/Messages/MessageList"

const ChannelPage = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { channel } = useParams();
    const { data: messages } = useSWR([`/api/members/channel/${channel}/messages`, getToken], fetchWithToken);
    return (
        <>
            <h1> {channel} </h1>
            <MessageList messages={messages || []} />
        </>
    );
};

export default ChannelPage;
