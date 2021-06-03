import React from 'react';
import { render } from '@testing-library/react';
import SearchHistory from 'main/pages/SearchHistory/SearchHistory';
import useSWR from "swr";
import { useParams} from "react-router-dom";

jest.mock("swr");
jest.mock("react-router-dom", () => {
    return {
        'useParams': jest.fn(),
    };
});

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
