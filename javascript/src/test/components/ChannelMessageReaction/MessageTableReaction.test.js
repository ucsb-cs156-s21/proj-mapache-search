import React from 'react';
import { render } from "@testing-library/react";
import MessageTableReaction from "main/components/ChannelMessageReaction/MessageTableReaction.js";
import useSWR from "swr";
jest.mock("swr");
jest.mock("react-router-dom", () => {
    return {
        'useParams': jest.fn(),
    };
});
describe("MessageTableReactions tests", () => {
    test("it renders without crashing", () => {
        useSWR.mockReturnValue({
            data:  []
        });
        render(<MessageTableReaction/>);
    });
    test("row.message_reactions is not null", () => {
        useSWR.mockReturnValue({
            data:  []
        });
        const testMessages = [{
            "user_profile": {
                "real_name": "test-user"
            },
            "text": "test-text",
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        const testUser = getByText(/test-user/);
        const testText = getByText(/test-text/);
        const testCount = getByText(/1/);
        expect(testUser).toBeInTheDocument();
        expect(testText).toBeInTheDocument();
        expect(testCount).toBeInTheDocument();
    });
    test("row.message_reactions is formatted (user mention)", () => {
        useSWR.mockReturnValue({
            data: [{
                id: "U017218J9B3",
                real_name: "test-person"
            }]
        });
        const testMessages = [{
            "user": "U017218J9B3",
            "text": "<@U017218J9B3> has joined the channel",
            "user_profile": {
                "real_name": "Test Person"
            },
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        const testUser = getByText(/Test Person/);
        const testText = getByText(/@test-person has joined the channel/);
        const testCount = getByText(/1/);
        expect(testUser).toBeInTheDocument();
        expect(testText).toBeInTheDocument();
        expect(testCount).toBeInTheDocument();
    });
    test("row.message_reactions is formatted (channel mention)", () => {
        useSWR.mockReturnValue({
            data: [{
                id: "U017218J9B3",
                real_name: "test-person"
            }]
        });
        const testMessages = [{
            "user": "U017218J9B3",
            "text": "<@U017218J9B3> has joined the channel",
            "user_profile": {
                "real_name": "Test Person"
            },
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        const testUser = getByText(/Test Person/);
        const testText = getByText(/@test-person has joined the channel/);
        const testCount = getByText(/1/);
        expect(testUser).toBeInTheDocument();
        expect(testText).toBeInTheDocument();
        expect(testCount).toBeInTheDocument();
    });
    test("Username not found", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "<@U017218J9B3> has joined the channel",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            },
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        const nameElement = getByText(/@U017218J9B3/);
        expect(nameElement).toBeInTheDocument();
    });
    test("Unembedded https links are clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            "user": "U017218J9B3",
            "text": "Office hours at <https://ucsb.zoom.us/j/89220034995?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09>",
            "user_profile": {
                "real_name": "Test Person"
            },
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        const linkElement = getByText(/https:\/\/ucsb.zoom.us\/j\/89220034995\?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09/);
        expect(linkElement.href).toEqual("https://ucsb.zoom.us/j/89220034995?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09");
        
    });

    test("Unembedded email links are clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Email me at <mailto:test@ucsb.edu>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            },
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        const linkElement = getByText(/mailto:test@ucsb.edu/);
        expect(linkElement.href).toEqual("mailto:test@ucsb.edu");
        
    });

    test("Unembedded phone links are clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Call me at <tel:+01234567890>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            },
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        const linkElement = getByText("tel:+01234567890");
        expect(linkElement.href).toEqual("tel:+01234567890");
        
    });

    test("Embedded https links are clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Office hours at <https://ucsb.zoom.us/j/89220034995?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09|zoom>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            },
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        const linkElement = getByText(/zoom/);
        expect(linkElement.href).toEqual("https://ucsb.zoom.us/j/89220034995?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09");
        
    });

    test("Embedded email links are clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Email me at <mailto:test@ucsb.edu|this email>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            },
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        const linkElement = getByText(/this email/);
        expect(linkElement.href).toEqual("mailto:test@ucsb.edu");
        
    });

    test("Embedded phone links are clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Call me at <tel:+01234567890|+0 123 456 7890>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            },
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        const linkElement = getByText("+0 123 456 7890");
        expect(linkElement.href).toEqual("tel:+01234567890");
        
    });

    test("Channel links are clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "Please post in <#C01K1CR63MX|help-jpa02>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            },
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        const linkElement = getByText(/#help-jpa02/);
        expect(linkElement.href).toEqual("http://localhost/member/listViewChannels/help-jpa02");
        
    });    

    test("Brackets removed from Channel IDs and they are not links", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "<!channel> This is an announcement",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            },
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const {queryByText} = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        var bracketElement = queryByText(/<@channel>/);
        expect(bracketElement).toEqual(null);
        bracketElement = queryByText(/@channel/);
        expect(bracketElement).toBeInTheDocument();
        
    });

    test("Bracketed text that is not an http or mailto link is not clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "<!channel> This is an announcement",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            },
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        const bracketElement = getByText(/@channel/);
        expect(bracketElement.getAttribute("href")).toEqual(null);
        
    });

    test("Channel tags begin with @ and are bolded", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "<!channel> This is an announcement",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            },
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        const bracketElement = getByText(/@channel/);
        expect(bracketElement).toHaveStyle("font-weight: bold");
        
    });

    test("Brackets removed from elements that are not links", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            "type": "message",
            "subtype": "channel_join",
            "ts": "1594143066.000200",
            "user": "U017218J9B3",
            "text": "<!channel> This is an announcement <testing>",
            "channel": "section-6pm",
            "user_profile": {
                "real_name": "Test Person"
            },
            "message_reactions": [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const {queryByText} = render(<MessageTableReaction messages={testMessages} reaction = {testReaction}/>);
        var bracketElement = queryByText(/<testing>/);
        expect(bracketElement).toEqual(null);
        bracketElement = queryByText(/testing/);
        expect(bracketElement).toBeInTheDocument();
        
    });
});


