import React from "react"
import MessageListView from "main/components/ChannelMessages/MessageListView";
import MessageFixtures from "fixtures/MessageFixtures";


export default {
  title: 'components/ChannelMessages/MessageListView',
  component: MessageListView
};

const Template = (args) => <MessageListView {...args}/>;

export const WithSearchBar = Template.bind({});
WithSearchBar.args = {
  messages: MessageFixtures.tenMessages
};

export const NoSearchBar = Template.bind({});
NoSearchBar.args = {
  messages: MessageFixtures.tenMessages,
  searchField: false
};
