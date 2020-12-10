import React from "react";
import { render, waitFor } from "@testing-library/react";
import StudentForm from "main/components/Students/StudentForm";
import userEvent from "@testing-library/user-event";

describe("StudentForm tests", () => {

  const sampleStudent = {
    "id": 1,
    "email": "jeff@ucsb.edu",
    "teamName" : "team-7pm-b"
  }

  test("empty component renders without crashing", () => {
    render(<StudentForm />);
  });

  test("component with existing course renders without crashing", () => {
    render(<StudentForm existingStudents={sampleStudent}/>);
  });

  test("updating student team works", async () => {

    const updateStudentMock = jest.fn();

    const { getByText, getByDisplayValue } = render
      (<StudentForm updateStudent={updateStudentMock} existingStudents={sampleStudent}/>)
    ;

    const updatedStudent = {
      ...sampleStudent,
      teamName: "updated teamName",
    };

    const input = getByDisplayValue(sampleStudent.teamName);
    userEvent.clear(input);
    userEvent.type(input, updatedStudent.teamName);

    const submitButton = getByText("Submit");
    userEvent.click(submitButton);

    expect(updateStudentMock).toHaveBeenCalledTimes(1);
    expect(updateStudentMock).toHaveBeenCalledWith(updatedStudent, updatedStudent.id);
  });

  test("creating student works", async () => {

    const createStudentMock = jest.fn();

    const { getByLabelText, getByText } = render
      (<StudentForm addStudent={createStudentMock} />)
    ;

    const emailInput = getByLabelText("Email");
    userEvent.type(emailInput, sampleStudent.email);

    const teamInput = getByLabelText("TeamName");
    userEvent.type(teamInput, sampleStudent.teamName);

    const submitButton = getByText("Submit");
    userEvent.click(submitButton);

    expect(createStudentMock).toHaveBeenCalledTimes(1);
    expect(createStudentMock).toHaveBeenCalledWith({ ... sampleStudent, id: undefined });
  });
});