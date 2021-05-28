import React from "react";
import { fetchWithToken } from "main/utils/fetch";
import { render, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import Search from "../../../main/pages/Search/Search.js";
import { ToastProvider } from "react-toast-notifications";
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
    test("it renders without crashing", () => {
        render(<Search/>);
    });

    test("when I enter a query, the state for query changes", () => {
        const { getByPlaceholderText } = render(<Search />);
        const enterQuery = getByPlaceholderText("type your query");
        userEvent.type(enterQuery, "github");
        expect(enterQuery.value).toBe("github");
    });

    test("renders when submit button is pressed and results are empty", async () => {
        fetchWithToken.mockResolvedValue({items:[]});
        const { getByText } = render(<Search />);
        userEvent.click(getByText("Submit"));
        await waitFor(() => expect(fetchWithToken).toHaveBeenCalled());
    });


    test("renders when submit button is pressed and results have contents", async () => {
        fetchWithToken.mockResolvedValue(fakeResults);
        const { getByText } = render(<Search />);
        userEvent.click(getByText("Submit"));
        await waitFor(() => expect(fetchWithToken).toHaveBeenCalled());
    });

    test("when I click submit button, ", async() => {
        fetchWithToken.mockImplementation(new Error());
        const { getByText } = render(<Search />);
        userEvent.click(getByText("Submit"));
        await waitFor(() => expect(fetchWithToken).toHaveBeenCalled());
    });

});
