import React from "react";
import { render } from "@testing-library/react";
import SearchMessagesByUser from "../../../../src/main/pages/AnalyzeMessageData/SearchMessagesByUser"

describe("Search Messages By User tests", () => {
    test("renders without crashing", () => {
        render(<SearchMessagesByUser />);
    });
});
