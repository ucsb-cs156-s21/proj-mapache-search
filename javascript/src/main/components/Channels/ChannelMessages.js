import React from "react";
import { Jumbotron } from "react-bootstrap";
import {useAuth0} from "@auth0/auth0-react";
import { Redirect } from "react-router-dom";

const ChannelMessages = () => {
    const { isAuthenticated } = useAuth0();

    return (
            <Jumbotron>
                <div className="text-left">
                    <h5>Channel Name</h5>
                    <p>Messages go here</p>
                </div>
            </Jumbotron>
    );
};

export default ChannelMessages;
