import React from "react";
import { Nav, Navbar } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import AuthNav from "main/components/Nav/AuthNav";
import useSWR from "swr";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "main/utils/fetch";
import NavbarHover from "../Nav/NavbarHover";
import { action } from "@storybook/addon-actions";

function AppNavbar({ appNavbar: { memberState }, onClick }) {
  const { getAccessTokenSilently: getToken } = useAuth0();
  const { data: roleInfo } = useSWR(
    ["/api/myRole", getToken],
    fetchWithToken
  );
  const isAdmin = roleInfo && roleInfo.role.toLowerCase() === "admin";
  const isMember = roleInfo && roleInfo.role.toLowerCase() === "member";
  
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

  return (
    <Navbar bg="dark" variant="dark" expand="lg">
      <Navbar.Toggle />
      <Navbar.Collapse>
      <LinkContainer to={""}>
        <Navbar.Brand data-testid="brand">Mapache Search</Navbar.Brand>
      </LinkContainer>
      <Nav roleInfo = {memberState}>
          {(isMember || isAdmin) &&
              <NavbarHover title="Channels" items={ChannelPages} onClick={action("clicked channels")}/>
          }
          { isAdmin &&
              <NavbarHover title="Admin" items={AdminPages} onClick={action("clicked admin")}/>
          }
          { (isMember || isAdmin)  &&
              <NavbarHover title="Slack Search" items={SearchPages} onClick={action("clicked slack search")}/>
          }
        <LinkContainer to={"/about"}>
          <Nav.Link onClick={action("clicked about")}>About</Nav.Link>
        </LinkContainer>
          { (isAdmin || isMember) &&
              <NavbarHover title="Analyze Slack Data" items={DataPages} onClick={action("clicked analyze slack data")}/>
          }
          { (isMember || isAdmin) && 
              <LinkContainer to={"/member/search"}>
                  <Nav.Link onClick={action("clicked google search")}>Google Search</Nav.Link>
              </LinkContainer> 
          }
      </Nav>
      <Navbar.Collapse className="justify-content-end">
        <AuthNav/>
      </Navbar.Collapse>
      </Navbar.Collapse>
    </Navbar>
  );
}

export default AppNavbar;