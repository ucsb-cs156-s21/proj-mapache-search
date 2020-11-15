import React from 'react';
import { render } from '@testing-library/react';
import SlackUsers from 'main/pages/Admin/SlackUsers';
describe('Slack Users tests', () => {

  test('renders without errors', () => {
    render(<SlackUsers />);
  });
});
