import React from "react";
import { Accordion, Card } from "react-bootstrap";
import { Link } from "react-router-dom";

export default function AccordionItem({ title, items = [], authorization = true, link}) {
  return (
    <>
      {authorization &&
        <Card>
          {items.length > 0 ?
            <>
              <Accordion.Toggle as={Card.Header} eventKey={title} className="dropdown-toggle">{title}</Accordion.Toggle>
              <Accordion.Collapse eventKey={title}>
                <Card.Body className="py-0">
                  {items.map(item =>
                    <div className="py-1 pl-2">
                      <Link key={item.name} to={item.link} className="text-dark">{item.name}</Link>
                    </div>
                  )}
                </Card.Body>
              </Accordion.Collapse>
            </>
          :
            <Card.Header as={Link} to={link} className="text-dark">{title}</Card.Header>
          }
        </Card>
      }
    </>
  )
};
