import React from 'react';
import { render } from '@testing-library/react';
import AllUser from 'main/pages/SearchHistory/AllUser';

describe('All Users tests', () => {
   
        test('renders without errors', () => {
            render(<AllUser />);
        });
     
});

