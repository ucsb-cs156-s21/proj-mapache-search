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
        const selectStartDate = getByLabelText("Start date");
        const selectEndDate = getByLabelText("End date");
        userEvent.type(selectStartDate, '2021-05-04');
        userEvent.type(selectEndDate, '2021-05-05');
    });

    test("dateUser state changes when user types in date search bar", () => {
        const { getByLabelText } = render(<SearchBetweenDates />); 
        const selectStartDate = getByLabelText("Start date");
        const selectEndDate = getByLabelText("End date");
        userEvent.type(selectStartDate, '2021-05-04');
        userEvent.type(selectEndDate, '2021-05-05');
        expect(selectStartDate.value).toBe("2021-05-04");
        expect(selectEndDate.value).toBe("2021-05-05");
    });

    test("Fetch is called once and with correct url when user clicks on search button", async () => {
        const expectedURL = `/api/members/messages/datesearch?startDate=1620086400&endDate=1620172800`;
        const options = {
            method: 'GET',
        }
        fetchWithToken.mockResolvedValue([]);
        const { getByText, getByLabelText } = render(<SearchBetweenDates />);
        const startDate = getByLabelText("Start date");
        const endDate = getByLabelText("End date");
        userEvent.type(startDate, '2021-05-04');
        userEvent.type(endDate, '2021-05-05');
        const search = getByText("Search");
        userEvent.click(search);
        await waitFor(() => {
            expect(fetchWithToken).toHaveBeenCalledTimes(2);
            expect(fetchWithToken).toHaveBeenCalledWith(expectedURL, "fakeToken", options);
        });
    });  
});
