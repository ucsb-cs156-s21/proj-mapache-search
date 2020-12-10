import React from "react";
import { waitFor, render } from "@testing-library/react";
import useSWR from "swr";
jest.mock("swr");

import { useAuth0 } from "@auth0/auth0-react";
jest.mock("@auth0/auth0-react");

import userEvent from "@testing-library/user-event";

import { fetchWithToken } from "main/utils/fetch";
import {StudentCSVButton} from "main/pages/Students/StudentCSVButton";
jest.mock("main/utils/fetch");

describe("students CSV Upload test", () => {
    const user = {
        name: "test user",
    };
    const students = [
        {
            email: "jeff@ucsb.edu",
            teamName: "team-7pm-b",
        },
        {
            email: "john@ucsb.edu",
            teamName: "team-7pm-a",
        },
    ];
    const getAccessTokenSilentlySpy = jest.fn();
    const mutateSpy = jest.fn();
    beforeEach(() => {
        useAuth0.mockReturnValue({
            user,
            getAccessTokenSilently: getAccessTokenSilentlySpy,
        });
        useSWR.mockReturnValue({
            data: students,
            error: undefined,
            mutate: mutateSpy,
        });
    });
    test("renders without crashing", () => {
        render(<StudentCSVButton />);
    });
    test("upload file without crashing", () => {
        const addTask = jest.fn();
        addTask.mockImplementation(async () => {   
            throw new Error();
        });
        const testFile = new File(['test'], 'test.csv', {type: 'text/csv'});
        const { getByTestId, getByText } = render(<StudentCSVButton />);
        const input = getByTestId("csv-input");
        userEvent.upload(input, testFile);
        expect(input.files[0]).toStrictEqual(testFile)
        expect(input.files.item(0)).toStrictEqual(testFile)
        expect(input.files).toHaveLength(1)

        userEvent.click(getByText("Submit")); 
    });
});
