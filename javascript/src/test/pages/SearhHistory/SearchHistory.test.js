import React from 'react';
import { render } from '@testing-library/react';
import SearchHistory from 'main/pages/SearchHistory/SearchHistory';
import useSWR from "swr";

jest.mock("swr");


describe('Slack Users tests', () => {
  
        test('Member User', () => {
            render(<SearchHistory />);
        });
        
          test('Admin User', () => {
            useSWR.mockReturnValue({
      roleInfor: {
        role: "admin"
      }
    });
            render(<SearchHistory />);
        });
});
