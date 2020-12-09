import React, {useState} from "react";
import { useAuth0 } from "@auth0/auth0-react";
import useSWR from "swr";
import { fetchWithToken } from "main/utils/fetch";
import MessageTable from "main/components/ChannelMessageReaction/MessageTableReaction"
import { Form } from "react-bootstrap";


const AnalyzeReactions = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const [searchReaction, setSearchReaction] = useState('');
    const { data: searchResults } = useSWR([`/api/members/messages/reactonsearch?searchReaction=${searchReaction}`, getToken], fetchWithToken);
    const handleSearchReactionOnChange = (event) => {
        setSearchReaction(event.target.value);
    };
    return ( 
        <>
            <h1> Search Results </h1>
            <Form>
                <Form.Group controlId="searchReaction" onChange={handleSearchReactionOnChange}>
                    <Form.Label>Search Reaction</Form.Label>
                    <Form.Control type="text" placeholder="Enter Search Reaction" />
                </Form.Group>
            </Form>
        <MessageTable messages = {searchResults || [] } />
        </>
    );
};

export default AnalyzeReactions;
