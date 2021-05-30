import React from 'react';
import { act, render } from '@testing-library/react';
import SearchHistory from 'main/pages/SearchHistory/SearchHistory';

describe('Slack Users tests', () => {
    act(()=>{
        test('renders without errors', () => {
            render(<SearchHistory />);
        });
    });   
});