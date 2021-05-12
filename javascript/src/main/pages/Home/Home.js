import React from "react";
import {useAuth0} from "@auth0/auth0-react";
import { CardDeck, Card } from "react-bootstrap";

const Home = () => {
    const { isAuthenticated: _isAuthenticated } = useAuth0();

    return (
            <div>
      <h1>Mapache Search</h1>
      <CardDeck>
        <Card>
          <Card.Body>
            <Card.Title>Students</Card.Title>
            <Card.Text>Students can use the app to search for and share helpful resources with their class.</Card.Text>
          </Card.Body>
        </Card>
        <Card>
          <Card.Body>
            <Card.Title>Instructors</Card.Title>
            <Card.Text>Instructors can monitor the types of searchers that students are performing, and point them in the right direction.</Card.Text>
          </Card.Body>
        </Card>
        <Card>
          <Card.Body>
            <Card.Title>Researchers</Card.Title>
            <Card.Text>Researchers can use the data from Mapache Search to study metacognition - in particular, the way students formulate questions.</Card.Text>
            <a href="https://www.nsf.gov/awardsearch/showAward?AWD_ID=1915198&HistoricalAwards=false" target="_blank" rel="noopener noreferrer">
              Read More About The Research Project
            </a>
          </Card.Body>
        </Card>
      </CardDeck>
    </div>
    );
};

export default Home;
