import React from 'react';
import { MemoryRouter } from "react-router-dom";

import AppNavbar from "main/components/Nav/AppNavbar";

export default {
  title: 'components/Nav/AppNavbar',
  component: AppNavbar
};

const Template = (args) => (
  <MemoryRouter>
  <AppNavbar {...args}/>
</MemoryRouter>
);

export const Guest = Template.bind({});
Guest.args = {
  appNavbar: {
    isMember: false,
  },
};

export const Admin = Template.bind({});
Admin.args = {
  appNavbar: {
    isAdmin: true,
  },
};

export const Member = Template.bind({});
Member.args = {
  appNavbar: {
    isMember: true,
  },
};
