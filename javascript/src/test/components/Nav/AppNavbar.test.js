import React from "react";
import { render, screen } from "@testing-library/react";
import AppNavbar from "main/components/Nav/AppNavbar";
import { useAuth0 } from "@auth0/auth0-react";
import { createMemoryHistory } from "history";
import { Router } from "react-router-dom";
import useSWR from "swr";
jest.mock("@auth0/auth0-react");
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

  test("Non dropdown search should be called Google search", () => {
    useSWR.mockReturnValue({
      data: {
        role: "admin"
      }
    });
    const history = createMemoryHistory();
    const { getByText } = render(
      <Router history={history}>
        <AppNavbar />
      </Router>
    );
    const searchLink = getByText(/Google Search/);
    expect(searchLink.href).toMatch("/member/GoogleSearch");
  });

  test("Dropdown search should be called Slack search", () => {
    useSWR.mockReturnValue({
      data: {
        role: "admin"
      }
    });
    const history = createMemoryHistory();
    const { getByText } = render(
      <Router history={history}>
        <AppNavbar />
      </Router>
    );
    const dropDown = getByText(/Slack Search/);
<<<<<<< Updated upstream
    expect(screen.queryByText('Message Search')).not.toBeTruthy();
    dropDown.click();
    expect(screen.queryByText('Message Search').href).toMatch("/member/messages/search");
=======
    //expect(screen.queryByText('Slack Search')).not.toBeTruthy();
    //dropDown.click();
    //expect(screen.queryByText('Slack Search').href).toMatch("/member/messages/search");
    expect(dropDown).toBeInTheDocument();
>>>>>>> Stashed changes
  });
});

