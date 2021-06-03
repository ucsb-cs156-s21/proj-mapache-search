import React from 'react';
import { render } from '@testing-library/react';
import SearchHistory from 'main/pages/SearchHistory/SearchHistory';
import useSWR from "swr";

jest.mock("swr");

describe('Search History tests', () => {
   
        test('test as member', () => {
            render(<SearchHistory />);
        });
            
           test("test as admin", () => {
        useSWR.mockReturnValue({
      data: {
        role: "admin"
      }
    });
        render(<SearchHistory />);
    });
    
     
});
