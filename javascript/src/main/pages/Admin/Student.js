import React, { useState } from "react";
import useSWR from "swr";
import { ListGroup } from "react-bootstrap";
import { StudentCSVButton } from "./StudentCSVButton";
import { fetchWithToken } from "main/utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import Loading from "main/components/Loading/Loading";
import StudentTable from "main/components/Students/StudentTable"

const Students = () => {
  const { getAccessTokenSilently: getToken } = useAuth0();
  // retrieves data 
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

  const deleteStudent = async (id) => {
    await fetchWithToken('/api/students/${id}', getToken, {
      method: "DELETE",
      headers: {
        "content-type": "application/json",
      },
      noJSON: true,
    });
    await mutateStudents();
  };

  const uploadCSV = async (file) => {
    const data = new FormData();
    data.append("csv", file);
    await fetchWithToken('/api/students/upload', getToken, {
      method: "POST",
      body: data
    });
    await mutateStudents();
  };

  return (
    <>
        <h1>
            Upload CSV File of Students
        </h1>
        <p>
          Make sure that the uploaded CSV contains a header
        </p>
        <StudentCSVButton addTask={uploadCSV}/>
        <StudentTable students={studentList} deleteStudent={deleteStudent}/>
    </>
  );
};

export default Students;