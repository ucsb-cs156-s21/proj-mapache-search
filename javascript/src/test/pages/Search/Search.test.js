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
jest.mock("main/utils/fetch");

describe("Search tests", () => {
    test("it renders without crashing", () => {
        render(<Search/>);
    });

    test("when I enter a query, the state for query changes", () => {
        const { getByPlaceholderText } = render(<Search />);
        const enterQuery = getByPlaceholderText("type your query");
        userEvent.type(enterQuery, "github");
        expect(enterQuery.value).toBe("github");
    });

    test("renders when submit button is pressed", () => {
        fetchWithToken.mockResolvedValue([]);
        const { getByText } = render(<Search />);
        userEvent.click(getByText("Submit"));
        expect(fetchWithToken).toHaveBeenCalled();
    });

    test("when I click submit button, ", () => {
        fetchWithToken.mockImplementation(new Error());
        const { getByText } = render(<Search />);
        userEvent.click(getByText("Submit"));
        expect(fetchWithToken).toHaveBeenCalled();
    });

});
