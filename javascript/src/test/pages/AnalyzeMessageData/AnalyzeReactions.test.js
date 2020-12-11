import React from "react";
import { waitFor, render } from "@testing-library/react";
import AnalyzeReactions from "main/pages/AnalyzeMessageData/AnalyzeReactions";
import userEvent from "@testing-library/user-event";
import { fetchWithToken } from "main/utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";


jest.mock("@auth0/auth0-react");

jest.mock("main/utils/fetch", () => ({
  fetchWithToken: jest.fn()
}));

describe("AnalyzeReactions tests", () => {

  beforeEach(() => {
    useAuth0.mockReturnValue({
      getAccessTokenSilently: "fakeToken"
    });
  });

  test("renders without crashing", () => {
    render(<AnalyzeReactions />);
  });

  test("renders without crashing on search", () => {
    const { getByLabelText } = render(<AnalyzeReactions />);
    const selectSearchReaction = getByLabelText("Search Reaction");
    userEvent.type(selectSearchReaction, "springboot");
  });

  test("searchReaction state changes when user types in search bar", () => {
      const { getByLabelText } = render(<AnalyzeReactions />); 
      const selectSearchReaction = getByLabelText("Search Reaction");
      userEvent.type(selectSearchReaction, "Test_Jones");
      expect(selectSearchReaction.value).toBe("Test_Jones");
  });

  test("Fetch is called once and with correct url when user clicks on search button", async () => {
      const expectedURL = `/api/members/messages/reactionsearch?searchReaction=Test_Jones`;
      const options = {
          method: 'GET',
      }
      fetchWithToken.mockResolvedValue([]);
      const { getByText, getByLabelText } = render(<AnalyzeReactions />);
      const selectSearchReaction = getByLabelText("Search Reaction");
      userEvent.type(selectSearchReaction, "Test_Jones");
      const search = getByText("Search");
      userEvent.click(search);
      await waitFor(() => {
          expect(fetchWithToken).toHaveBeenCalledTimes(1);
          expect(fetchWithToken).toHaveBeenCalledWith(expectedURL, "fakeToken", options);
      });
  });

  test("Fetch is called once and with correct url when user clicks on search button", async () => {
      const expectedURL = `/api/members/messages/reactionsearch?searchReaction=%2B1`
      const options = {
          method: 'GET',
      }
      fetchWithToken.mockResolvedValue([]);
      const { getByText, getByLabelText } = render(<AnalyzeReactions />);
      const selectSearchReaction = getByLabelText("Search Reaction");
      userEvent.type(selectSearchReaction, "+1");
      const search = getByText("Search");
      userEvent.click(search);
      await waitFor(() => {
          expect(fetchWithToken).toHaveBeenCalledTimes(1);
          expect(fetchWithToken).toHaveBeenCalledWith(expectedURL, "fakeToken", options);
      });
  });
});
