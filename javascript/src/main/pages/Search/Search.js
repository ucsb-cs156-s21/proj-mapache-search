import React, { useState } from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { Form, Button, Row, Col } from "react-bootstrap";
import { fetchWithToken } from "main/utils/fetch";
import SearchCard from "main/components/SearchCard/SearchCard";


const Search = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const emptyQuery = {
        searchQuery: "",
    }
    const emptyResults = { items: [] }
    
    
    const fetchSearchResults = async (_event) => {
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
            return emptyResults;
        }
    };
    const fetchQuota = async (_event) => {
        const url = `/api/member/search/quota`;

        try {
            const result = await fetchWithToken(url, getToken, {
                method: "GET",
                headers: {
                    "content-type": "application/json",
                },
            });
            console.log(`result=${JSON.stringify(result)}`);
            return result;
        } catch (err) {
            console.log(`err=${err}`);
            return {quota:0};
        }
    };

    
    const [query, setQuery] = useState(emptyQuery);
    const [results, setResults] = useState(emptyResults);
    const [quota, setQuota] = useState(0);

    const handleOnSubmit = async (e) => {
        e.preventDefault();
        const answer = await fetchSearchResults(e);
        setResults(answer);
        const quotaInfo = await fetchQuota(e);
        setQuota(quotaInfo.quota);
    }


    const cardList = results.items.map(search => {
        console.log("Search = ", search);
        return (
            <SearchCard
                title={search.title}
                description={search.snippet}
                link={search.link}
            />
        )
    })

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
            <p>
                searchesRemaining: {quota}
            </p>

            <div>
                {cardList}        
            </div>

        </>
    );
};

export default Search;