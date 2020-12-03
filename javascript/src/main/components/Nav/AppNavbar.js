import React from "react";
import { Nav, Navbar, NavDropdown } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import AuthNav from "main/components/Nav/AuthNav";
import ProfileNav from "main/components/Nav/ProfileNav";
import useSWR from "swr";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "main/utils/fetch";
import Search from "main/pages/Search/Search";

function AppNavbar() {
  const { getAccessTokenSilently: getToken } = useAuth0();
  const { data: roleInfo } = useSWR(
    ["/api/myRole", getToken],
    fetchWithToken
  );
  const isAdmin = roleInfo && roleInfo.role.toLowerCase() === "admin";
  const isMember = roleInfo && roleInfo.role.toLowerCase() === "member";

  return (
    <Navbar bg="dark" variant="dark">
      <LinkContainer to={""}>
        <Navbar.Brand data-testid="brand">Mapache Search</Navbar.Brand>
      </LinkContainer>
      <Nav>
          { isAdmin &&
              <NavDropdown title="Admin">
                  <NavDropdown.Item href="/admin">Maintain Admins</NavDropdown.Item>
                  <NavDropdown.Item href="/admin/slackUsers">Slack Users</NavDropdown.Item>
              </NavDropdown>
          }
          { (isMember || isAdmin)  &&
              <NavDropdown title="Channels">
                  <NavDropdown.Item href="/member/channels">List Channels</NavDropdown.Item>
              </NavDropdown>
          }
        <LinkContainer to={"/about"}>
            <Nav.Link>About</Nav.Link>
        </LinkContainer>
        <ProfileNav />
          { (isMember || isAdmin) && 
              <LinkContainer to={"/member/search"}>
                  <Nav.Link>Search</Nav.Link>
              </LinkContainer> 
          }
      </Nav>
      <Navbar.Collapse className="justify-content-end">
        <AuthNav />
      </Navbar.Collapse>
    </Navbar>
  );
}

export default AppNavbar;
