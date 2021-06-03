import React from 'react';
import { render } from '@testing-library/react';
import SearchHistory from 'main/pages/SearchHistory/SearchHistory';
//import useSWR from "swr";

//jest.mock("swr");


describe('Slack Users tests', () => {
  
        test('renders without errors', () => {
            render(<SearchHistory />);
        });
   
});
