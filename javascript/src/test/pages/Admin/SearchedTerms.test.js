import React from 'react';
import { render } from '@testing-library/react';
import SearchedTerms from 'main/pages/Admin/SearchedTerms';
describe('Searched Terms tests', () => {

  test('renders without errors', () => {
    render(<SearchedTerms />);
  });
});
