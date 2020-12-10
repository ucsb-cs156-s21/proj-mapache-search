import React, {useEffect, useState} from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "main/utils/fetch";
import UserMessageList from "main/components/UserMessages/UserMessageList"
import { Form, Button } from "react-bootstrap";
import useSWR from 'swr';

const SearchMessagesByUser = () => {
    
    return (
            <h1> Search Results </h1>
    );
};

export default SearchMessagesByUser;

