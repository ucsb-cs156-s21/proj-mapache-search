import React from "react";
import { render } from "@testing-library/react";
import HistogramOfMessagesByUser from "main/pages/AnalyzeMessageData/HistogramOfMessagesByUser"

describe("Histogram Of Messages By User tests", () => {
    test("renders without crashing", () => {
        render(<HistogramOfMessagesByUser />);
    });
});
