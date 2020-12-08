import React from "react";
import { Button } from "react-bootstrap";
import useSWR from "swr";
import { StudentCSVButton } from "./StudentCSVButton";
import { fetchWithToken } from "main/utils/fetch";
import { useAuth0 } from "@auth0/auth0-react";
import Loading from "main/components/Loading/Loading";
import StudentTable from "main/components/Students/StudentTable";

import {useHistory} from "react-router-dom";

const Team1 = () => {
  const history = useHistory();
  const { getAccessTokenSilently: getToken } = useAuth0();
  // retrieves data 
  const { data: team1List, error, mutate: mutateStudents } = useSWR(
    ["/api/students/team1", getToken],
    fetchWithToken
  );

  if (error) {
    return (
      <h1>We encountered an error; please reload the page and try again.</h1>
    );
  }
  if (!team1List) {
    return <Loading />;
  }

  const deleteStudent = async (id) => {
    try {
      await fetchWithToken(`/api/students/team1/${id}`, getToken, {
        method: "DELETE",
        headers: {
          "content-type": "application/json",
        },
        noJSON: true,
      });
      await mutateStudents();
    } catch (err) {
      console.log("Caught error from delete students");
    }
  };

  return (
    <>
        <h1>
            Team1 student table
        </h1>
        <br/>
        <br/>
        <StudentTable students={team1List} deleteStudent={deleteStudent}/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
        <br/>
    </>
  );
};

export default Team1;