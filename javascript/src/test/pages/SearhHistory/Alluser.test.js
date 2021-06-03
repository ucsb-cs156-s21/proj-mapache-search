import React from 'react';
import { act, render } from '@testing-library/react';
import Alluser from 'main/pages/SearchHistory/Alluser';

describe('All Users tests', () => {
    act(()=>{
        test('renders without errors', () => {
            render(<Alluser />);
        });
    });   
});
© 2021 GitHub, Inc.
