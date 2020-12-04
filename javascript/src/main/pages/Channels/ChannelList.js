import React from 'react';
import { Jumbotron } from "react-bootstrap";
import {useAuth0} from "@auth0/auth0-react";
import { Redirect } from "react-router-dom";
import ChannelTable from "main/components/Channels/ChannelTable"
import useSWR from "swr";
import {fetchWithToken} from "main/utils/fetch";



const ChannelList = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { data: channels } = useSWR(["/api/members/channels", getToken], fetchWithToken);

    return (
       // <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.bundle.min.js" crossorigin="anonymous"></script>

                <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
                <div class="input-group">

                    <input class="form-control" type="text" placeholder="Search" aria-label="Search" aria-describedby="basic-addon2" />
                    <div class="input-group-append">
                        <button class="btn btn-primary" type="button"><i class="fas fa-search"></i></button>
                    </div>
                    <ChannelTable channels={channels || []} />
                </div>
            </form>      
        
    );
};

export default ChannelList;

