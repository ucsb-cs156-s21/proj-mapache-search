import React from "react";
import { Button, Col, Row } from "react-bootstrap";
import useSWR from "swr";
import { StudentCSVButton } from "./StudentCSVButton";
import { fetchWithToken } from "main/utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import Loading from "main/components/Loading/Loading";
import StudentTable from "main/components/Students/StudentTable";
import {buildDeleteStudent, buildDeleteAllStudents, uploadStudentsCSV} from "main/services/StudentServices";
import {useHistory} from "react-router-dom";

const Students = () => {
  const history = useHistory();
  const { getAccessTokenSilently: getToken } = useAuth0();
  const { data: studentList, error, mutate: mutateStudents } = useSWR(
    ["/api/students", getToken],
    fetchWithToken
  );

  if (error) {
    return (
      <h1>We encountered an error; please reload the page and try again.</h1>
    );
  }
  if (!studentList) {
    return <Loading />;
  }

  const deleteStudent = buildDeleteStudent(
    getToken, mutateStudents
  );

  const deleteAllStudents = buildDeleteAllStudents(
    getToken, mutateStudents
  );

  const uploadedStudents = uploadStudentsCSV(
    getToken, mutateStudents
  );

  return (
    <>
        <h1>
            Upload CSV File of Students
        </h1>
        <p>
          Make sure that the uploaded CSV contains a header
        </p>
        <StudentCSVButton addTask={uploadedStudents}/>
        <Row style={{paddingTop: 10}}>
            <Col xs={6} style={{ padding: 0 }}>
              <Button onClick={()=>history.push("/students/new")}>New Student</Button>
            </Col>
            <Col xs={6} style={{ padding: 0 }}>
            <Button variant="danger" onClick={() => deleteAllStudents()}>Delete All</Button>
            </Col>
        </Row>
        <br/><br/>
        <StudentTable students={studentList} deleteStudent={deleteStudent}/>
        <br/><br/><br/><br/><br/><br/>
    </>
  );
};

export default Students;
