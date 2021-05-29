import React, { useState, useEffect } from "react";
import { useCallback } from 'react'
import { useAuth0 } from "@auth0/auth0-react";
import { Form, Button, Row, Col } from "react-bootstrap";
import { fetchWithToken } from "main/utils/fetch";
import SearchCard from "main/components/SearchCard/SearchCard";

import { useToasts } from "react-toast-notifications";


const Search = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const emptyQuery = {
        searchQuery: ""
    }
    const emptyResults = { items: [] }


const { addToast } = useToasts();

    
    const fetchSearchResults = async (_event) => {
        const url = `/api/member/search/basic?searchQuery=${query.searchQuery}`;
        if(query.searchQuery === ""){
            addToast("Please enter search query.", { appearance: "error" });
            return emptyResults;
        }
        try {
            const result = await fetchWithToken(url, getToken, {
                method: "GET",
                headers: {
                    "content-type": "application/json",
                },
            });
            console.log(`result=${JSON.stringify(result)}`)
            if(result.items){
                return result;
            }

            addToast(`Error: ${JSON.stringify(result)}`, { appearance: "error" });

            return emptyResults;
        } catch (err) {

            addToast(`Error: ${err}`, { appearance: "error" });
            console.log(`err=${err}`)
            
            return emptyResults;
        }
    };
    const fetchQuota = useCallback(async (_event) => {
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
    },[getToken])

    
    const [query, setQuery] = useState(emptyQuery);
    const [results, setResults] = useState(emptyResults);

    const [quota, setQuota] = useState(0);

    useEffect(() => {
        async function getQuota() {
            const quotaInfo = await fetchQuota();
            
            if(quotaInfo && quotaInfo.quota)
                setQuota(quotaInfo.quota);
        };
        getQuota();
    }, [fetchQuota]);
   

    const handleOnSubmit = async (e) => {
        e.preventDefault();
        const answer = await fetchSearchResults(e);

        console.log(`answer=${JSON.stringify(answer)}`);

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
                    <Col sm={{ span: 10, offset: 7 }}>
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