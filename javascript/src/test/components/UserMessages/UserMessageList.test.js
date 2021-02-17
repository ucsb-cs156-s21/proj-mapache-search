import React from 'react';
import { render } from "@testing-library/react";
import UserMessageList from "main/components/UserMessages/UserMessageList";

describe("UserMessageList tests", () => {
    test("it renders without crashing", () => {
        render(<UserMessageList/>);
    });
});