import React from "react";
import { Nav, Navbar } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import AuthNav from "main/components/Nav/AuthNav";
import useSWR from "swr";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "main/utils/fetch";
import NavbarHover from "../Nav/NavbarHover";

export function PureNavbar({ isAdmin, isMember, channelPages, adminPages, searchPages, dataPages }) {
  return (
    <Navbar className="navbar-custom" expand="lg" bg="dark" variant="dark">
      <Navbar.Toggle />
      <Navbar.Collapse>
        <LinkContainer to="">
        <Navbar.Brand data-testid="brand">
          <p className="brand"><img className="brand" src={'/navbarlogo.png'}  alt="Mapache Search icon"  /></p>
          <p className="brand">Mapache Search</p>
          </Navbar.Brand>
        </LinkContainer>
        <Nav>
          {(isMember || isAdmin) &&
            <NavbarHover title="Channels" items={channelPages} />
          }
          {isAdmin &&
            <NavbarHover title="Admin" items={adminPages} />
          }
          {(isMember || isAdmin) &&
            <NavbarHover title="Slack Search" items={searchPages} />
          }
          {(isAdmin || isMember) &&
            <NavbarHover title="Analyze Slack Data" items={dataPages} />
          }
          {(isMember || isAdmin) &&
            <LinkContainer to="/member/search">
              <Nav.Link >Google Search</Nav.Link>
            </LinkContainer>
          }
        </Nav>
        <Navbar.Collapse className="justify-content-end">
          <AuthNav />
        </Navbar.Collapse>
      </Navbar.Collapse>
    </Navbar>
  );
}

function AppNavbar() {
  const { getAccessTokenSilently: getToken } = useAuth0();
  const { data: roleInfo } = useSWR(
    ["/api/myRole", getToken],
    fetchWithToken
  );
  const isAdmin = roleInfo && roleInfo.role.toLowerCase() === "admin";
  const isMember = roleInfo && roleInfo.role.toLowerCase() === "member";

  const channelPages = [
    { link: "/member/channels", name: "List Channels" },
  ];

  const adminPages = [
    {link:"/admin", name:"Maintain Admins",},
    {link:"/admin/slackUsers", name:"Slack Users",},
    {link:"/admin/teams", name:"Manage Teams",},
    {link:"/admin/students", name:"Manage Students",},
    {link:"/admin/searchInfo", name:"Search Information",},
    {link:"/admin/searchedTerms", name:"Searched Terms",},

  ];

  const searchPages = [
    { link: "/member/messages/search", name: "Slack Search" },
  ];

  const dataPages = [
    { link: "/member/analyzemessages/reactions", name: "Analyze Reactions", },
    { link: "/member/analyzemessages/countmessages", name: "Count Messages By User", },
    { link: "/member/analyzemessages/messagehistogram", name: "Histogram of Messages for a User", },
    { link: "/member/analyzemessages/searchmessages", name: "Search Messages By User", },
  ];

  return (<PureNavbar isAdmin={isAdmin} isMember={isMember} searchPages={searchPages} dataPages={dataPages} channelPages={channelPages} adminPages={adminPages} />);
}

export default AppNavbar;
