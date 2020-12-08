import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
const { SearchBar } = Search;

export default ({ messages }) => {
    const columns = [{
        dataField: 'user',
        text: 'user',
        sort: true
    },{
        dataField: 'text',
        text: 'Contents',
        sort: true
    },
        {
            dataField: 'ts',
            text: 'Time',
            sort: true,
            formatter: (cell, _) => {
                // convert timestamp to human readable time
                let date = new Date(cell * 1000);
                return date.getUTCFullYear() +
                    '-' + ('0' + date.getMonth()).slice(-2) +
                    '-' + ('0' + date.getDate()).slice(-2) +
                    ' ' + ('0' + date.getHours()).slice(-2) +
                    ':' + ('0' + date.getMinutes()).slice(-2) +
                    ':' + ('0' + date.getSeconds()).slice(-2)
            },
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
