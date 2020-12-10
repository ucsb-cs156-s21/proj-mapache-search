import React, { Component, Fragment } from 'react';
import { render, getByText } from "@testing-library/react";
import MessageTableReaction from "main/components/ChannelMessageReaction/MessageTableReaction.js";

describe("MessageTableReactions tests", () => {
    test("it renders without crashing", () => {
        render(<MessageTableReaction/>);
    });
});