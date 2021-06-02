import React, { useState} from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "main/utils/fetch";
import { Form, Button } from "react-bootstrap";
import MessageListView from "main/components/ChannelMessages/MessageListView"

const SearchBetweenDates = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const [ startDate, setStartDate ] = useState(''); 
    const [ endDate, setEndDate ] = useState('');
    const [ searchResults, setSearchResults ] = useState([]);

    const handleStartDateOnChange = (event) => {
        setStartDate((new Date(event.target.value).getTime() / 1000).toString());
    };

    const handleEndDateOnChange = (event) => {
        setEndDate((new Date(event.target.value).getTime() / 1000).toString());
    };
    const handleSearchDateOnSubmit = (event) => {
        event.preventDefault();
        const url = `/api/members/messages/datesearch?startDate=${startDate}&endDate=${endDate}`;
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
                <Form.Group controlId="startDate" onChange={handleStartDateOnChange}>
                    <Form.Label>Start date</Form.Label>
                        <Form.Control type="date" placeholder="mm/dd/yyyy" />
                </Form.Group>
                <Form.Group controlId="endDate" onChange={handleEndDateOnChange}>
                    <Form.Label>End date</Form.Label>
                        <Form.Control type="date" placeholder="mm/dd/yyyy" />
                </Form.Group>
                <Button onClick={handleSearchDateOnSubmit}>Search</Button>
            </Form>
            <MessageListView messages={searchResults} searchField = {false} />

        </>
    );
    
};

export default SearchBetweenDates;