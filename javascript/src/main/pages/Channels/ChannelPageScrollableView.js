import React from "react";
import {useAuth0} from "@auth0/auth0-react";
import useSWR from "swr";
import { useParams} from "react-router-dom";
import {fetchWithToken} from "main/utils/fetch";
import MessageScrollableView from "main/components/ChannelMessages/MessageScrollableView"

const ChannelPageScrollableView = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { channel } = useParams();
    const { data: messages } = useSWR([`/api/members/channel/${channel}/messages`, getToken], fetchWithToken);

    return (
        <>
            <h1> {channel} </h1>
            <a href={"/member/listViewChannels/" + channel}>Switch to List View to search</a>

            <MessageScrollableView messages={messages || []} channel={channel} />
        </>
    );
};

export default ChannelPageScrollableView;
