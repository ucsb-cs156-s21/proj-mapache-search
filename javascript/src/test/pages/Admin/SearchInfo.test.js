import React from 'react';
import { render } from '@testing-library/react';
import AppUsers from 'main/pages/Admin/SearchInfo';
describe('Search Info tests', () => {

  test('renders without errors', () => {
    render(<AppUsers />);
  });
});
