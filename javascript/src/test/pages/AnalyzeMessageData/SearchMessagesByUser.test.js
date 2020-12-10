import React from "react";
import { render } from "@testing-library/react";
import ChannelPage from "main/pages/AnalyzeMessageData/SearchMessagesByUser";
import { useParams} from "react-router-dom";
import useSWR from "swr";
import SearchMessagesByUser from "../../../main/pages/AnalyzeMessageData/SearchMessagesByUser";
import userEvent from "@testing-library/user-event";

jest.mock("react-router-dom", () => {
    return {
        'useParams': jest.fn(),
    };
});

describe("SearchMessagesByUser tests", () => {

    test("renders without crashing", () => {
        render(<SearchMessagesByUser />);
    });

    test("renders without crashing on search", () => {
        const { getByLabelText } = render(<SearchMessagesByUser />);
        const selectSearchUser = getByLabelText("Search User");
        userEvent.type(selectSearchUser, "springboot");
    });

    test("searchUser state changes when user types in search bar", () => {
        const { getByLabelText } = render(<SearchMessagesByUser />); 
        const selectSearchUser = getByLabelText("Search User");
        userEvent.type(selectSearchUser, "Test Jones");
        expect(selectSearchUser.value).toBe("Test Jones");
    });
});