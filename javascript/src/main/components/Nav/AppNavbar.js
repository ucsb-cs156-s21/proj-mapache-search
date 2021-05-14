import React from "react";
import { Accordion, Nav, Navbar } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import { Link } from "react-router-dom";
import AuthNav from "main/components/Nav/AuthNav";
import useSWR from "swr";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "main/utils/fetch";
import NavbarHover from "../Nav/NavbarHover";
import AccordionItem from "./AccordionItem";

export function NavbarContents({ isAdmin, isMember, channelPages, adminPages, searchPages, dataPages }) {
  return (
    <>
      <AuthNav />

      <Accordion className="w-100 p-3">
        <AccordionItem title="Channels" items={channelPages} authorization={(isMember || isAdmin)} />
        <AccordionItem title="Admin" items={adminPages} authorization={isAdmin} />
        <AccordionItem title="Slack Search" items={searchPages} authorization={(isMember || isAdmin)} />
        <AccordionItem title="Analyze Slack Data" items={dataPages} authorization={(isMember || isAdmin)} />
        <AccordionItem title="Google Search" link="/member/search" authorization={(isMember || isAdmin)} />
      </Accordion>
    </>
  );
}

function AppNavbar() {
  const { getAccessTokenSilently: getToken } = useAuth0();
  const { data: roleInfo } = useSWR(
    ["/api/myRole", getToken],
    fetchWithToken
  );
  const isAdmin = roleInfo ? roleInfo.role.toLowerCase() === "admin" : false;
  const isMember = roleInfo ? roleInfo.role.toLowerCase() === "member" : false;

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

  return (
    <>
      <Navbar className="navbar-custom p-0 d-none d-xl-inline flex-shrink-0" expand="xl">
        <Navbar.Collapse className="navbar-content flex-column py-5 bg-dark">
          <LinkContainer to="">
            <Navbar.Brand data-testid="brand" className="text-center m-0">
              <img src='/navbarlogo.png' alt="Mapache Search icon" className="mx-auto" width="60%" />
              <h3 className="text-center py-3 text-light">Mapache Search</h3>
            </Navbar.Brand>
          </LinkContainer>
          <NavbarContents
            isAdmin={isAdmin}
            isMember={isMember}
            searchPages={searchPages}
            dataPages={dataPages}
            channelPages={channelPages}
            adminPages={adminPages}
          />
        </Navbar.Collapse>
      </Navbar>

      <Navbar className="d-flex justify-content-start d-xl-none" expand="xl" bg="dark" variant="dark">
        <Navbar.Toggle />
        <Navbar.Brand>
          <Link className="brand px-3 text-light" to="/">Mapache Search</Link>
        </Navbar.Brand>
        <Navbar.Collapse className="bg-dark">
          <NavbarContents
            isAdmin={isAdmin}
            isMember={isMember}
            searchPages={searchPages}
            dataPages={dataPages}
            channelPages={channelPages}
            adminPages={adminPages}
          />
        </Navbar.Collapse>
      </Navbar>
    </>
  );
}

export default AppNavbar;
