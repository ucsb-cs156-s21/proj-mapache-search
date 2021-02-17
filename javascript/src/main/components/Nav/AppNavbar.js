import React from "react";
import { Nav, Navbar, NavDropdown } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import AuthNav from "main/components/Nav/AuthNav";
import ProfileNav from "main/components/Nav/ProfileNav";
import useSWR from "swr";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "main/utils/fetch";

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
        {(isMember || isAdmin) &&
          <NavDropdown title="Channels">
            <NavDropdown.Item href="/member/channels">List Channels</NavDropdown.Item>
          </NavDropdown>
        }

          { isAdmin &&
              <NavDropdown title="Admin">
                  <NavDropdown.Item href="/admin">Maintain Admins</NavDropdown.Item>
                  <NavDropdown.Item href="/admin/slackUsers">Slack Users</NavDropdown.Item>
                  <NavDropdown.Item href="/admin/teams">Manage Teams</NavDropdown.Item>
                  <NavDropdown.Item href="/admin/students">Manage Students</NavDropdown.Item>
                  <NavDropdown.Item href="/admin/searchInfo">Search Information</NavDropdown.Item>
              </NavDropdown>
          }
          { (isMember || isAdmin)  &&
              <NavDropdown title="Search">
                  <NavDropdown.Item href="/member/messages/search">Message Search</NavDropdown.Item>
              </NavDropdown>
          }
        <LinkContainer to={"/about"}>
          <Nav.Link>About</Nav.Link>
        </LinkContainer>
        <ProfileNav />
          { (isAdmin || isMember) &&
              <NavDropdown title="Analyze Message Data">
                  <NavDropdown.Item href="/member/analyzemessages/reactions">Analyze Reactions</NavDropdown.Item>
                  <NavDropdown.Item href="/member/analyzemessages/countmessages">Count Messages By User</NavDropdown.Item>
                  <NavDropdown.Item href="/member/analyzemessages/messagehistogram">Histogram of Messages for a User</NavDropdown.Item>
                  <NavDropdown.Item href="/member/analyzemessages/searchmessages">Search Messages By User</NavDropdown.Item>
              </NavDropdown>
          }
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
