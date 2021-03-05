import React, { useState } from "react";
import useSWR from "swr";
import { useAuth0 } from "@auth0/auth0-react";
import { fetchWithToken } from "main/utils/fetch";
import { Form, Row, Container, Col, Badge, InputGroup, FormControl, Button } from "react-bootstrap";

import ReactJson from "react-json-view";

const Profile = () => {

  const { user, getAccessTokenSilently: getToken } = useAuth0();
  const { name, picture, email } = user;
  const { data: roleInfo } = useSWR(
    ["/api/myRole", getToken],
    fetchWithToken
  );
  //const emptyToken = {
    //inputApiToken: "",
  //}

  const [apiToken, setApiToken] = useState("");
  const [results, setResults] = useState({});
  let currentApiTokenStatus = <p>You do not have an API Token associated with your account!</p>;
  const [tokenStatus, setTokenStatus] = useState("");

  // Function to add token to account here
  const addAPIToken = async (event) => {
    const url = `/api/addApiKey`;
    
        try {
          const result = await fetchWithToken(url, getToken, {
            method: 'PUT',
            headers: {
              'content-type': 'application/json',
            }, 
            body: apiToken
          });
          console.log(`result=${result}`)
          return result;
        } catch (err) {
          console.log(`err=${err}`)
        } 
  };

  const fetchApiToken = async (event) => {
    const url = `/api/apiKey`;
    try {
        const result = await fetchWithToken(url, getToken, {
            method: "GET",
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
  // Function to put handle on submit
  const handleOnSubmit = async (e) => {
     e.preventDefault();
    const answer = await addAPIToken(e);
    setResults(answer);
    const apiReturn = await fetchApiToken(e);
    alert('API was submitted: ' + apiReturn["token"]);
  }
  
  async function returnApiTokenStatus(){
    var apiStatus = await fetchApiToken();
    var status;
    if (apiStatus["token"] == "invalid token")
      status = "You do not have a valid API Token associated with your account! (Default will be used)";
    else
      status = "Your custom API token is " + apiStatus["token"];
    setTokenStatus(status);
  }
  

  returnApiTokenStatus();

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

      <p>{tokenStatus}</p>

      <p>
      Don't have an API key or want to learn more? <a href="https://developers.google.com/custom-search/v1/overview">Consider clicking here!</a>
      </p>

      <Form onSubmit={handleOnSubmit}>
                <Form.Group as={Row} controlId="search">
                    <Form.Label column sm={2}>API Search Token</Form.Label>
                    <Col sm={10}>
                        <Form.Control type="text" placeholder="Enter your API token" onChange={(e) => setApiToken(
                            e.target.value
                        )} />
                    </Col>
                </Form.Group>
                <Form.Group as={Row}>
                    <Col sm={{ span: 10, offset: 2 }}>
                        <Button type="submit">Submit</Button>
                    </Col>
                </Form.Group>
            </Form>
      <Row className="text-left">
        <ReactJson src={user} />
      </Row>
      <Container className="mb-5"><Row></Row></Container>
      <Container className="mb-5"><Row></Row></Container>
      <Container className="mb-5"><Row></Row></Container>
    </Container>
  );
};

export default Profile;
