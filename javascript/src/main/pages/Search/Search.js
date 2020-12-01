import React, { useState } from "react";
import { Jumbotron } from "react-bootstrap";
import {useAuth0} from "@auth0/auth0-react";
import { Redirect } from "react-router-dom";
import { Form, Button, Row, Col, Container } from "react-bootstrap";

const Search = () => {
    const { isAuthenticated } = useAuth0();
    const emptyQuery = {
        searchQuery: "",
    }

    const [query, setQuery] = useState(emptyQuery);

    const handleOnSubmit = (e) => {
        e.preventDefault();
        //perform search here
    }

    return (
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
    );
};

export default Search;
