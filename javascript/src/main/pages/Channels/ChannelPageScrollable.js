import React from "react";
import {useAuth0} from "@auth0/auth0-react";
import useSWR from "swr";
import { useParams} from "react-router-dom";
import {fetchWithToken} from "main/utils/fetch";
import MessageScrollableView from "main/components/ChannelMessages/MessageScrollableView"

// import ChannelLinksTable from "main/components/Channels/ChannelLinksTable"

const ChannelPageScrollable = () => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const { channel } = useParams();
    const { data: messages } = useSWR([`/api/members/channel/${channel}/messages`, getToken], fetchWithToken);


    // const links = [];
    // messages.map((el) => {
    //     console.log(el?.text);
    // });
    
    // console.log(messages[1].text);
    // var bracketRegEx = /<(.*?)>/g;
    // var found = messages.text.match(bracketRegEx)
    // if (found){
    //     for (let i=0; i<found.length; i++){
    //         var current = found[i].replace('<', '');
    //         current = current.replace('>', '');
    //         if (found[i].includes("http")){
    //             links.push({'link': current});
    //         }
    //     }
    // }
    return (
        <>
            <h1> {channel} </h1>
            <a href={"/member/listViewChannels/" + channel}>Switch to List View to search</a>
            
            {/* <ChannelLinksTable data={links} messages={messages} /> */}
            <MessageScrollableView messages={messages || []} channel={channel} />
        </>
    );
};

export default ChannelPageScrollable;
