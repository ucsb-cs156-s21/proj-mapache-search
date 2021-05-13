import React from "react";
import { Accordion, Nav, Navbar } from "react-bootstrap";
import { LinkContainer } from "react-router-bootstrap";
import AuthNav from "main/components/Nav/AuthNav";
import useSWR from "swr";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "main/utils/fetch";
import NavbarHover from "../Nav/NavbarHover";
import AccordionItem from "./AccordionItem";

export function PureNavbar({ isAdmin, isMember, channelPages, adminPages, searchPages, dataPages }) {
  return (
    <Navbar className="navbar-custom" expand="lg" bg="dark" variant="dark">
      <Navbar.Toggle />
      <Navbar.Collapse className="navbar-content flex-column pt-5">
        <LinkContainer to="">
          <Navbar.Brand data-testid="brand" className="brand text-center">
            <img src='/navbarlogo.png' alt="Mapache Search icon" className="mx-auto" />
            <h3 className="text-center py-3">Mapache Search</h3>
          </Navbar.Brand>
        </LinkContainer>

        <Accordion className="w-100 px-3">
          <AccordionItem title="Channels" items={channelPages} />
          <AccordionItem title="Admin" items={adminPages} />
          <AccordionItem title="Slack Search" items={searchPages} authorization={(isMember || isAdmin)} />
          <AccordionItem title="Analyze Slack Data" items={dataPages} authorization={(isMember || isAdmin)} />
          <AccordionItem title="Google Search" link="/member/search" authorization={(isMember || isAdmin)} />
        </Accordion>

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

  return (<PureNavbar isAdmin={isAdmin} isMember={isMember} searchPages={searchPages} dataPages={dataPages} channelPages={channelPages} adminPages={adminPages} />);
}

export default AppNavbar;
