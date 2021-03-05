import React from "react";
import { render } from "@testing-library/react";
import SearchCard from "main/components/SearchCard/SearchCard";

describe("SearchCard tests", () => {
    test("The title, description, and link are correct", async () => {
        const { getByText } = render(<SearchCard title="title" description="description" link="link" />);
        expect(getByText('title')).toBeInTheDocument()
        expect(getByText('description')).toBeInTheDocument()
        expect(getByText('link').closest('a')).toHaveAttribute('href', 'link')
      });
});


