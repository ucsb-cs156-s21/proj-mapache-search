import React, { useState } from "react";
import useSWR from "swr";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "main/utils/fetch";
import { Row, Container, Col, Badge, InputGroup, FormControl, Button } from "react-bootstrap";

import ReactJson from "react-json-view";

const Profile = () => {

  const { user, getAccessTokenSilently: getToken } = useAuth0();
  const { name, picture, email } = user;
  const { data: roleInfo } = useSWR(
    ["/api/myRole", getToken],
    fetchWithToken
  );
  const emptyToken = {
    inputApiToken: "",
  }

  // Function to add token to account here
  const addAPIToken = async (event) => {
    const url = `/apiKey/${apiToken.inputApiToken}`;
    
        try {
          const result = await fetchWithToken(url, getToken, {
            method: "PUT",
            headers: {
              "content-type": "application/json",
            },
          });
          console.log(`result=${JSON.stringify(result)}`)
          return result;
        } catch (err) {
          console.log(`err=${err}`)
        } 
  };

  const [apiToken, setApiToken] = useState(emptyToken);
  const [results, setResults] = useState({});

  // Function to put handle on submit
  const handleOnSubmit = async (e) => {
     e.preventDefault();
    const answer = await addAPIToken(e);
    setResults(answer);
    alert('API was submitted');
  }


  return (
    <Container className="mb-5">
      <Row className="align-items-center profile-header mb-5 text-center text-md-left">
        <Col md={2}>
          <img
            src={picture}
            alt="Profile"
            className="rounded-circle img-fluid profile-picture mb-3 mb-md-0"
          />
        </Col>
        <Col md>
          <h2>{name}</h2>
          <p className="lead text-muted">{email}</p>
          {roleInfo ?
            <Badge variant="info">{roleInfo.role}</Badge> :
            <span>Loading role...</span>
          }
        </Col>
      </Row>
      <Row className="align-items-center mb-5 text-center text-md-left">
        <InputGroup className="mb-3" onSubmit={handleOnSubmit}>
          <InputGroup.Prepend>
            <InputGroup.Text id="inputGroup-sizing-default">API Key</InputGroup.Text>
          </InputGroup.Prepend>
          <FormControl
            placeholder="Enter API Key"
            aria-label="Enter API Key"
            aria-describedby="inputGroup-sizing-default"
          />
          <InputGroup.Append>
            <Button type="submit" variant="outline-secondary">Submit</Button>
          </InputGroup.Append>
        </InputGroup>

      </Row>
      <Row className="text-left">
        <ReactJson src={user} />
      </Row>
    </Container>
  );
};

export default Profile;
