import React from "react";
import { waitFor, render } from "@testing-library/react";
import SearchMessagesByUser from "main/pages/AnalyzeMessageData/SearchMessagesByUser";
import userEvent from "@testing-library/user-event";
import { fetchWithToken } from "main/utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";


jest.mock("@auth0/auth0-react");

jest.mock("main/utils/fetch", () => ({
  fetchWithToken: jest.fn()
}));

describe("SearchMessagesByUser tests", () => {

    beforeEach(() => {
        useAuth0.mockReturnValue({
          getAccessTokenSilently: "fakeToken"
        });
    });

    test("renders without crashing", () => {
        render(<SearchMessagesByUser />);
    });

    test("renders without crashing on search", () => {
        const { getByLabelText } = render(<SearchMessagesByUser />);
        const selectSearchUser = getByLabelText("Search User");
        userEvent.type(selectSearchUser, "springboot");
    });

    test("searchUser state changes when user types in search bar", () => {
        const { getByLabelText } = render(<SearchMessagesByUser />); 
        const selectSearchUser = getByLabelText("Search User");
        userEvent.type(selectSearchUser, "Test Jones");
        expect(selectSearchUser.value).toBe("Test Jones");
    });

    test("Fetch is called once and with correct url when user clicks on search button", async () => {
        const expectedURL = `/api/members/messages/usersearch?searchUser=Test Jones`;
        const options = {
            method: 'GET',
        }
        fetchWithToken.mockResolvedValue([]);
        const { getByText, getByLabelText } = render(<SearchMessagesByUser />);
        const selectSearchUser = getByLabelText("Search User");
        userEvent.type(selectSearchUser, "Test Jones");
        const search = getByText("Search");
        userEvent.click(search);
        await waitFor(() => {
            expect(fetchWithToken).toHaveBeenCalledTimes(1);
            expect(fetchWithToken).toHaveBeenCalledWith(expectedURL, "fakeToken", options);
        });
    });
});