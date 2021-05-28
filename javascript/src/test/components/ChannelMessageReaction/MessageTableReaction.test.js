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
            data: []
        });
        render(<MessageTableReaction/>);
    });

    test("row.message_reactions is not null", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            user: "test-user",
            text: "test-text",
            message_reactions: [{
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

    test("user id is replaced with username", () => {
        useSWR.mockReturnValue({
            data: [{
                id: "U017218J9B3",
                real_name: "Test Person"
            }]
        });
        const testMessages = [{
            user: "U017218J9B3",
            text: "<@U017218J9B3> has joined the channel",
            message_reactions: [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        const testUser = getByText(/Test Person/);
        const testText = getByText(/@Test Person has joined the channel/);
        expect(testUser).toBeInTheDocument();
        expect(testText).toBeInTheDocument();
    });

    test("Unembedded https links are clickable", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            user: "test-user",
            text: "Office hours at <https://ucsb.zoom.us/j/89220034995?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09>",
            message_reactions: [{
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
            user: "U017218J9B3",
            text: "Email me at <mailto:test@ucsb.edu>",
            message_reactions: [{
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
            user: "U017218J9B3",
            text: "Call me at <tel:+01234567890>",
            message_reactions: [{
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
            user: "U017218J9B3",
            text: "Office hours at <https://ucsb.zoom.us/j/89220034995?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09|zoom>",
            message_reactions: [{
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
            user: "U017218J9B3",
            text: "Email me at <mailto:test@ucsb.edu|this email>",
            message_reactions: [{
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
            user: "U017218J9B3",
            text: "Call me at <tel:+01234567890|+0 123 456 7890>",
            message_reactions: [{
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
            user: "U017218J9B3",
            text: "Please post in <#C01K1CR63MX|help-jpa02>",
            message_reactions: [{
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
            user: "U017218J9B3",
            text: "<!channel> This is an announcement",
            message_reactions: [{
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
            user: "U017218J9B3",
            text: "<!channel> This is an announcement",
            message_reactions: [{
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
            user: "U017218J9B3",
            text: "<!channel> This is an announcement",
            message_reactions: [{
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
            user: "U017218J9B3",
            text: "<!channel> This is an announcement <testing>",
            message_reactions: [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const {queryByText} = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        var bracketElement = queryByText(/<testing>/);
        expect(bracketElement).toEqual(null);
        bracketElement = queryByText(/testing/);
        expect(bracketElement).toBeInTheDocument();
        
    });

    test("User tags are styled using the correct css class", () => {
        useSWR.mockReturnValue({
            data: []
        });
        const testMessages = [{
            user: "U017218J9B3",
            text: "<@U017218J9B3> has joined the channel",
            message_reactions: [{
                count: 1,
                name: "test-name"
            }]
        }];
        const testReaction = "test-name";
        const { getByText } = render(<MessageTableReaction messages = {testMessages} reaction = {testReaction}/>);
        setTimeout(function () {
            const userTag = getByText(/@Test Person/);
            expect(userTag).toHaveClass("user-tag");
        }, 500)
        
    });


});


