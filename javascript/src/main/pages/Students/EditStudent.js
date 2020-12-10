import React from "react";
import useSWR, { cache } from "swr";

import StudentForm from "main/components/Students/StudentForm";
import {buildUpdateStudent} from "main/services/StudentServices";

import { fetchWithToken } from "main/utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import Loading from "main/components/Loading/Loading";
import { useParams, useHistory } from "react-router-dom";

const EditStudent = () => {
  const history = useHistory();
  
  const { studentId } = useParams();
  const { getAccessTokenSilently: getToken } = useAuth0();

  const updateStudent = buildUpdateStudent(
    getToken, 
    () => { 
      history.push("/admin/students"); 
    }, 
    () => { 
      console.log("Caught error from update student");
    }
  );

  cache.clear();
  const { data: student } = useSWR(
    [`/api/students/${studentId}`, getToken],
    fetchWithToken
  );

  return (
    <>
      <h1>Edit Course</h1>
      {
        student ?
          <StudentForm updateStudent={updateStudent} existingStudents={student} /> :
          <Loading />
      }
    </>
  );
};

export default EditStudent;