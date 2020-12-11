import React from "react";
import { fireEvent, getAllByTestId, render, getByText, toBeInTheDocument, waitFor } from "@testing-library/react";
import userEvent from "@testing-library/user-event";
import HistogramOfMessagesByUser from "main/pages/AnalyzeMessageData/HistogramOfMessagesByUser";
import { fetchWithToken } from "main/utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import useSWR from "swr";

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
});
