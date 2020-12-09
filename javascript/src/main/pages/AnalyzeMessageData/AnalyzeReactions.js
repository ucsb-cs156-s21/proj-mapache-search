import React, {useState} from "react";
import { useAuth0 } from "@auth0/auth0-react";
import useSWR from "swr";
import { fetchWithToken } from "main/utils/fetch";
import MessageTable from "main/components/ChannelMessageReaction/MessageTableReaction"
import { Form, Button } from "react-bootstrap";


const AnalyzeReactions = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const [ searchReaction, setSearchReaction ] = useState("");
    const [ searchResults, setSearchResults ] = useState([]);
    const handleSearchReactionOnChange = (event) => {
        setSearchReaction(event.target.value);
    };

    const handleSearchReactionOnSubmit = () => {
        const url = `/api/members/messages/reactionsearch?searchReaction=${searchReaction}`;
        const options = {
            method: 'GET',
        }
        fetchWithToken(url, getToken, options)
            .then((response) => {
                setSearchResults(response);
            })
    };
    return ( 
        <>
            <h1> Search Results </h1>
            <Form>
                <Form.Group controlId="searchReaction" onChange={handleSearchReactionOnChange}>
                    <Form.Label>Search Reaction</Form.Label> <br/>
                    <a href = "https://www.webfx.com/tools/emoji-cheat-sheet/"> This is the link for the emoji names </a>
                    <Form.Control type="text" placeholder="Enter Search Reaction" />
                    <Button onClick={handleSearchReactionOnSubmit}>Search</Button>
                </Form.Group>
            </Form>
        <MessageTable messages = {searchResults || [] } />
        </>
    );
};

export default AnalyzeReactions;
