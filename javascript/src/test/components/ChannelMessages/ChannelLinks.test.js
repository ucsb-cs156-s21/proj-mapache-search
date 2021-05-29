import React from "react";
import { render } from "@testing-library/react";
import ChannelLinks from "main/components/ChannelMessages/ChannelLinks";
import useSWR from "swr";
jest.mock("swr");
jest.mock("react-router-dom", () => {
    return {
        'useParams': jest.fn(),
    };
});
describe("ChannelLinks tests", () => {

    test("renders without crashing", () => {
        useSWR.mockReturnValue({
            data:  []
        });
        render(<ChannelLinks messages={[]} />);
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
            "text": "<@U017218J9B3> look at this <https://youtu.be/dQw4w9WgXcQ>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getAllByText} = render(<ChannelLinks messages={[exampleMessage]}/>);
        const nameElement = getAllByText(/Test Person/);
        expect(nameElement).toHaveLength(1);
    });

    test("Only displays link", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Office hours at <https://youtu.be/dQw4w9WgXcQ>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {queryByText} = render(<ChannelLinks messages={[exampleMessage]}/>);
        const textElement = queryByText(/Office hours at/);
        expect(textElement).toBeNull();
        
    });

    test("Unembedded https links are clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Office hours at <https://youtu.be/dQw4w9WgXcQ>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<ChannelLinks messages={[exampleMessage]}/>);
        const linkElement = getByText(/https:\/\/youtu.be\/dQw4w9WgXcQ/);
        expect(linkElement.href).toEqual("https://youtu.be/dQw4w9WgXcQ");
        
    });


    test("Embedded https links are clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Office hours <https://youtu.be/dQw4w9WgXcQ|at this link>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<ChannelLinks messages={[exampleMessage]}/>);
        const linkElement = getByText(/at this link/);
        expect(linkElement.href).toEqual("https://youtu.be/dQw4w9WgXcQ");
        
    });

    test("Date and time render correctly", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1610833617.007900",
            "user": "U017218J9B3",
            "text": "<!channel> This is an announcement. Watch this <https://youtu.be/dQw4w9WgXcQ>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<ChannelLinks messages={[exampleMessage]}/>);
        // the timestamps in this test are in UTC because Jest tests
        // and Github tests use UTC time zone
        const date = getByText("2021-01-16 21:46:57");
        expect(date).toBeInTheDocument();
        
    });


});
