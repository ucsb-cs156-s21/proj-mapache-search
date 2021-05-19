import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';

const channelLinks=(channelName) => {
    const link = `/member/channels/${channelName}`
    return channelName.replace(channelName,'<a href = ' + link + '>' + channelName + '</a>')
}

const channelMarkup = (channelName) => {
    channelName = channelLinks(channelName)
    return {
        __html: channelName
    }
}

const textLinks = (text) => {
    var bracketRegEx = /<(http(?:.*?))>/g;
    return text.replace(bracketRegEx, '<a href = "$1" target = "_blank">$1</a>');
}

const createMarkup = (text) => {
    text = textLinks(text)
    return {
        __html: text
    }
}

export default ({ channels }) => {


    const columns = [{
        dataField: 'name',
        text: 'Name',
        formatter: (cell) => <p dangerouslySetInnerHTML = {channelMarkup(cell)}></p>,
        sort: true,
      
    },{
        dataField: 'purpose.value',
        text: 'Purpose',
        formatter: (cell) => <p dangerouslySetInnerHTML = {createMarkup(cell)}></p>,
        sort: true,
       
    },{
        dataField: 'topic.value',
        text: 'Topic',
        formatter: (cell) => <p dangerouslySetInnerHTML = {createMarkup(cell)}></p>,
        sort: true,
      
    }];

    return (
        
        <BootstrapTable 
            bootstrap4={true}
            keyField='id' 
            data={messages} 
            columns={columns} 
        />

    );
};
