import React from "react";
import { render } from "@testing-library/react";
import AppNavbar from "main/components/Nav/AppNavbar";
import { useAuth0 } from "@auth0/auth0-react";
jest.mock("@auth0/auth0-react");
import { createMemoryHistory } from "history";
import { Router } from "react-router-dom";
import useSWR from "swr";
jest.mock("swr");

describe("AppNavbar tests", () => {
  beforeEach(() => {
    useAuth0.mockReturnValue({
      isAuthenticated: true,
      logout: jest.fn(),
      loginWithRedirect: jest.fn(),
    });
    useSWR.mockReturnValue({
      data: {
        role: "guest"
      }
    });
  });
  test("should render the correct brand text", () => {
    const history = createMemoryHistory();
    const { getByText } = render(
      <Router history={history}>
        <AppNavbar />
      </Router>
    );
    const brandElement = getByText(/Mapache Search/);
    expect(brandElement).toBeInTheDocument();
  });
  test("should have the correct links in the navbar", () => {
    const history = createMemoryHistory();
    const { getByText } = render(
      <Router history={history}>
        <AppNavbar />
      </Router>
    );

    const userInfoLink = getByText(/Profile/);
    expect(userInfoLink.href).toMatch("/profile");
  });

  test("should render admin links when admin", () => {
    useSWR.mockReturnValue({
      data: {
        role: "admin"
      }
    });
    const { getByText } = render(
      <Router history={createMemoryHistory()}>
        <AppNavbar />
      </Router>
    );
    expect(getByText("Admin")).toBeInTheDocument();
  });

  test("should not render admin links when not admin", () => {
    useSWR.mockReturnValue({
      data: {
        role: "member"
      }
    });
    const { queryByText } = render(
      <Router history={createMemoryHistory()}>
        <AppNavbar />
      </Router>
    );
    expect(queryByText("Admin")).toBe(null);
  });
});
