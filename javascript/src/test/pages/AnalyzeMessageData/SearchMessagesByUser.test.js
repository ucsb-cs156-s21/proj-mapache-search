import React from "react";
import { render } from "@testing-library/react";
import ChannelPage from "main/pages/AnalyzeMessageData/SearchMessagesByUser";
import { useParams} from "react-router-dom";
import useSWR from "swr";
import SearchMessagesByUser from "../../../main/pages/AnalyzeMessageData/SearchMessagesByUser";
import userEvent from "@testing-library/user-event";

jest.mock("swr");
jest.mock("react-router-dom", () => {
    return {
        'useParams': jest.fn(),
    };
});

describe("SearchMessagesByUser tests", () => {
    beforeEach(() => {
        useParams.mockReturnValue({
            'user': 'test-user'
        });
    });

    test("renders without crashing", () => {
        //useSWR.mockReturnValue({});
        render(<SearchMessagesByUser />);
    });

    test("renders without crashing", () => {
        //useSWR.mockReturnValue({});
        const { getByLabelText } = render(<SearchMessagesByUser />);
        const selectSearchUser = getByLabelText("Search User");
        userEvent.type(selectSearchUser, "springboot");
    });
});