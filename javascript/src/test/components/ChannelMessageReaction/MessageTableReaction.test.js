import React, { Component, Fragment } from 'react';
import { render, getByText } from "@testing-library/react";
import MessageTableReaction from "main/components/ChannelMessageReaction/MessageTableReaction.js";

describe("MessageTableReactions tests", () => {
    test("it renders without crashing", () => {
        render(<MessageTableReaction/>);
    });

    test("row.message_reactions is null", () => {
        const { getByText } = render(<MessageTableReaction/>);
        const SubjectFormattter = getByText(//)
        expect().toBe("");
    });

    test()
});