import React, { useState } from "react";
import { Jumbotron } from "react-bootstrap";
import { useAuth0 } from "@auth0/auth0-react";
import { Redirect } from "react-router-dom";
import { Form, Button, Row, Col, Container } from "react-bootstrap";
import JSONPrettyCard from "main/components/Utilities/JSONPrettyCard";
import { fetchWithToken } from "main/utils/fetch";
import { render } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Search from "../../../main/pages/Search/Search.js";

import SearchParameters from "../../../../../main/java/edu/ucsb/mapache/models/SearchParameters.java"

describe("Search tests", () => {
    test("it renders without crashing", () => {
        render(<Search/>);
    });

    /*test("when I enter a query, the state for query changes", () => {
        const { getQuery } = render(<Search />);
        const enterQuery = setQuery("enter-query")
        userEvent.type(enterQuery, "github");
        expect(enterQuery.value).toBe("github");
    });*/
});
