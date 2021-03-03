import React from "react";
import { Nav, Navbar, NavDropdown } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import AuthNav from "main/components/Nav/AuthNav";
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
  
<<<<<<< Updated upstream
=======
  const ChannelPages = [
    {link:"/member/channels", name:"List Channels"},
  ];

  const AdminPages = [
    {link:"/admin", name:"Maintain Admins",},
    {link:"/admin/slackUsers", name:"Slack Users",},
    {link:"/admin/teams", name:"Manage Teams",},
    {link:"/admin/students", name:"Manage Students",},
    {link:"/admin/searchInfo", name:"Search Information",},
  ];

  const SearchPages = [
    {link:"/member/messages/search", name:"Slack Search"},
  ];

  const DataPages = [
    {link:"/member/analyzemessages/reactions", name:"Analyze Reactions",},
    {link:"/member/analyzemessages/countmessages", name:"Count Messages By User",},
    {link:"/member/analyzemessages/messagehistogram", name:"Histogram of Messages for a User",},
    {link:"/member/analyzemessages/searchmessages", name:"Search Messages By User",},
  ];

>>>>>>> Stashed changes
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
<<<<<<< Updated upstream
              <NavDropdown title="Slack Search">
                  <NavDropdown.Item href="/member/messages/search">Message Search</NavDropdown.Item>
              </NavDropdown>
=======
              <NavbarHover title="Slack Search" items={SearchPages} />
>>>>>>> Stashed changes
          }
        <LinkContainer to={"/about"}>
          <Nav.Link>About</Nav.Link>
        </LinkContainer>
          { (isAdmin || isMember) &&
<<<<<<< Updated upstream
              <NavDropdown title="Analyze Slack Data">    
                  <NavDropdown.Item href="/member/analyzemessages/reactions">Analyze Reactions</NavDropdown.Item>
                  <NavDropdown.Item href="/member/analyzemessages/countmessages">Count Messages By User</NavDropdown.Item>
                  <NavDropdown.Item href="/member/analyzemessages/messagehistogram">Histogram of Messages for a User</NavDropdown.Item>
                  <NavDropdown.Item href="/member/analyzemessages/searchmessages">Search Messages By User</NavDropdown.Item>
              </NavDropdown>
          }
          { (isMember || isAdmin) && 
              <LinkContainer to={"/member/GoogleSearch"}>   
                  <Nav.Link>Google Search</Nav.Link>     
=======
          <NavbarHover title="Analyze Slack Data" items={DataPages} />
          }
          { (isMember || isAdmin) && 
              <LinkContainer to={"/member/GoogleSearch"}>
                  <Nav.Link>Google Search</Nav.Link>
>>>>>>> Stashed changes
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