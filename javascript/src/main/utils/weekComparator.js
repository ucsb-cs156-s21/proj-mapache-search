/*Data passed into function is of the form:
    {
        "week": string,
        "weekNum": int,
        "count": int //Count of messages for that week
    }
*/
const weekComparator = (weekA, weekB) => {
    if (weekA.weekNum < weekB.weekNum)
        return -1;
    return 1;
}

export default weekComparator; 