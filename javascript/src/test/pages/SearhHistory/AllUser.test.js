import React from 'react';
import { act, render } from '@testing-library/react';
import AllUser from 'main/pages/SearchHistory/AllUser';

describe('All Users tests', () => {
    act(()=>{
        test('renders without errors', () => {
            render(<AllUser />);
        });
    });   
});

