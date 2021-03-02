import React from "react";
import { render, fireEvent, act} from "@testing-library/react";
import NavbarHover from "../../../main/components/Nav/NavbarHover";

describe("NavbarHover tests", () => {
    test("check dropdown menu items when hovering", async () => {
        const AdminPages = [
            {
                link:"/admin", 
                name:"Maintain Admins",
            },
            {
                link:"/admin/slackUsers", 
                name:"Slack Users",
            },
            {
                link:"/admin/teams", 
                name:"Manage Teams",
            },
            {
                link:"/admin/students", 
                name:"Manage Students",
            },
            {
                link:"/admin/searchInfo", 
                name:"Search Information",
            },
        ];

        const { queryByText } = render(<NavbarHover data-testid="dropdown" title="Admin" items={AdminPages} />);
        expect(queryByText("Maintain Admins")).toBeNull();
        expect(queryByText("Slack Users")).toBeNull();
        expect(queryByText("Manage Teams")).toBeNull();
        expect(queryByText("Manage Students")).toBeNull();
        expect(queryByText("Search Information")).toBeNull();
        
        await act( async () => {
            fireEvent.mouseEnter(queryByText("Admin"));
        });
        
        expect(queryByText("Maintain Admins")).toBeInTheDocument();
        expect(queryByText("Slack Users")).toBeInTheDocument();
        expect(queryByText("Manage Teams")).toBeInTheDocument();
        expect(queryByText("Manage Students")).toBeInTheDocument();
        expect(queryByText("Search Information")).toBeInTheDocument();

        await act( async () => {
            fireEvent.mouseLeave(queryByText("Admin"));
        });

        expect(queryByText("Maintain Admins")).not.toBeVisible();
        expect(queryByText("Slack Users")).not.toBeVisible();
        expect(queryByText("Manage Teams")).not.toBeVisible();
        expect(queryByText("Manage Students")).not.toBeVisible();
        expect(queryByText("Search Information")).not.toBeVisible();
    });
});