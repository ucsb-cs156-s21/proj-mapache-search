import React from "react";
import ReactDOM from "react-dom";
import App from "main/App";
import Auth0ProviderWithHistory from "main/components/Auth/Auth0ProviderWithHistory";
import { BrowserRouter } from "react-router-dom";
import * as serviceWorker from "./serviceWorker";
import "react-bootstrap-table-next/dist/react-bootstrap-table2.min.css";
import "./index.css";
import "bootstrap/dist/css/bootstrap.css";

ReactDOM.render(
  <React.StrictMode>
    <BrowserRouter>
      <Auth0ProviderWithHistory>
        <App />
      </Auth0ProviderWithHistory>
    </BrowserRouter>
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want your app to work offline and load faster, you can change
// unregister() to register() below. Note this comes with some pitfalls.
// Learn more about service workers: https://bit.ly/CRA-PWA
serviceWorker.unregister();
