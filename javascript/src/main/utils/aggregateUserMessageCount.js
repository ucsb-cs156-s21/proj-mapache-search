const aggregateUserMessageCount = (agg_messages, agg_slackUsers) => {
    const userMessageCounts = [];
    const usersAdded = [];
    let i;
    for(i = 0; i<agg_slackUsers.length;i++){
        let count = 0;
        let j;
        for(j=0; j < agg_messages.length; j++) {
            if (agg_messages[j].user_profile !== null && agg_messages[j].user_profile.real_name === agg_slackUsers[i].profile.real_name){
                count++;
            }
        }
        const userCountPair = {
            name: agg_slackUsers[i].profile.real_name,
            count: count
        }
        
        /* this if statement is needed because of an edge case where 
           people with multiple emails in the slack workspace show up as
           multiple slackUsers. this is a bug and should be fixed */
        if (usersAdded.indexOf(agg_slackUsers[i].profile.real_name) == -1) {
            userMessageCounts.push(userCountPair);
            usersAdded.push(agg_slackUsers[i].profile.real_name);  
        }
    }
    return userMessageCounts;
}

export default aggregateUserMessageCount;
