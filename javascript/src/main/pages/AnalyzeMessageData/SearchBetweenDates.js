import React, { useState} from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "main/utils/fetch";
import { Form, Button } from "react-bootstrap";
import MessageListView from "main/components/ChannelMessages/MessageListView"

const SearchBetweenDates = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const [ searchDate, setSearchDate ] = useState(''); 
    const [ searchDate2, setSearchDate2 ] = useState('');
    const [ searchResults, setSearchResults ] = useState([]);

    const handleSearchDateOnChange = (event) => {
        setSearchDate((new Date(event.target.value).getTime() / 1000).toString());
    };

    const handleSearchDateOnChange2 = (event) => {
        setSearchDate2((new Date(event.target.value).getTime() / 1000).toString());
    };
    const handleSearchDateOnSubmit = (event) => {
        event.preventDefault();
        const url = `/api/members/messages/datesearch?searchDate=${searchDate}&searchDate2=${searchDate2}`;
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
                    <Form.Label>Start date</Form.Label>
                        <Form.Control type="date" placeholder="mm/dd/yyyy" />
                </Form.Group>
                <Form.Group controlId="searchDate2" onChange={handleSearchDateOnChange2}>
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