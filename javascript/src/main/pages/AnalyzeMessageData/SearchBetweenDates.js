import React, { useState} from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "main/utils/fetch";
import { Form, Button } from "react-bootstrap";
import DateMessageList from "main/components/DateMessages/DateMessageList"
import DateChannelMessageList from "../../components/DateMessages/DateChannelMessageList";

const SearchBetweenDates = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const [ searchDate, setSearchDate ] = useState('');
    const [ searchResults, setSearchResults ] = useState([]);

    const handleSearchDateOnChange = (event) => {
        setSearchDate(event.target.value);
    };
    
    const handleSearchDateOnSubmit = (event) => {
        event.preventDefault();
        const url = `/api/members/messages/datesearch?searchDate=${searchDate}`;
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
            <Form onSubmit={handleSearchDateOnSubmit}>
                <Form.Group controlId="searchDate" onChange={handleSearchDateOnChange}>
                    <Form.Label>Search Between Two Dates</Form.Label>
                        <Form.input type="date"></Form.input>
                        <Button onClick={handleSearchDateOnSubmit}>Search</Button>
                </Form.Group>
            </Form>
            <DateChannelMessageList messages = {searchResults} />
            <DateMessageList messages = {searchResults} />
        </>
    );
};

export default SearchBetweenDates;