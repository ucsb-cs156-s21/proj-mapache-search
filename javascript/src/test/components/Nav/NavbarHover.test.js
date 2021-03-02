import React from "react";
import { render, screen, fireEvent, act} from "@testing-library/react";
import NavbarHover from "../../../main/components/Nav/NavbarHover";

describe("NavbarHover tests", () => {
    test("check dropdown menu items when hovering", async () => {
        const AdminPages = [
            {link:"/admin", name:"Maintain Admins",},
            {link:"/admin/slackUsers", name:"Slack Users",},
            {link:"/admin/teams", name:"Manage Teams",},
            {link:"/admin/students", name:"Manage Students",},
            {link:"/admin/searchInfo", name:"Search Information",},
        ];
        
        await act( async () => {
            render(<NavbarHover title="Admin" items={AdminPages} />);
            fireEvent.mouseOver(screen.getByText("Admin"));
        });
        
        expect(screen.getByText("Maintain Admins")).toBeInTheDocument();
        expect(screen.getByText("Slack Users")).toBeInTheDocument();
        expect(screen.getByText("Manage Teams")).toBeInTheDocument();
        expect(screen.getByText("Manage Students")).toBeInTheDocument();
        expect(screen.getByText("Search Information")).toBeInTheDocument();
    });
});