import React from "react";
import { render, waitFor } from "@testing-library/react";
import { useAuth0 } from "@auth0/auth0-react";
jest.mock("@auth0/auth0-react");
import NewStudent from "main/pages/Students/NewStudent";
import userEvent from "@testing-library/user-event";

import useSWR from "swr";
jest.mock("swr");

import { useHistory } from 'react-router-dom';
jest.mock("react-router-dom", () => ({
  useHistory: jest.fn() 
}));

import { fetchWithToken } from "main/utils/fetch";
jest.mock("main/utils/fetch", () => ({
  fetchWithToken: jest.fn()
}));

describe("New Student page test", () => {
  const user = {
    name: "test user",
  };
  const getAccessTokenSilentlySpy = jest.fn();
  const student =
  {
    email: "jeff@ucsb.edu",
    teamName: "team-7pm-b",
  };

  beforeEach(() => {
    useAuth0.mockReturnValue({
      admin: undefined,
      getAccessTokenSilently: getAccessTokenSilentlySpy,
      user: user
    });
  });

  afterEach(() => {
    jest.clearAllMocks();
  });

  test("renders without crashing", () => {
    render(<NewStudent />);
  });

  test("clicking submit button redirects to students page", async () => {
    const pushSpy = jest.fn();
    useHistory.mockReturnValue({
      push: pushSpy
    });

    const { getByText } = render(
      <NewStudent />
    );

    const submitButton = getByText("Submit");
    expect(submitButton).toBeInTheDocument();
    userEvent.click(submitButton);

    await waitFor(() => expect(pushSpy).toHaveBeenCalledTimes(1));
    expect(pushSpy).toHaveBeenCalledWith("/admin/students");
    
  });

  test("clicking submit button redirects to home page on error", async () => {

    fetchWithToken.mockImplementation(() => {
      throw new Error();
    });

    const pushSpy = jest.fn();
    useHistory.mockReturnValue({
      push: pushSpy
    });

    const { getByText } = render(
      <NewStudent />
    );

    const submitButton = getByText("Submit");
    expect(submitButton).toBeInTheDocument();
    userEvent.click(submitButton);
  });
});
