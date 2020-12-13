import React from "react";
import { render } from "@testing-library/react";
import AppFooter from "main/components/Footer/AppFooter";
import { buildCreateStudent, buildDeleteStudent, buildUpdateStudent, buildDeleteAllStudents, uploadStudentsCSV } from "main/services/StudentServices";

import { fetchWithToken } from "main/utils/fetch";

jest.mock("main/utils/fetch", () => ({
    fetchWithToken:  jest.fn()
}));

describe("StudentService tests", () => {

    const getToken = jest.fn();
    const onSuccess = jest.fn(); 
    const onError = jest.fn();

    beforeEach( () => {
        jest.clearAllMocks();
    });

    test("buildCreateStudent and invoke createStudent", async () => {
        const createStudent = buildCreateStudent(getToken, onSuccess, onError);
        await createStudent();
        expect(onSuccess).toBeCalledTimes(1);
    });
    test("buildUpdateStudent and invoke updateStudent", async () => {
        const updateStudent= buildUpdateStudent(getToken, onSuccess, onError);
        await updateStudent();
        expect(onSuccess).toBeCalledTimes(1);
    });
    test("buildDeleteStudent and invoke deleteStudent", async () => {
        const deleteStudent = buildDeleteStudent(getToken, onSuccess, onError);
        await deleteStudent();        
        expect(onSuccess).toBeCalledTimes(1);
    });
    test("buildDeleteAllStudents and invoke updateStudent", async () => {
        const deleteAllStudent = buildDeleteAllStudents(getToken, onSuccess, onError);
        await deleteAllStudent();
        expect(onSuccess).toBeCalledTimes(1);
    });
    test("uploadStudentsCSV and invoke deleteStudent", async () => {
        const uploadStudent = uploadStudentsCSV(getToken, onSuccess, onError);
        await uploadStudent();        
        expect(onSuccess).toBeCalledTimes(1);
    });

    test("buildCreateStudent where we expect onError to be called", async () => {
        fetchWithToken.mockImplementation( async () => { throw new Error("mock error"); } );
        const createStudent = buildCreateStudent(getToken, onSuccess, onError);
        await createStudent();
        expect(onError).toBeCalledTimes(1);
    });
    test("buildUpdateStudent where we expect onError to be called", async () => {
        fetchWithToken.mockImplementation( async () => { throw new Error("mock error"); } );
        const updateStudent = buildUpdateStudent(getToken, onSuccess, onError);
        await updateStudent();
        expect(onError).toBeCalledTimes(1);
    });
    test("buildDeleteStudent where we expect onError to be called", async () => {
        fetchWithToken.mockImplementation( async () => { throw new Error("mock error"); } );
        const deleteStudent = buildDeleteStudent(getToken, onSuccess, onError);
        await deleteStudent();
        expect(onError).toBeCalledTimes(1);
    });
    test("buildDeleteAllStudents where we expect onError to be called", async () => {
        fetchWithToken.mockImplementation( async () => { throw new Error("mock error"); } );
        const deleteAllStudent = buildDeleteAllStudents(getToken, onSuccess, onError);
        await deleteAllStudent();
        expect(onError).toBeCalledTimes(1);
    });
    test("uploadStudentsCSV where we expect onError to be called", async () => {
        fetchWithToken.mockImplementation( async () => { throw new Error("mock error"); } );
        const uploadStudent = uploadStudentsCSV(getToken, onSuccess, onError);
        await uploadStudent();
        expect(onError).toBeCalledTimes(1);
    });
});