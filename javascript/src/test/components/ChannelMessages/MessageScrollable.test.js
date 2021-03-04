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

    test("Unembedded https links are clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Office hours at <https://ucsb.zoom.us/j/89220034995?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<MessageScrollableView messages={[exampleMessage]}/>);
        setTimeout(function (){
            const linkElement = getByText(/https:\/\/ucsb.zoom.us\/j\/89220034995\?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09/);
            expect(linkElement.href).toMatch("https://ucsb.zoom.us/j/89220034995?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09");
        }, 500)
    });

    test("Unembedded email links are clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Email me at <mailto:test@ucsb.edu>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<MessageScrollableView messages={[exampleMessage]}/>);
        setTimeout(function (){
            const linkElement = getByText(/mailto:test@ucsb.edu/);
            expect(linkElement.href).toMatch("mailto:test@ucsb.edu");
        }, 500)
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
            "text": "Office hours at <https://ucsb.zoom.us/j/89220034995?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09|zoom>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<MessageScrollableView messages={[exampleMessage]}/>);
        setTimeout(function (){
            const linkElement = getByText(/zoom/);
            expect(linkElement.href).toMatch("https://ucsb.zoom.us/j/89220034995?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09");
        }, 500)
    });

    test("Embedded email links are clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Email me at <mailto:test@ucsb.edu|this email>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<MessageScrollableView messages={[exampleMessage]}/>);
        setTimeout(function (){
            const linkElement = getByText(/this email/);
            expect(linkElement.href).toMatch("mailto:test@ucsb.edu");
        }, 500)
    });

    test("Brackets removed from elements that are not links", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "<!channel> This is an announcement",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<MessageScrollableView messages={[exampleMessage]}/>);
        setTimeout(function (){
            var bracketElement = getByText(/<!channel>/);
            expect(bracketElement).toHaveLength(0);
            bracketElement = getByText(/!channel/);
            expect(bracketElement).toBeInTheDocument();
        }, 500)
    });

    test("Bracketed text that is not an http or mailto link is not clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "<!channel> This is an announcement",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<MessageScrollableView messages={[exampleMessage]}/>);
        setTimeout(function (){
            const bracketElement = getByText(/!channel/);
            expect(bracketElement.href).toMatch(null);
        }, 500)
    });

    test("Date and time render correctly", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const exampleMessage = {
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "<!channel> This is an announcement",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            }
        }
        const {getByText} = render(<MessageScrollableView messages={[exampleMessage]}/>);
        setTimeout(function (){
            const date = getByText(/2020-07-07 10:31:06/);
            expect(date).toBeInTheDocument();
        }, 500)
    });

    test("Messages sorted in chronological order", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const exampleMessage = [
            {
                "type": "message3",
                "subtype": "channel_join",
                "ts": "1611257072.000900", //Jan 21, 2021, 11:24:32
                "user": "U017218J9B3",
                "text": "<!channel> This is an announcement",
                "channel": "section-6pm",
                "user_profile": {
                    "real_name": "Test Person"
                }
            },
            {     
                "type": "message1",
                "subtype": "channel_join",
                "ts": "1611875002.014300", //Jan 28 2021, 15:03:22
                "user": "U017218J9B3",
                "text": "<!channel> This is an announcement",
                "channel": "section-6pm",
                "user_profile": {
                    "real_name": "Test Person"
                }
            },
            {
                "type": "message2",
                "subtype": "channel_join",
                "ts": "1611867078.013800", //Jan 28, 2021, 12:51:18
                "user": "U017218J9B3",
                "text": "<!channel> This is an announcement",
                "channel": "section-6pm",
                "user_profile": {
                    "real_name": "Test Person"
                }
            }
        ]
        const {getAllByText} = render(<MessageScrollableView messages={exampleMessage}/>);
        setTimeout(function (){
            const dates = getAllByText(/2021.{15}/);
            const expectedDate1 = "2021-01-28 15:03:22";
            const expectedDate2 = "2021-01-28 12:51:18";
            const expectedDate3 = "2021-01-21 11:24:32";
            expect(dates[0]).toEqual(expectedDate1);
            expect(dates[1]).toEqual(expectedDate2);
            expect(dates[2]).toEqual(expectedDate3);
        }, 500)
    });

});
