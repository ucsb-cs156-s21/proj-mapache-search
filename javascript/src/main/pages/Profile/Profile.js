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
  const emptyToken = {
    inputApiToken: "",
  }

<<<<<<< HEAD
  const [apiToken, setApiToken] = useState(emptyToken);
  const [results, setResults] = useState({});

  // Function to add token to account here
  const addAPIToken = async (event) => {
    const url = `/api/addApiKey`;
=======
  // Function to add token to account here
  const addAPIToken = async (event) => {
    const url = `/apiKey/${apiToken.inputApiToken}`;
>>>>>>> 6dca1329b74d108ad745acb32b232132ce3bb751
    
        try {
          const result = await fetchWithToken(url, getToken, {
            method: "PUT",
            headers: {
              "content-type": "application/json",
<<<<<<< HEAD
            }, 
            body: {
              "token": apiToken
            }
=======
            },
>>>>>>> 6dca1329b74d108ad745acb32b232132ce3bb751
          });
          console.log(`result=${JSON.stringify(result)}`)
          return result;
        } catch (err) {
          console.log(`err=${err}`)
        } 
  };

  const fetchApiToken = async (event) => {
<<<<<<< HEAD
    const url = `/api/apiKey`;
=======
    const url = `/apiKey`;
>>>>>>> 6dca1329b74d108ad745acb32b232132ce3bb751
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
<<<<<<< HEAD

=======
  const [apiToken, setApiToken] = useState(emptyToken);
  const [results, setResults] = useState({});
>>>>>>> 6dca1329b74d108ad745acb32b232132ce3bb751
  // Function to put handle on submit
  const handleOnSubmit = async (e) => {
     e.preventDefault();
    const answer = await addAPIToken(e);
    setResults(answer);
    const apiReturn = await fetchApiToken(e);
    if(apiReturn === null)
      alert('API null !!!!!!!!!');
    alert('API was submitted: ' + apiReturn);
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
      {/* <Row className="align-items-center mb-5 text-center text-md-left">
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

      </Row> */}
      <Form onSubmit={handleOnSubmit}>
                <Form.Group as={Row} controlId="search">
                    <Form.Label column sm={2}>Search</Form.Label>
                    <Col sm={10}>
                        <Form.Control type="text" placeholder="type your token" onChange={(e) => setApiToken({
                            ...apiToken,
                            inputApiToken: e.target.value
                        })} />
                    </Col>
                </Form.Group>
                <Form.Group as={Row}>
                    <Col sm={{ span: 10, offset: 2 }}>
                        <Button type="submit">Submit</Button>
                    </Col>
                </Form.Group>
            </Form>
      ur api is ${fetchApiToken}
      <Row className="text-left">
        <ReactJson src={user} />
      </Row>
    </Container>
  );
};

export default Profile;
