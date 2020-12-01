import React, { useState } from "react";
import { Jumbotron } from "react-bootstrap";
import { useAuth0 } from "@auth0/auth0-react";
import { Redirect } from "react-router-dom";
import { Form, Button, Row, Col, Container } from "react-bootstrap";
import JSONPrettyCard from "main/components/Utilities/JSONPrettyCard";
import { fetchWithToken } from "main/utils/fetch";

const Search = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const emptyQuery = {
        searchQuery: "",
    }

    const fetchSearchResults = async (event) => {
        const url = `/api/member/search/basic?searchQuery=${query.searchQuery}`;

        try {
            const result = await fetchWithToken(url, getToken, {
                method: "GET",
                headers: {
                    "content-type": "application/json",
                },
            });
            console.log(`result=${JSON.stringify(result)}`)
            return result;
        } catch (err) {
            console.log(`err=${err}`)
        }
    };

    const [query, setQuery] = useState(emptyQuery);
    const [results, setResults] = useState({});

    const handleOnSubmit = async (e) => {
        e.preventDefault();
        const answer = await fetchSearchResults(e);
        setResults(answer);
    }

    return (
        <>
            <Form onSubmit={handleOnSubmit}>
                <Form.Group as={Row} controlId="search">
                    <Form.Label column sm={2}>Search</Form.Label>
                    <Col sm={10}>
                        <Form.Control type="text" placeholder="type your query" onChange={(e) => setQuery({
                            ...query,
                            searchQuery: e.target.value
                        })} />
                    </Col>
                </Form.Group>
                <Form.Group as={Row}>
                    <Col sm={{ span: 10, offset: 2 }}>
                        <Button type="submit">Submit</Button>
                    </Col>
                </Form.Group>
            </Form>
            <JSONPrettyCard
                expression={"results"}
                value={results}
            />
        </>
    );
};

export default Search;
