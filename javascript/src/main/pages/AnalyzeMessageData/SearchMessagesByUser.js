import React, { useState} from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "main/utils/fetch";
import { Form, Button } from "react-bootstrap";
import UserMessageList from "main/components/UserMessages/UserMessageList"
import UserChannelMessageList from "../../components/UserMessages/UserChannelMessageList";

const SearchMessagesByUser = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const [ searchUser, setSearchUser ] = useState('');
    const [ searchResults, setSearchResults ] = useState([]);

    const handleSearchUserOnChange = (event) => {
        setSearchUser(event.target.value);
    };
    
    const handleSearchUserOnSubmit = () => {
        const url = `/api/members/messages/usersearch?searchUser=${searchUser}`;
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
                <Form.Group controlId="searchUser" onChange={handleSearchUserOnChange}>
                    <Form.Label>Search User</Form.Label>
                    <Form.Control type="text" placeholder="Enter Search User" />
                    <Button onClick={handleSearchUserOnSubmit}>Search</Button>
                </Form.Group>
            </Form>
            <UserChannelMessageList messages = {searchResults} />
            <UserMessageList messages = {searchResults} />
        </>
    );
};

export default SearchMessagesByUser;

