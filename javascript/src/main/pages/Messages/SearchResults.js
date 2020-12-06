import React, {useState} from "react";
import { Jumbotron } from "react-bootstrap";
import { useAuth0 } from "@auth0/auth0-react";
import { Redirect } from "react-router-dom";
import useSWR from "swr";
import { useParams } from "react-router-dom";
import { fetchWithToken } from "main/utils/fetch";
import MessageList from "main/components/ChannelMessages/MessageList"
import JSONPrettyCard from "main/components/Utilities/JSONPrettyCard";
import { Form, Button } from "react-bootstrap";

const SearchPage = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const [searchString, setSearchString] = useState('');
    const { data: searchResults } = useSWR([`/api/members/messages/contentsearch?searchString=${searchString}`, getToken], fetchWithToken);
    const handleSearchStringOnChange = (event) => {
        setSearchString(event.target.value);
    };
    return (
        <>
            <h1> Search Results </h1>
            <Form>
                <Form.Group controlId="searchString" onChange={handleSearchStringOnChange}>
                    <Form.Label>Search String</Form.Label>
                    <Form.Control type="text" placeholder="Enter Search String" />
                </Form.Group>
            </Form>
            <MessageList messages = {searchResults || []} />
        </>
    );
};

export default SearchPage;