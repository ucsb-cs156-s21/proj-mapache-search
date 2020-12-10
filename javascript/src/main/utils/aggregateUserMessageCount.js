const aggregateUserMessageCount = (agg_messages, agg_slackUsers) => {
    const userMessageCounts = [];

    var i;
    for(i = 0; i<agg_slackUsers.length;i++){
        var count = 0;
        var j;
        for(j=0; j < agg_messages.length; j++) {
            if (agg_messages[j].user_profile !== null && agg_messages[j].user_profile.real_name === agg_slackUsers[i].real_name){
                count++;
            }
        }
        const userCountPair = {
            name: agg_slackUsers[i].real_name,
            count: count.toString()
        }
        userMessageCounts.push(userCountPair);
    }
    return userMessageCounts;
}

export default aggregateUserMessageCount;