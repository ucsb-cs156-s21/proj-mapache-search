import React from "react";
import { fireEvent, getAllByTestId, render, getByText, toBeInTheDocument, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import HistogramOfMessagesByUser from "main/pages/AnalyzeMessageData/HistogramOfMessagesByUser";
import { fetchWithToken } from "main/utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import useSWR from "swr";
import weekComparator from "main/utils/weekComparator";
import renderer from 'react-test-renderer'

jest.mock("@auth0/auth0-react");

jest.mock("main/utils/fetch", () => ({
  fetchWithToken: jest.fn()
}));

describe("HistogramOfMessagesByUser tests", () => {
  beforeEach(() => {
    useAuth0.mockReturnValue({
      getAccessTokenSilently: "fakeToken"
    });
  });

  test("renders without crashing", () => {
    render(<HistogramOfMessagesByUser />);
  });

  test("renders without crashing on search for user", async () => {
    const { getByLabelText, getAllByTestId } = render(<HistogramOfMessagesByUser />);
    const selectSearchUser = getByLabelText("Enter a user");
    userEvent.type(selectSearchUser, "Test User");
    expect(selectSearchUser.value).toBe("Test User");
  })

  test("fetch with token is correctly called when user clicks button", async () => {
    const expectedURL = `/api/members/messages/usersearch?searchUser=Test User`;
    const options = {
      method: 'GET'
    }
    fetchWithToken.mockResolvedValue([]);
    const { getByText, getByLabelText } = render(<HistogramOfMessagesByUser />);
    const selectSearchUser = getByLabelText("Enter a user");
    userEvent.type(selectSearchUser, "Test User");
    const goButton = getByText("Go");
    userEvent.click(goButton);
    await waitFor(() => {
      expect(fetchWithToken).toHaveBeenCalledTimes(1);
      expect(fetchWithToken).toHaveBeenCalledWith(expectedURL, "fakeToken", options);
    })
  })

  test("sorting by week", () => {
    const weekOne = {
      "week": "startDate - endDate",
      "weekNum": 3,
      "count": 1
    }
    const weekTwo = {
      "week": "startDate - endDate",
      "weekNum": 4,
      "count": 1
    }
    const expected = -1;
    const result = weekComparator(weekOne, weekTwo);
    expect(result).toBe(expected);
    const expected2 = 1;
    const result2 = weekComparator(weekTwo, weekOne);
    expect(result2).toBe(expected2);
  })
});
