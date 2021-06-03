import React from 'react';
import { act, render } from '@testing-library/react';
import SearchHistory from 'main/pages/SearchHistory/SearchHistory';
import useSWR from "swr";
jest.mock("@auth0/auth0-react");
jest.mock("swr");

describe('Search History tests', () => {
    act(()=>{
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
});
