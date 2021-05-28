import React, { useState } from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import { fetchWithToken } from "../../utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";

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
        <BootstrapTable 
            bootstrap4={true}
            keyField='id' 
            data={messageChannels} 
            columns={columns} 
        />

    );
};


