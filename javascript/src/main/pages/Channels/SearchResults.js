import React from "react";
import { Jumbotron } from "react-bootstrap";
import {useAuth0} from "@auth0/auth0-react";
import { Redirect } from "react-router-dom";
import useSWR from "swr";
import { useParams} from "react-router-dom";
import {fetchWithToken} from "main/utils/fetch";
import MessageList from "main/components/ChannelMessages/MessageList"

// SearchResult page is not fully implemented yet as it needs the backend to give proper data
const SearchPage = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    return (
        <>
            <h1> Search Results </h1>
        </>
    );
};

export default SearchPage;