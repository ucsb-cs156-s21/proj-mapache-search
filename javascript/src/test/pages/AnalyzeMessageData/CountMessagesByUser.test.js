import React from "react";
import { render } from "@testing-library/react";
import CountMessagesByUser from "../../../../src/main/pages/AnalyzeMessageData/CountMessagesByUser"

describe("Count Messages By User tests", () => {
    test("renders without crashing", () => {
        render(<CountMessagesByUser />);
    });
});
