import React from "react";
import { Jumbotron } from "react-bootstrap";
import {useAuth0} from "@auth0/auth0-react";
import { Redirect } from "react-router-dom";
import ChannelMessages from "main/components/Channels/ChannelMessages"
import useSWR from "swr";
import { useParams} from "react-router-dom";
import {fetchWithToken} from "main/utils/fetch";

const ChannelPage = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { channel } = useParams();
    //const { data: channels } = useSWR(['/api/members/channels/${channel}', getToken], fetchWithToken);

    return (
           //<ChannelMessages channels={channels|| []} />
           <>
           <h1> {channel} </h1>
           {
            <p>Messages go here</p>

           }
         </>
    );
};

export default ChannelPage;
