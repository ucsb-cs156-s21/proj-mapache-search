import React from 'react';
import { render } from "@testing-library/react";
import UserMessageList from "main/components/UserMessages/UserMessageList";

describe("UserMessageList tests", () => {
    test("it renders without crashing", () => {
        render(<UserMessageList/>);
    });

    test("Default message display", () => {
        useSWR.mockReturnValue({
            data: [{
                id: "U017218J9B3",
                real_name: "Test Person"
            }]
        });
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Someone said U017218J9B3",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<UserMessageList messages={[exampleMessage]}/>);
        const nameElement = getByText(/Someone said U017218J9B3/);
        expect(nameElement).toBeInTheDocument();
    });

    test("Channel tags begin with @ and are bolded", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "<!channel> This is an announcement",
            "channel": "section-7pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<UserMessageList messages={[exampleMessage]}/>);
        const bracketElement = getByText(/@channel/);
        expect(bracketElement).toHaveStyle("font-weight: bold");
        
    });

    test("Displays username in message", () => {
        useSWR.mockReturnValue({
            data: [{
                id: "U017218J9B3",
                real_name: "Test Person"
            }]
        });
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "<@U017218J9B3> has joined the channel",
            "channel": "section-7pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<MessageScrollableView messages={[exampleMessage]}/>);
        setTimeout(function (){
            const nameElement = getByText(/Test Person has joined the channel/);
            expect(nameElement).toBeInTheDocument();
        }, 500)

    });

    test("Embedded email links are clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Email me at <mailto:test@ucsb.edu|this email>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<MessageScrollableView messages={[exampleMessage]}/>);
        const linkElement = getByText(/this email/);
        expect(linkElement.href).toEqual("mailto:test@ucsb.edu");
        
    });
});