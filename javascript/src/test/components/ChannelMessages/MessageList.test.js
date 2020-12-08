import React from "react";
import { render } from "@testing-library/react";
import MessageListView from "main/components/ChannelMessages/MessageListView";

describe("MessageListView tests", () => {
    test("renders without crashing", () => {
        render(<MessageListView messages={[]} />);
    });
});
