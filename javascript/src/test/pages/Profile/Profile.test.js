import React from "react";
import { render } from "@testing-library/react";
import Profile from "main/pages/Profile/Profile";
import { useAuth0 } from "@auth0/auth0-react";
import useSWR from "swr";
import { fetchWithToken } from "main/utils/fetch";
import userEvent from "@testing-library/user-event";
jest.mock("@auth0/auth0-react");
jest.mock("swr");
jest.mock("main/utils/fetch");

describe("Profile tests", () => {
  beforeEach(() => {
    useAuth0.mockReturnValue({
      user: {
        name: "test user",
        email: "test@test.com",
        picture: "https://picsum.photos/200",
      },
    });
    useSWR.mockReturnValue({
      data: {
        role : "Admin"
      }
    })
  });
  test("renders without crashing", () => {
    render(<Profile />);
  });

  test("renders loading when role hasn't been retrieved", () => {
    useSWR.mockReturnValue({});
    const { getByText } =render(<Profile />);
    expect(getByText("Loading role...")).toBeInTheDocument();
  });

  test("renders role correctly", () => {
    const { getByText } =render(<Profile />);
    expect(getByText("Admin")).toBeInTheDocument();
  });

  test("renders when submit button is pressed", () => {
    fetchWithToken.mockResolvedValue([]);
    const { getByText } = render(<Profile />);
    userEvent.click(getByText("Submit"));
    expect(fetchWithToken).toHaveBeenCalled();
  });
  test("when I click submit button, ", () => {
    fetchWithToken.mockImplementation(new Error());
    const { getByText } = render(<Profile />);
    userEvent.click(getByText("Submit"));
    expect(fetchWithToken).toHaveBeenCalled();
  });
  test("when I enter a token, the state for token changes", () => {
    const { getByPlaceholderText } = render(<Profile />);
    const enterToken = getByPlaceholderText("Enter your API token");
    userEvent.type(enterToken, "github");
    expect(enterToken.value).toBe("github");
  });
  test("renders token status correctly", () => {
    const { getByText } =render(<Profile />);
    expect(getByText("You do not have a valid API Token associated with your account! (Default will be used)")).toBeInTheDocument();
  });
});
