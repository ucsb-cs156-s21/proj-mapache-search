import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';

export default ({ messages }) => {

    const messageChannels = [];

    const columns = [{
        dataField: 'channel',
        text: 'Channel'
    },{
        dataField: 'count',
        text: 'Message Count'
    }
    ];

    if(Array.isArray(messages) && messages.length) {
        for (let i = 0; i < messages.length; i++) {
            if(!messageChannels.some(e => e.channel === messages[i].channel)) {
                const channelCountPair = {
                    channel: messages[i].channel,
                    count: 1
                }
                messageChannels.push(channelCountPair);
            } else {
                const index = messageChannels.findIndex(channelCountPair => channelCountPair.channel === messages[i].channel);
                messageChannels[index].count++;
            }
        }
    }

    return (
        <BootstrapTable keyField='channel' data={messageChannels} columns={columns}/>
    );
};


