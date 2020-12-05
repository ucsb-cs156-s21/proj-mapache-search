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

    function getInputValue(e) {
        // If the user doesn't enter anything in the search bar the user will stay on the channel list page
        if(document.getElementById("myInput").value.length == 0){
            return;
        }
        else{
            // Saves the user's input in inputVal and sends the user to the SearchResults Page
            var inputVal = document.getElementById("myInput").value;
            window.location.href = `/member/channels/SearchResults`;
        }
    }
    
    return (
                // form allows you to type into the search bar
                <form class="d-none d-md-inline-block form-inline ml-auto mr-0 mr-md-3 my-2 my-md-0">
                <div class="input-group">
                    <input class="form-control" type="text" id = "myInput" placeholder="Search" aria-label="Search" aria-describedby="basic-addon2" />
                    <div class="input-group-append">
                        {/* Uses boostrap to make the search button which stores user input inputVal in the function getInputValue */}
                        <button class="btn btn-primary" type="button" onClick={getInputValue}><i class="fas fa-search"></i>Search</button>
                    </div>
                     {/* Lists out all of the channels with their respective data */}
                    <ChannelTable channels={channels || []} />
                </div>
            </form>
    );
};

export default ChannelList;

