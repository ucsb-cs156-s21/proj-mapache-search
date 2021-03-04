import React from "react";
import { Card } from "react-bootstrap";

const SearchCard = ({title, description, link}) => {
  return (
    <Card>
      <Card.Body>
        <Card.Title>{title}</Card.Title>
        <Card.Text>
          {description}
    </Card.Text>
        <Card.Link href={link}>{link}</Card.Link>
      </Card.Body>
    </Card>
  );
};

export default SearchCard;
