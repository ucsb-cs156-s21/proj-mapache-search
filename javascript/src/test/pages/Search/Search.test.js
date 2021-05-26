import React from "react";
import { fetchWithToken } from "main/utils/fetch";
import { render, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Search from "../../../main/pages/Search/Search.js";
jest.mock("main/utils/fetch");

describe("Search tests", () => {
    const fakeResults = {
        items: [
            {
                title: "fakeTitle", snippet: "fakeSnippet", link: "https://example.org"
            }, {
                title: "fakeTitle1", snippet: "fakeSnippet2", link: "https://example3.org"
            }
        ]
    }
  

    test("when I enter a query, the state for query changes", () => {
        const { getByPlaceholderText } = render(<Search />);
        const enterQuery = getByPlaceholderText("type your query");
        userEvent.type(enterQuery, "github");
        expect(enterQuery.value).toBe("github");
    });

    test("renders when submit button is pressed and results are empty", async () => {

        fetchWithToken.mockImplementation(
            (url) => {
                console.log(`url=${url}`)
                if(url === "/api/member/search/quota")
                    return {quota:0};
                else {
                    return {items:[]}; 
                }
            }
        );
        const { getByText } = render(<Search />);
        userEvent.click(getByText("Submit"));
        await waitFor(() => expect(fetchWithToken).toHaveBeenCalled());
    });


    test("renders when submit button is pressed and results have contents", async () => {
        
        fetchWithToken.mockImplementation(
            (url) => {
                console.log(`url=${url}`)
                if(url === "/api/member/search/quota")
                    return {quota:0};
                else {
                    return fakeResults; 
                }
            }
        );
        const { getByText } = render(<Search />);
        userEvent.click(getByText("Submit"));
        await waitFor(() => expect(fetchWithToken).toHaveBeenCalled());
    });

    

});
