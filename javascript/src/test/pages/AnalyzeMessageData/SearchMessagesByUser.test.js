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

});