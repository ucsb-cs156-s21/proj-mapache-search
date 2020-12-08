import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import TimeFormatter from "./time"
const { SearchBar } = Search;


const userName = (message) => {
    let userProfile = message.user_profile;
    let result = "";
    if(userProfile == null) {
        result += message.user;
    } else {
        result += userProfile.real_name;
    }
    return(
        <p>{result}</p>
    );
}

export default ({ messages }) => {
    const columns = [{
        isDummyField: true,
        formatter: (cell, row) => userName(row),
        dataField: 'name',
        text: 'Username'
    },{
        dataField: 'text',
        text: 'Contents',
        sort: true
    },
        {
            dataField: 'ts',
            text: 'Time',
            sort: true,
            formatter: TimeFormatter,
            style: {
                width: "20%"
            }
        }
    ];

    return (
        <div style={{textAlign: "left"}}>
            <ToolkitProvider
                keyField="ts"
                data={ messages }
                columns={ columns }
                search
                button
            >
                {
                    props => (
                        <div>
                            <SearchBar { ...props.searchProps } />
                            <hr />
                            <BootstrapTable { ...props.baseProps } />
                        </div>
                    )
                }
            </ToolkitProvider>
        </div>
    );
};
