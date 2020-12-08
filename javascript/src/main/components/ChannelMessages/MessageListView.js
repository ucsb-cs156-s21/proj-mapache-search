import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';
import ToolkitProvider, { Search } from 'react-bootstrap-table2-toolkit';
import TimeFormatter from "./time"
const { SearchBar } = Search;
export default ({ messages }) => {
    const columns = [{
        dataField: 'user_profile.real_name',
        text: 'Name',
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
