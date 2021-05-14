import React from 'react';
import { MemoryRouter } from "react-router-dom";
import AppNavbar from "main/components/Nav/AppNavbar";

export default {
  title: 'components/Nav/AppNavbar',
  component: AppNavbar
};

const Template = () => (
  <MemoryRouter>
    <AppNavbar />
  </MemoryRouter>
);

export const Desktop = Template.bind({});
Desktop.parameters = {
  viewport: {
    defaultViewport: "desktop"
  }
};

export const Tablet = Template.bind({});
Tablet.parameters = {
  viewport: {
    defaultViewport: "tablet"
  }
};
