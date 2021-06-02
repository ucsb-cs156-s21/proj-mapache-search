import React from 'react';
import {useAuth0} from "@auth0/auth0-react";
import ChannelTable from "main/components/Channels/ChannelTable"
import useSWR from "swr";
import {fetchWithToken} from "main/utils/fetch";

const ChannelList = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: channels } = useSWR(["/api/members/channels", getToken], fetchWithToken);
    return (
            <ChannelTable channels={channels || []} />       
    );
};

export default ChannelList;

