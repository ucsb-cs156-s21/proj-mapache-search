import React, { useState } from "react";
import { Form, Button, Row, Col, Container } from "react-bootstrap";

const StudentForm = ({addStudent, existingStudents, updateStudent}) => {
    const emptyStudent = {
        email: "",
        teamName: "",
    }

    const [student, setStudent] = useState(existingStudents || emptyStudent);

    const handleOnSubmit = (e) => {
        e.preventDefault();
        if (addStudent) {
            addStudent(student);
        }
        else{
            updateStudent(student, student.id);
        }
    }

    return (
        <form onSubmit={handleOnSubmit}>
            <Form.Group as={Row} controlId="email">
                <Form.Label column sm={2}>
                    Email
                </Form.Label>
                <Col sm={10}>
                    <Form.Control type="email" placeholder="email" value={student.email} onChange={(e) => setStudent({
                        ...student,
                        email: e.target.value
                    })} />
                </Col>
            </Form.Group>
            <Form.Group as={Row} controlId="teamName">
                <Form.Label column sm={2}>
                    TeamName
                </Form.Label>
                <Col sm={10}>
                    <Form.Control type="text" placeholder="teamName" value={student.teamName} onChange={(e) => setStudent({
                        ...student,
                        teamName: e.target.value
                    })} />
                </Col>
            </Form.Group>
            <Form.Group as={Row}>
                <Col sm={{ span: 10, offset: 2 }}>
                    <Button type="submit">Submit</Button>
                </Col>
            </Form.Group>
        </form>
    );
};

export default StudentForm;