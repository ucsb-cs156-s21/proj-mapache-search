import React from "react";
import { fetchWithToken } from "main/utils/fetch";
import { render, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Search from "../../../main/pages/Search/Search.js";
import { useToasts } from "react-toast-notifications";

jest.mock("main/utils/fetch");
jest.mock("react-toast-notifications", () => ({
    useToasts: jest.fn(),
  }));
beforeEach(() => {    useToasts.mockReturnValue({
    addToast: jest.fn(),
  });
});
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
                
                if(url === "/api/member/search/quota")
                    return {quota:90};
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
                
                if(url === "/api/member/search/quota")
                    return {quota:90};
                else {
                    return fakeResults; 
                }
            }
        );
        const { getByText, getByLabelText } = render(<Search />);
        const searchTextBox = getByLabelText("Search");
        userEvent.type(searchTextBox, "spring boot");
        userEvent.click(getByText("Submit"));
        await waitFor(() => expect(fetchWithToken).toHaveBeenCalled());
      });
      
      test("renders when submit button is pressed and results do not have contents", async () => {
        fetchWithToken.mockImplementation(
            (url) => {
                
                if(url === "/api/member/search/quota")
                    return {quota:90};
                else {
                    return {}; 
                }
            }
        );
        const { getByText, getByLabelText } = render(<Search />);
        const searchTextBox = getByLabelText("Search");
        userEvent.type(searchTextBox, "spring boot");
        userEvent.click(getByText("Submit"));
        await waitFor(() => expect(fetchWithToken).toHaveBeenCalled());
      });
      
      test("renders when submit button is pressed and results is an error", async () => {
        fetchWithToken.mockImplementation(
            (url) => {
                
                if(url === "/api/member/search/quota")
                    return {quota:90};
                else {
                    throw new Error('result not found');
                }
            }
        );
        const { getByText, getByLabelText } = render(<Search />);
        const searchTextBox = getByLabelText("Search");
        userEvent.type(searchTextBox, "spring boot");
        userEvent.click(getByText("Submit"));
        await waitFor(() => expect(fetchWithToken).toHaveBeenCalled());
      });
      

    test("test what happens where there is an error fetching the search quota", async () => {
        fetchWithToken.mockImplementation(
            (url) => {
                
                if(url === "/api/member/search/quota")
                    throw new Error('error getting quota');
                else {
                    return fakeResults; 
                }
            }
        );
        const { getByText, getByLabelText } = render(<Search />);
        const searchTextBox = getByLabelText("Search");
        userEvent.type(searchTextBox, "spring boot");
        userEvent.click(getByText("Submit"));
        await waitFor(() => expect(fetchWithToken).toHaveBeenCalled());
      });

    

});
