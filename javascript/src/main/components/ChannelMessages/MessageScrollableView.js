import React from "react";
import TimeFormatter from "./time"
import {useAuth0} from "@auth0/auth0-react";
import useSWR from "swr";
import {fetchWithToken} from "../../utils/fetch";

const replaceMessage = (text, slackUsers) => {
    return text.replace(/<@([A-Z0-9]{11})>/g, (_,userId) => {
        if(slackUsers != null) {
            for(let i = 0; i < slackUsers.length; i++) {
                if(slackUsers[i].id === userId) {
                    return "@" + slackUsers[i].real_name;
                }
            }
        }
        return "@" + userId;
    })
}

const formatBracketedText = (text) => {
    var bracketRegEx = /<(.*?)>/g;
    var found = text.match(bracketRegEx)
    if (found){
        for (let i=0; i<found.length; i++){
            var current = found[i].replace('<', '');
            current = current.replace('>', '');
            if (found[i].includes("|") && (found[i].includes("mailto") || found[i].includes("http") || found[i].includes("tel"))){      // embedded links
                var links = current.split('|');
                text = text.replace(found[i], '<a href = ' + links[0] + ' target = "_blank">' + links[1] +'</a>')
            }else if (found[i].includes("http") || found[i].includes("mailto") || found[i].includes("tel")){                            // unembedded links
                text = text.replace(found[i], '<a href = ' + current + ' target = "_blank">' + current + '</a>')
            }else if (found[i].includes("|")){                                                                                          // channel links
                links = current.split('|');
                text = text.replace(found[i], '<a href = /member/channels/' + links[1] + '>#' + links[1] + '</a>')
            }else if (found[i].includes("!")){                                                                                          // channel tags (ex: @channel)
                text = text.replace(found[i], '<strong> @' + current + '</strong>')
                text = text.replace("!", "")
            }else{
                text = text.replace(found[i], current)
            }
        }
    }
    return text;
}

const createMarkup = (text, slackUsers) => {
    text = replaceMessage(text, slackUsers)
    text = formatBracketedText(text)
    return {
        __html: text
    }
}

const sortByDate = (a, b) => {
    return (b.key-a.key);
}

const replaceLink = (text) => {
    var link = /<(http(?:.*?))>/g;
    return text.replace(link, '<a href = "$1">$1</a>');
}


export default ({ messages, _channel }) => {
    const { getAccessTokenSilently: getToken } = useAuth0();
    const {data: slackUsers} = useSWR([`/api/slackUsers`, getToken], fetchWithToken);
    return (
        <div style={{textAlign: "left", marginTop: 20}}>
            {
                messages.map((el) => {
                    return (
                        <div
                            key={el?.ts}
                            style={{
                            borderRadius: 10,
                            paddingTop: 15,
                            paddingLeft: 10,
                            paddingRight: 10,
                            borderStyle: "solid",
                            borderColor: "#ccc",
                            marginTop: 10
                        }}>
                            <strong>{el?.user_profile?.real_name || el.user}</strong>
                            <label style={{marginLeft: 10}}>{TimeFormatter(el?.ts)}</label>
                            <p>{replaceMessage(el?.text, slackUsers), replaceLink(el?.text)}</p>
                        </div>
                    )
                }).sort(sortByDate)
            }
        </div>
    );
};
