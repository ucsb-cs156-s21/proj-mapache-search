import React, {useState} from "react";
import { useAuth0 } from "@auth0/auth0-react";
import useSWR from "swr";
import { fetchWithToken } from "main/utils/fetch";
import MessageTableReaction from "main/components/ChannelMessageReaction/MessageTableReaction"
import { Form, Button } from "react-bootstrap";


const AnalyzeReactions = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const [ searchReaction, setSearchReaction ] = useState('');
    const [ searchResults, setSearchResults ] = useState([]);
    
    const handleSearchReactionOnChange = (event) => {
        setSearchReaction(event.target.value);
    };
    
    const handleSearchReactionOnSubmit = (e) => {
        e.preventDefault();
        let url = `/api/members/messages/reactionsearch?searchReaction=${encodeURIComponent(searchReaction)}`;
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
            <Form onSubmit={handleSearchReactionOnSubmit}>
                <Form.Group controlId="searchReaction">
                    <Form.Label>Search Reaction</Form.Label> <br/>
                    <a href = "https://www.webfx.com/tools/emoji-cheat-sheet/"> This is the link for the emoji names </a>
                    <Form.Control type="text" placeholder="Enter Search Reaction" onChange={(e) => handleSearchReactionOnChange(e)} />
                </Form.Group>
                <Form.Group>
                    <Button type='submit'>Search</Button>
                </Form.Group>
            </Form>
        <MessageTableReaction messages = {searchResults} reaction={searchReaction}/>
        </>
    );
};

export default AnalyzeReactions;
