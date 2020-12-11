import React from "react";
import { render } from "@testing-library/react";
import HistogramOfMessagesByUser from "../../../main/pages/AnalyzeMessageData/HistogramOfMessagesByUser";

import useSWR from "swr";
jest.mock("swr");

import { useAuth0 } from "@auth0/auth0-react";
jest.mock("@auth0/auth0-react");

import { fetchWithToken } from "main/utils/fetch";
jest.mock("main/utils/fetch");

describe("HistogramOfMessagesByUser tests", () => {
  beforeEach(() => {
    useAuth0.mockReturnValue({
      getAccessTokenSilently: jest.fn(),
    });
  });

  const mockBackend = (results) => {
    useSWR.mockImplementation(([endpoint, getToken], fetch) => {
      if (endpoint === `/api/members/messages/usersearch?searchUser=""`)
        return {
          data: results,
        };
      else
        fail(`test called on unexpected endpoint: ${endpoint}`);
    });
  }

  test("renders without crashing", () => {
    render(<HistogramOfMessagesByUser />);
  });

  test("selectedUser state changes when user selects a user and presses go", () => {

  })

});
