import React from "react";
import {Button, Form} from "react-bootstrap";
import TimeFormatter from "./time"


export default ({ messages, channel }) => {
    return (
        <div style={{textAlign: "left", marginTop: 20}}>
            {
                messages.map((el) => {
                    return (
                        <div style={{
                            borderRadius: 10,
                            paddingTop: 15,
                            paddingLeft: 10,
                            paddingRight: 10,
                            borderStyle: "solid",
                            borderColor: "#ccc",
                            marginTop: 10
                        }}>
                            <strong>{el?.user_profile?.real_name || el.user}</strong>
                            <label style={{marginLeft: 10}}>{TimeFormatter(el?.ts)}</label>
                            <p>{el?.text}</p>
                        </div>
                    )
                })
            }
        </div>
    );
};
