import React from "react";
import { render } from "@testing-library/react";
import About from "main/pages/About/About";

describe("About tests", () => {
  test("renders without crashing", () => {
    const { getByText } =  render(<About />);
    expect(getByText("Mapache Search"))
    expect(getByText("Students can use the app to search for and share helpful resources with their class."))
    expect(getByText("Instructors can monitor the types of searchers that students are performing, and point them in the right direction."))
    expect(getByText("Researchers can use the data from Mapache Search to study metacognition - in particular, the way students formulate questions."))
    expect(getByText("Read More About The Research Project"))
  });
});
