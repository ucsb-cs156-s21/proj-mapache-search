import React from "react";

import StudentForm from "main/components/Students/StudentForm";
import {buildCreateStudent} from "main/services/StudentServices";

import { fetchWithToken } from "main/utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import Loading from "main/components/Loading/Loading";
import { useParams, useHistory } from "react-router-dom";

const NewStudent = () => {
  const history = useHistory();
  const { getAccessTokenSilently: getToken } = useAuth0();
  const createStudent = buildCreateStudent(
    getToken,
    () => { 
      history.push("/admin/students"); 
    }, 
    () => { 
      console.log("Caught error from create student");
    }
  );

  return (
    <>
      <h1>Create Student</h1>
      {
        <StudentForm addStudent={createStudent}/>
      }
    </>
  );
};

export default NewStudent;