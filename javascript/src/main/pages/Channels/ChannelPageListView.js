import React from "react";
import {useAuth0} from "@auth0/auth0-react";
import useSWR from "swr";
import { useParams} from "react-router-dom";
import {fetchWithToken} from "main/utils/fetch";
import MessageList from "main/components/ChannelMessages/MessageList"

const ChannelPageListView = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { channel } = useParams();
    const { data: messages } = useSWR([`/api/members/channel/${channel}/messages`, getToken], fetchWithToken);

    return (
        <>
            <h1> {channel} </h1>
            <a href={"/member/scrollableChannels/" + channel}>Switch to Scrollable View</a>
            <MessageList messages={messages || []} />
        </>
    );
};

export default ChannelPageListView;
