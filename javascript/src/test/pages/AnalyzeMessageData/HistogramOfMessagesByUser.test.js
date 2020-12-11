import HistogramOfMessagesByUser from "../../../main/pages/AnalyzeMessageData/HistogramOfMessagesByUser";
import { render } from "@testing-library/react";
describe("HistogramOfMessagesByUser tests", () => {
    test("renders without crashing", () => {
      render(<HistogramOfMessagesByUser/>);
    });
});
