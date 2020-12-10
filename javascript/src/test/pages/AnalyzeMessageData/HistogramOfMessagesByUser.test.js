import React from "react";
import { render } from "@testing-library/react";
import HistogramOfMessagesByUser from "../../../main/pages/AnalyzeMessageData/HistogramOfMessagesByUser";

describe("HistogramOfMessagesByUser tests", () => {
    test("renders without crashing", () => {
      render(<HistogramOfMessagesByUser/>);
    });
});
