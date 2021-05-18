import React from 'react';

import MessageListView from "main/components/ChannelMessages/MessageListView";


export default {
  title: 'components/ChannelMessages/MessageListView',
  component: MessageListView
};

const Template = (args) => <MessageListView {...args}/>;

export const WithSearchBar = Template.bind({});
WithSearchBar.args = {
  messages: [{
    "type": "message",
    "subtype": "channel_join",
    "ts": "1594143066.000200",
    "user": "U017218J9B3",
    "text": "<@U017218J9B3> has joined the channel",
    "channel": "section-6pm",
    "user_profile": {
      "real_name": "Test Person"
    }
  },
  {
    "type": "message",
    "subtype": "channel_join",
    "ts": "1594143066.000200",
    "user": "U017218J9B3",
    "text": "Office hours at <https://ucsb.zoom.us/j/89220034995?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09>",
    "channel": "section-6pm",
    "user_profile": {
      "real_name": "Test Person"
    }
  }],
};

export const NoSearchBar = Template.bind({});
NoSearchBar.args = {
    searchField: false,
    ...WithSearchBar.args
};
