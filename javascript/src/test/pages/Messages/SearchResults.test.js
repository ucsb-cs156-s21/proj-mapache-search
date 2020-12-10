import React from "react";
import { render } from "@testing-library/react";
import { useParams} from "react-router-dom";
import SearchResults from "main/pages/Messages/SearchResults";
import userEvent from "@testing-library/user-event";

describe("ChannelPage tests", () => {
    test("renders without crashing", () => {
        //useSWR.mockReturnValue({});
        render(<SearchResults />);
    });

    test("renders without crashing", () => {
        //useSWR.mockReturnValue({});
        const { getByLabelText } = render(<SearchResults />);
        const selectSearchString = getByLabelText("Search String");
        userEvent.type(selectSearchString, "springboot");
    });
});
