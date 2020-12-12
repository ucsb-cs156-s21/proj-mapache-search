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
import Teams from "main/pages/Admin/Teams";
import Admin from "main/pages/Admin/Admin";
import SlackUsers from "./pages/Admin/SlackUsers";
import SearchInfo from "./pages/Admin/SearchInfo";
import AuthorizedRoute from "main/components/Nav/AuthorizedRoute";
import ChannelList from "main/pages/Channels/ChannelList";
import ChannelPageList from "./pages/Channels/ChannelPageList";
import ChannelPageScrollable from "./pages/Channels/ChannelPageScrollable";
import AnalyzeReactions from "main/pages/AnalyzeMessageData/AnalyzeReactions";
import CountMessagesByUser from "main/pages/AnalyzeMessageData/CountMessagesByUser";
import HistogramOfMessagesByUser from "main/pages/AnalyzeMessageData/HistogramOfMessagesByUser";
import SearchMessagesByUser from "main/pages/AnalyzeMessageData/SearchMessagesByUser";
import Search from "main/pages/Search/Search";
import ChannelPage from "main/pages/Channels/ChannelPage";
import SearchResults from "main/pages/Messages/SearchResults";


function App() {
  return (
    <div className="App">
      <AppNavbar />
      <Container className="flex-grow-1 mt-5">
        <Switch>
          <AuthorizedRoute path="/admin" exact component={Admin} authorizedRoles={["admin"]} />
          <AuthorizedRoute path="/admin/slackUsers" exact component={SlackUsers} authorizedRoles={["admin"]} />
          <AuthorizedRoute path="/admin/teams" exact component={Teams} authorizedRoles={["admin"]} />
          <AuthorizedRoute path="/admin/searchInfo" exact component={SearchInfo} authorizedRoles={["admin"]} />
<<<<<<< HEAD
        <AuthorizedRoute path="/member/channels" exact component={ChannelList} authorizedRoles={["admin", "member"]} />
        <AuthorizedRoute path="/member/search" exact component={Search} authorizedRoles={["admin", "member"]} />
        <AuthorizedRoute path="/member/messages/search" component={SearchResults} authorizedRoles={["admin", "member"]} />
        <AuthorizedRoute path="/member/listViewChannels/:channel" component={ChannelPageList} authorizedRoles={["admin", "member"]} />
        <AuthorizedRoute path="/member/channels/:channel" component={ChannelPageScrollable} authorizedRoles={["admin", "member"]} />
=======
          <AuthorizedRoute path="/member/channels" exact component={ChannelList} authorizedRoles={["admin", "member"]} />
        <AuthorizedRoute path="/member/search" exact component={Search} authorizedRoles={["admin", "member"]} />
        <AuthorizedRoute path="/member/messages/search" component={SearchResults} authorizedRoles={["admin", "member"]} />
        <AuthorizedRoute path="/member/channels/:channel" component={ChannelPageScrollable} authorizedRoles={["admin", "member"]} />
>>>>>>> abff33a22442b759f7dfe7d6b645adda3675e98f
        <AuthorizedRoute path="/member/analyzemessages/reactions" exact component={AnalyzeReactions} authorizedRoles={["admin", "member"]} />
        <AuthorizedRoute path="/member/analyzemessages/countmessages" exact component={CountMessagesByUser} authorizedRoles={["admin", "member"]} />
        <AuthorizedRoute path="/member/analyzemessages/messagehistogram" exact component={HistogramOfMessagesByUser} authorizedRoles={["admin", "member"]} />
        <AuthorizedRoute path="/member/analyzemessages/searchmessages" exact component={SearchMessagesByUser} authorizedRoles={["admin", "member"]} />
<<<<<<< HEAD
=======
          <AuthorizedRoute path="/member/listViewChannels/:channel" component={ChannelPageList} authorizedRoles={["admin", "member"]} />
>>>>>>> abff33a22442b759f7dfe7d6b645adda3675e98f
      <PrivateRoute path="/profile" component={Profile} />
      <Route path="/about" component={About} />
      <Route path="/" exact component={Home} />
        </Switch>
      </Container >
    <AppFooter />
    </div >
  );
}

export default App;
