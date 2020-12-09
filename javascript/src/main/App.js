import React from "react";
import "main/App.css";
import AppNavbar from "main/components/Nav/AppNavbar";
import { Container } from "react-bootstrap";
import { Route, Switch } from "react-router-dom";
import AppFooter from "main/components/Footer/AppFooter";
import About from "main/pages/About/About";
import Home from "main/pages/Home/Home";
import Profile from "main/pages/Profile/Profile";
import PrivateRoute from "main/components/Auth/PrivateRoute";
import Admin from "main/pages/Admin/Admin";
import SlackUsers from "./pages/Admin/SlackUsers";
import SearchInfo from "./pages/Admin/SearchInfo";
import AuthorizedRoute from "main/components/Nav/AuthorizedRoute";
import ChannelList from "main/pages/Channels/ChannelList";

import ChannelPageList from "./pages/Channels/ChannelPageList";
import ChannelPageScrollable from "./pages/Channels/ChannelPageScrollable";

import Search from "main/pages/Search/Search";
import ChannelPage from "./pages/Channels/ChannelPage";

import SearchResults from "./pages/Messages/SearchResults";


function App() {
  return (
    <div className="App">
      <AppNavbar />
      <Container className="flex-grow-1 mt-5">
        <Switch>
          <AuthorizedRoute path="/admin" exact component={Admin} authorizedRoles={["admin"]} />
          <AuthorizedRoute path="/admin/slackUsers" exact component={SlackUsers} authorizedRoles={["admin"]} />
          <AuthorizedRoute path="/admin/searchInfo" exact component={SearchInfo} authorizedRoles={["admin"]} />
          <AuthorizedRoute path="/member/channels" exact component={ChannelList} authorizedRoles={["admin","member"]} />
          <AuthorizedRoute path="/member/search" exact component={Search} authorizedRoles={["admin","member"]} />
          <AuthorizedRoute path="/member/messages/search" component={SearchResults} authorizedRoles={["admin","member"]} />
          <AuthorizedRoute path="/member/listViewChannels/:channel" component={ChannelPageList} authorizedRoles={["admin","member"]} />
          <AuthorizedRoute path="/member/channels/:channel" component={ChannelPageScrollable} authorizedRoles={["admin","member"]} />
          <PrivateRoute path="/profile" component={Profile} />
          <Route path="/about" component={About} />
          <Route path="/" exact component={Home} />
        </Switch>
      </Container>
      <AppFooter />
    </div>
  );
}

export default App;
