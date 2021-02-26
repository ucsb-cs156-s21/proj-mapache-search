import React  from 'react';
import { render } from "@testing-library/react";
import JSONPrettyCard from "../../../main/components/Utilities/JSONPrettyCard.js";

describe("JSON Pretty Card tests", () => {
    test("it renders without crashing", () => {
        render(<JSONPrettyCard/>);
    });

});