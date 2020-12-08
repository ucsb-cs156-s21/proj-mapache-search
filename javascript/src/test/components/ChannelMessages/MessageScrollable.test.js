import React from "react";
import { render } from "@testing-library/react";
import MessageScrollableView from "main/components/ChannelMessages/MessageScrollableView";

describe("MessageScrollableView tests", () => {
    test("renders without crashing", () => {
        render(<MessageScrollableView messages={[]} channel={""} />);
    });
});
