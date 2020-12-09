import React from "react";
import { render } from "@testing-library/react";
import MessageList from "main/components/ChannelMessages/MessageList";

describe("MessageList tests", () => {
    test("renders without crashing", () => {
        render(<MessageList messages={[]} />);
    });

    test("loads names", () => {
        const exampleMessage = {
            "user": "TESTID12345",
            "text": "This is <@TESTID12345> speaking",
            "ts": "1594143066.000200"
        }
        const exampleUser = {
            "id": "TESTID12345",
            "real_name": "Test Name"
        }
        const {getByText} = render(<MessageList messages={[exampleMessage]} users={[exampleUser]} />);
        const messageElement = getByText(/This is Test Name speaking/);
        expect(messageElement).toBeInTheDocument();
    });
});
