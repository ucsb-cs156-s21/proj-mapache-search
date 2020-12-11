import CountMessagesByUser from "main/pages/AnalyzeMessageData/CountMessagesByUser";
import { render } from "@testing-library/react";
describe("CountMessagesByUser tests", () => {
    test("renders without crashing", () => {
      render(<CountMessagesByUser/>);
    });
});
