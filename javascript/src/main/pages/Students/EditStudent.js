import React from "react";
import useSWR, { cache } from "swr";

import StudentForm from "main/components/Students/StudentForm";
import { fetchWithToken } from "main/utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import Loading from "main/components/Loading/Loading";
import { useParams, useHistory } from "react-router-dom";

const EditStudent = () => {
//   const { addToast } = useToasts();
  const history = useHistory();
  
  const { studentId } = useParams();
  const { getAccessTokenSilently: getToken } = useAuth0();

  const updateStudent = async (item, id) => {
    try {
        await fetchWithToken(`/api/students/${id}`, getToken, {
            method: "PUT",
            headers: {
            "content-type": "application/json",
            },
            body: JSON.stringify(item),
        });
        history.push("/admin/students"); 
    } catch (err) {
        console.log("Caught error from update student");
    }
  };

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