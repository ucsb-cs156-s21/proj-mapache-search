import React from "react";
import BootstrapTable from 'react-bootstrap-table-next';

import useSWR from "swr";
import {useAuth0} from "@auth0/auth0-react";
import {fetchWithToken} from "../../utils/fetch";
import { propTypes } from "react-json-pretty";


const SearchedTermsTable = (props) => {

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
             <h2>Searched Terms</h2>
            <BootstrapTable keyField='id' data={props.data} columns={columns} />
        </>
    );
};
export default SearchedTermsTable;