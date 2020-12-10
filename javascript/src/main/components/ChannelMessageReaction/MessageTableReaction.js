import React from "react";
import BootstrapTable  from 'react-bootstrap-table-next';
//import { BootstrapTable, TableHeaderColumn } from 'react-bootstrap-table';

export default ({ messages = []}, {reaction = [] }) => {

    function subjectFormatter(cell, row, rowIndex) {

        if (row.message_reactions !== null) {
            const correctReaction = row.message_reactions.find((message_reaction) => {
                return message_reaction.name === reaction;
            });
            console.log(correctReaction);
            return correctReaction.count;
        }
        return "reactions field not found";
    }

    const columns = [{
        dataField: 'user',
        text: 'User'
    },{
        dataField: 'text',
        text: 'Contents'
    },{
        dataField: 'reactions',
        formatter: subjectFormatter,
        text: 'Count'
    }
    ];

    return (
     
        <BootstrapTable keyField='ts' data={messages} columns={columns}/>
//        <TableHeaderColumn dataField="reactions" dataSort={true} dataFormat={subjectFormatter} >Subject</TableHeaderColumn>
  
    )
};