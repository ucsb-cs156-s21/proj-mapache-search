import React, { Component, Fragment } from 'react';
import { render } from "@testing-library/react";
import UserChannelMessageList from "main/components/UserMessages/UserChannelMessageList"

describe("UserChannelMessageList tests", () => {

    test("it renders without crashing", () => {
        render(<UserChannelMessageList/>);
    });

    test("it renders when given messages", () => {
        const testMessages = [
            {
                channel: 'test-channel'
            },
            {
                channel: 'test-channel'
            },
            {
                channel: 'test-channel'
            }
        ];
        const { getByText } = render(<UserChannelMessageList messages={testMessages}/>);
        const channelElement = getByText(/test-channel/);
        const countElement = getByText(/3/);
        expect(channelElement).toBeInTheDocument();
        expect(countElement).toBeInTheDocument();
    });

    test("it renders when given null messages", () => {
        const { getByText } = render(<UserChannelMessageList messages={}/>);
        const channelElement = getByText(/Channel/);
        const countElement = getByText(/Message Count/);
        expect(channelElement).toBeInTheDocument();
        expect(countElement).toBeInTheDocument();
    });
});
