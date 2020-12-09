import React from "react";
import { render } from "@testing-library/react";
import MessageScrollableView from "main/components/ChannelMessages/MessageScrollableView";
import useSWR from "swr";
jest.mock("swr");
jest.mock("react-router-dom", () => {
    return {
        'useParams': jest.fn(),
    };
});
describe("MessageScrollableView tests", () => {
    test("renders without crashing", () => {
        useSWR.mockReturnValue({
            data: []
        });
        render(<MessageScrollableView messages={[]} channel={""} />);
    });

    test("Displays username", () => {
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
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<MessageScrollableView messages={[exampleMessage]}/>);
        const nameElement = getByText(/Test Person/);
        expect(nameElement).toBeInTheDocument();
    });

});
