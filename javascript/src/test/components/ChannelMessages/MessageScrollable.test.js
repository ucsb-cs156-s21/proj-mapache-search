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
        const {getByText} = render(<MessageScrollableView messages={[exampleMessage]}/>);
        const nameElement = getByText(/Someone said U017218J9B3/);
        expect(nameElement).toBeInTheDocument();
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
            "channel": "section-6pm",
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
        const {getAllByText} = render(<MessageScrollableView messages={[exampleMessage]}/>);
        const nameElement = getAllByText(/Test Person/);
        expect(nameElement).toHaveLength(2);

    });

    test("Username not found", () => {
        useSWR.mockReturnValue({
            data: []
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
        setTimeout(function (){
            const nameElement = getByText(/@U017218J9B3/);
            expect(nameElement).toBeInTheDocument();
        }, 500)
    });

});
