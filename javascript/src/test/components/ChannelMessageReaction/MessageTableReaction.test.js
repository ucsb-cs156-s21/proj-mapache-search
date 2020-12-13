import React, { Component, Fragment } from 'react';
import { render, getByText } from "@testing-library/react";
import MessageTableReaction from "main/components/ChannelMessageReaction/MessageTableReaction.js";

describe("MessageTableReactions tests", () => {
    test("it renders without crashing", () => {
        render(<MessageTableReaction/>);
    });
    test("row.message_reactions is not null", () => {
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
});


