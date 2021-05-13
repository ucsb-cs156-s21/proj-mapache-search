import React from "react";
import { useAuth0 } from "@auth0/auth0-react";
import { Nav } from "react-bootstrap";
import LoginButton from "./LoginButton";
import LogoutButton from "./LogoutButton";
import { LinkContainer } from "react-router-bootstrap";

const AuthNav = () => {
    const { user } = useAuth0();

    return (
        <>
            {user ?
                <>
                    <LinkContainer to="/profile">
                        <div className="align-middle">
                            <img
                                src={user.picture}
                                alt="Profile"
                                className="rounded-circle d-inline-block"
                                width="36"
                                style={{ marginRight: 15 }}
                            />
                            <p className="text-light p-0 d-inline-block">{"Hello, " + user.name}</p>
                        </div>
                    </LinkContainer>
                    <LogoutButton />
                </>
            :
                <LoginButton />
            }
        </>
    );
};

export default AuthNav;
