import React from 'react';
import { render } from '@testing-library/react';
import Teams from 'main/pages/Admin/Teams';
describe('Teams tests', () => {

  test('renders without errors', () => {
    render(<Teams />);
  });
});
