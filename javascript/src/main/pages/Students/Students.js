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
import { ToastProvider,useToasts } from 'react-toast-notifications';


const Students = () => {
  const { addToast } = useToasts();
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
  

  const onUploadError = (error) => {
    // //getToken,
    // () => {
    //   mutateStudents();
    //   addToast(" upload right format",{ appearance: 'success' });
    // },
    // () =>{
    //   addToast("Didn't upload right format",{ appearance: 'error' });

    // }
    addToast("Didn't upload right format", { appearance: 'error' });
    console.log("error=",error.name,error.message);
  };
  const uploadedStudents = uploadStudentsCSV(
    getToken, mutateStudents,onUploadError
  );

  return (
    <>
        <h1>
            Upload CSV File of Students
        </h1>
        <p>
          Make sure that the uploaded CSV contains a header.  Sample format:
        </p>
        <pre style={{whiteSpace: 'pre', textAlign: 'left', width: '20em', marginLeft: 'auto', border: 'solid blue 1px', marginRight: 'auto', padding: '1em'}}>
        first, last, email, id, section, team<br/>
        Chris, Gaucho, cgaucho@ucsb.edu, 5pm, s21-5pm-1<br/>
        Laurie, Del Player, ldelplaya@ucsb.edu, 7pm, s21-7pm-3<br/>
        ...<br/>
        {/* email,teamName<br/>
        aaaaa@ucsb.edu,team1<br />
        bbbbb@ucsb.edu,team1<br />
        ccccc@ucsb.edu,team2<br /> */}
        </pre>
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
