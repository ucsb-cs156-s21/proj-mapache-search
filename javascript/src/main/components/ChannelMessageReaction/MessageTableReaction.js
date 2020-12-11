import React from "react";
import BootstrapTable  from 'react-bootstrap-table-next';

export default ({ messages=[],reaction }) => {

    function subjectFormatter(cell, row, rowIndex) {
        const correctReaction = row.message_reactions.find((message_reaction) => {
            return message_reaction.name === reaction;
        });
        return correctReaction.count;
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
    )
};
