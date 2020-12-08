import React from "react";

import StudentForm from "main/components/Students/StudentForm";
import { fetchWithToken } from "main/utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import Loading from "main/components/Loading/Loading";
import { useParams, useHistory } from "react-router-dom";

const NewStudent = () => {
//   const { addToast } = useToasts();
  const history = useHistory();
  const { getAccessTokenSilently: getToken } = useAuth0();

  const createStudent = async (item) => {
    try {
      await fetchWithToken(`/api/students`, getToken, {
        method: "POST",
        headers: {
          "content-type": "application/json",
        },
        body: JSON.stringify(item),
      });
      history.push("/admin/students"); 
    } catch (err) {
      console.log("Caught error from create student");
    }
  };

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