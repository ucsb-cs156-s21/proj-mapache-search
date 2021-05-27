import React from "react";
import { render } from "@testing-library/react";
import HistogramOfMessagesByUser from "main/pages/AnalyzeMessageData/HistogramOfMessagesByUser";

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
    render(<HistogramOfMessagesByUser/>);
  });

  test("renders without crashing on search for user", async () => {
    const { getByLabelText } = render(<HistogramOfMessagesByUser />);
    const selectSearchUser = getByLabelText("Enter a user");
    userEvent.type(selectSearchUser, "Test User");
    expect(selectSearchUser.value).toBe("Test User");
  })

  test("Fetch with token is called correctly when user clicks button", async () => {
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
