import React from 'react';

import SearchCard from "main/components/SearchCard/SearchCard";

export default {
  title: 'components/SearchCard/SearchCard',
  component: SearchCard
};


const Template = (args) => <SearchCard {...args}/>;

export const Card = Template.bind({});

Card.args = {
  title: "Hello",
  description: "World",
  link: "https://www.google.com/"
}
