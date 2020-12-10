import React from "react";
import { render } from "@testing-library/react";
import AnalyzeReactions from "main/pages/AnalyzeMessageData/AnalyzeReactions"

describe("Analyze Reactions tests", () => {
    test("renders without crashing", () => {
        render(<AnalyzeReactions />);
    });
});
