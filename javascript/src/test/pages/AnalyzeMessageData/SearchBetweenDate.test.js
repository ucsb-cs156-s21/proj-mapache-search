import React from "react";
import { waitFor, render } from "@testing-library/react";
import SearchBetweenDates from "main/pages/AnalyzeMessageData/SearchBetweenDates";
import userEvent from "@testing-library/user-event";
import { fetchWithToken } from "main/utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";


jest.mock("@auth0/auth0-react");

jest.mock("main/utils/fetch", () => ({
  fetchWithToken: jest.fn()
}));

describe("SearchBetweenDates tests", () => {

    beforeEach(() => {
        useAuth0.mockReturnValue({
          getAccessTokenSilently: "fakeToken"
        });
    });

    test("renders without crashing", () => {
        render(<SearchBetweenDates />);
    });

    test("renders without crashing on search", () => {
        const { getByLabelText } = render(<SearchBetweenDates />);
        const selectSearchDate1 = getByLabelText("Start date");
        const selectSearchDate2 = getByLabelText("End date");
        userEvent.type(selectSearchDate1, '2021-05-04');
        userEvent.type(selectSearchDate2, '2021-05-05');
    });

    test("dateUser state changes when user types in date search bar", () => {
        const { getByLabelText } = render(<SearchBetweenDates />); 
        const selectSearchDate1 = getByLabelText("Start date");
        const selectSearchDate2 = getByLabelText("End date");
        userEvent.type(selectSearchDate1, '2021-05-04');
        userEvent.type(selectSearchDate2, '2021-05-05');
        expect(selectSearchDate1.value).toBe("2021-05-04");
        expect(selectSearchDate2.value).toBe("2021-05-05");
    });

    test("Fetch is called once and with correct url when user clicks on search button", async () => {
        const expectedURL = `/api/members/messages/datesearch?searchDate=1620086400&searchDate2=1620172800`;
        const options = {
            method: 'GET',
        }
        fetchWithToken.mockResolvedValue([]);
        const { getByText, getByLabelText } = render(<SearchBetweenDates />);
        const selectSearchDate1 = getByLabelText("Start date");
        const selectSearchDate2 = getByLabelText("End date");
        userEvent.type(selectSearchDate1, '2021-05-04');
        userEvent.type(selectSearchDate2, '2021-05-05');
        const search = getByText("Search");
        userEvent.click(search);
        await waitFor(() => {
            expect(fetchWithToken).toHaveBeenCalledTimes(2);
            expect(fetchWithToken).toHaveBeenCalledWith(expectedURL, "fakeToken", options);
        });
    });  
});
