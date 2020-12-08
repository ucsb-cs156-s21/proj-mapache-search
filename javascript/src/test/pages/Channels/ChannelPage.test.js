import React from "react";
import { render } from "@testing-library/react";
import ChannelPage from "main/pages/Channels/ListView";
import { useParams} from "react-router-dom";
import useSWR from "swr";
jest.mock("swr");
jest.mock("react-router-dom", () => {
    return {
        'useParams': jest.fn(),
    };
});

describe("ChannelPageList tests", () => {
    beforeEach(() => {
        useParams.mockReturnValue({
            'channel': 'test-channel'
        });
    });

    test("renders without crashing", () => {
        useSWR.mockReturnValue({});
        render(<ChannelPage />);
    });

    test("loads messages from the backend", () => {
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "<@U017218J9B3> has joined the channel",
            "channel": "section-6pm"
        }


        useSWR.mockReturnValue({
            'data': [exampleMessage]
        });

        const { getByText } = render(<ChannelPage />);
        const contentsElement = getByText(exampleMessage.text);
        expect(contentsElement).toBeInTheDocument();
    });
});
