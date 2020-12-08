import React, {useState} from "react";
import { useAuth0 } from "@auth0/auth0-react";
import useSWR from "swr";
import { fetchWithToken } from "main/utils/fetch";
import UserMessageList from "main/components/UserMessages/UserMessageList"
import { Form } from "react-bootstrap";

const SearchMessagesByUser = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const [searchUser, setSearchUser] = useState('');
    const { data: searchResults } = useSWR([`/api/members/messages/usersearch?searchUser=${searchUser}`, getToken], fetchWithToken);
    const handleSearchUserOnChange = (event) => {
        setSearchUser(event.target.value);
    };
    return (
        <>
            <h1> Search Results </h1>
            <Form>
                <Form.Group controlId="searchUser" onChange={handleSearchUserOnChange}>
                    <Form.Label>Search User</Form.Label>
                    <Form.Control type="text" placeholder="Enter Search User" />
                </Form.Group>
            </Form>
            <UserMessageList messages = {searchResults || []} />
        </>
    );
};

export default SearchMessagesByUser;

