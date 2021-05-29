import React from "react";
import {useAuth0} from "@auth0/auth0-react";
import useSWR from "swr";
import { useParams } from "react-router-dom";
import {fetchWithToken} from "main/utils/fetch";
import ChannelLinks from "main/components/ChannelMessages/ChannelLinks"

const ChannelPageLinks = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { channel } = useParams();
    const { data: messages } = useSWR([`/api/members/channel/${channel}/messages`, getToken], fetchWithToken);

    return (
        <>
            <h1> {channel} Links </h1>
            <a href={"/member/listViewChannels/" + channel}>Switch to List View to search</a>
            <br/>
            <a href={"/member/channels/" + channel}>Switch to Scrollable View</a>
            <ChannelLinks messages={messages || []} channel={channel} />
        </>
    );
};

export default ChannelPageLinks;
