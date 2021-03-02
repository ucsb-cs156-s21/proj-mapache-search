import React, { useState } from "react";
import { NavDropdown } from "react-bootstrap";

function NavbarHover( {title, items = [] } ){
    const [show, setShow] = useState(false);
    
    return(
        <NavDropdown
            title={title}
            show={show}
            onMouseEnter={()=>setShow(true)}
            onMouseLeave={()=>setShow(false)}
        >
            {items.map(item => (
                <NavDropdown.Item href={item.link}>{item.name}</NavDropdown.Item>
            ))}
        </NavDropdown>
    )
}

export default NavbarHover;