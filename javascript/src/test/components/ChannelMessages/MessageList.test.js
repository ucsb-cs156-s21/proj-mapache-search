import React from "react";
import { render } from "@testing-library/react";
import MessageList from "main/components/ChannelMessages/MessageList";

describe("MessageList tests", () => {
    test("renders without crashing", () => {
        render(<MessageList messages={[]} />);
    });
});
