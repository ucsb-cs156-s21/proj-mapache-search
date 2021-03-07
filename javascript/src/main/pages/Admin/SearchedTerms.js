import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';

const SearchedTerms = () => {
    
    console.log(SearchedTerms);
    const columns = [{
        dataField: 'term',
        text: 'Term',
        sort:true
    }, {
        dataField: 'id',
        text: 'ID',
        sort:true
    }, {
        dataField: 'count',
        text: 'Count',
        sort:true
    }];

    return (
        <>
            <p>
                <font size = "5">
                    <b>Searched Terms</b>
                </font>
            </p>
            <BootstrapTable keyField='id' data={[]} columns={columns} />
        </>
    );
};
export default SearchedTerms;
