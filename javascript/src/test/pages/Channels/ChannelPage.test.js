import React from "react";
import { render } from "@testing-library/react";
import ChannelPageList from "main/pages/Channels/ChannelPageList";
import ChannelPageScrollable from "main/pages/Channels/ChannelPageScrollable";
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

    test("Message is scrolled into view", () => {
        Object.defineProperty(window, 'location', {
            get() {
                return { hash: "#U017218J9B31594143066.000200" };
            },
        });
        const mockScrollIntoView = jest.fn();
        HTMLElement.prototype.scrollIntoView = mockScrollIntoView;
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

        render(<ChannelPageList />);
        expect(mockScrollIntoView).toBeCalledWith({block: "center"});
    });

    test("Message in context is highlighted", () => {
        Object.defineProperty(window, 'location', {
            get() {
                return { hash: "#U017218J9B31594143066.000200" };
            },
        });
        const mockScrollIntoView = jest.fn();
        HTMLElement.prototype.scrollIntoView = mockScrollIntoView;
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
        expect(window.location.hash).toEqual("#U017218J9B31594143066.000200");
        expect(contentsElement.parentNode.parentNode.className).toEqual("focused");
    });
});

