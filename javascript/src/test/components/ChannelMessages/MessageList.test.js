import React from "react";
import { render } from "@testing-library/react";
import MessageList from "main/components/ChannelMessages/MessageList";

describe("MessageList tests", () => {
    test("renders without crashing", () => {
        render(<MessageList messages={[]} />);
    });

    test("Displays username", () => {
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "<@U017218J9B3> has joined the channel",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<MessageList messages={[exampleMessage]}/>);
        const nameElement = getByText(/Test Person/);
        expect(nameElement).toBeInTheDocument();
    });
});
