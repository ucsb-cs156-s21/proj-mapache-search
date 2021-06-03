import React from 'react';
import { render } from '@testing-library/react';
import SearchHistory from 'main/pages/SearchHistory/SearchHistory';
import useSWR from "swr";

jest.mock("swr");


describe('Search History tests', () => {
   
        test('test as member', () => {
            useSWR.mockReturnValue({
      data: {
        role: "member"
      }
    });
            const { getByText } = render(<SearchResults />);
    expect(getByText("Member")).toBeInTheDocument();
        });
            
           test("test as admin", () => {
        useSWR.mockReturnValue({
      data: {
        role: "admin"
      }
    });
        const { getByText } = render(<SearchResults />);
    expect(getByText("Admin")).toBeInTheDocument();
    });
    
     
});
