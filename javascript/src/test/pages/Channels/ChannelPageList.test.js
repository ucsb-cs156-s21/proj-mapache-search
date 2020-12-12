import React from "react";
import { render } from "@testing-library/react";
import ChannelPage from "main/pages/Channels/ChannelPage";
import { useParams } from "react-router-dom";
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
        render(<ChannelPageList />);
    });

    test("renders without crashing", () => {
        useSWR.mockReturnValue({});
        render(<ChannelPageScrollable />);
    });


    test("loads messages from the backend", () => {
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Someone has joined the channel",
            "channel": "section-6pm"
        }


        useSWR.mockReturnValue({
            'data': [exampleMessage]
        });

        const { getByText } = render(<ChannelPageList />);
        const contentsElement = getByText(exampleMessage.text);
        expect(contentsElement).toBeInTheDocument();
    });

    test("loads messages from the backend", () => {
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Someone has joined the channel",
            "channel": "section-6pm"
        }


        useSWR.mockReturnValue({
            'data': [exampleMessage]
        });

        const { getByText } = render(<ChannelPageScrollable />);
        const contentsElement = getByText(exampleMessage.text);
        expect(contentsElement).toBeInTheDocument();
    });
});