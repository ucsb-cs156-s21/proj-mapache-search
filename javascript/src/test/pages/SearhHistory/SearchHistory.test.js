import React from 'react';
import { render } from '@testing-library/react';
import SearchHistory from 'main/pages/SearchHistory/SearchHistory';
import useSWR from "swr";

jest.mock("swr");


describe('Slack Users tests', () => {
  
        test('Member User', () => {
            useSWR.mockReturnValue({
      roleInfor: {
        role: "member"
      }
    });
            render(<SearchHistory />);
        });
        
          test('Admin User', () => {
            useSWR.mockReturnValue({
      roleInfor: {
        role: "Admin"
      }
    });
            const { getByText } = render(<SearchHistory />);
    expect(getByText("Admin")).toBeInTheDocument();
        });
});
