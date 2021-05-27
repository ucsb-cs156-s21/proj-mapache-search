import React, {useState} from "react";
import { useAuth0 } from "@auth0/auth0-react";
import useSWR from "swr";
import { fetchWithToken } from "main/utils/fetch";
import SearchResultsView from "main/components/ChannelMessages/SearchResultsView"
import { Form } from "react-bootstrap";

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
            <SearchResultsView messages={searchResults || []} searchField={false} />
        </>
    );
};

export default SearchPage;