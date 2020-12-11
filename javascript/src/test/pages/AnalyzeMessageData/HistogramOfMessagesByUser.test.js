import HistogramOfMessagesByUser from "../../../main/pages/AnalyzeMessageData/HistogramOfMessagesByUser";
import { waitFor, render } from "@testing-library/react";

describe("HistogramOfMessagesByUser tests", () => {
    test("renders without crashing", () => {
      render(<HistogramOfMessagesByUser/>);
    });
});
