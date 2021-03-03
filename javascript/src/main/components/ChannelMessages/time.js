export default (cell, _) => {
    let date = new Date(cell * 1000);
    return date.getUTCFullYear() +
        '-' + ('0' + (date.getMonth()+1)).slice(-2) +
        '-' + ('0' + date.getDate()).slice(-2) +
        ' ' + ('0' + date.getHours()).slice(-2) +
        ':' + ('0' + date.getMinutes()).slice(-2) +
        ':' + ('0' + date.getSeconds()).slice(-2)
}