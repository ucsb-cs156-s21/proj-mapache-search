import React, { useState } from "react";
import { Form, Button, Row, Col, Container } from "react-bootstrap";

export const AddStudent = ({ addStudent }) => {
  const [value, setValue] = useState("");
  return (
    <form
      onSubmit={(event) => {
        event.preventDefault();
        const text = value.trim();
        text && addStudent(text);
        setValue("");
      }}
    >
      <Container fluid>
        <Row>
          <Col xs={5} style={{ padding: 0 }}>
            <Form.Control
              type="text"
              placeholder="student email"
              margin="normal"
              onChange={(event) => {
                setValue(event.target.value);
              }}
              value={value}
            />
          </Col>
          <Col xs={5} style={{ padding: 0 }}>
            <Form.Control
              type="text"
              placeholder="team"
              margin="normal"
              onChange={(event) => {
                setValue(event.target.value);
              }}
              value={value}
            />
          </Col>
          <Col xs={1} style={{ padding: 0 }}>
            <Button data-testid="todo-submit" type="submit">Submit</Button>
          </Col>
        </Row>
      </Container>
    </form>
  );
};