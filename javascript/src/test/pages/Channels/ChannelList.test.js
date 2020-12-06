import React from "react";
import { render } from "@testing-library/react";
import ChannelList from "main/pages/Channels/ChannelList";


import { useAuth0 } from "@auth0/auth0-react";
jest.mock("@auth0/auth0-react");
import useSWR from "swr";
jest.mock("swr");
import { fetchWithToken } from "main/utils/fetch";
jest.mock("main/utils/fetch");

describe("ChannelList tests", () => {

    const channels = [
        {
            "id": "C016GMB0H5L",
            "name": "section-6pm",
            "creator": "U017218J9B3",
            "is_archived": false,
            "is_general": false,
            "members": [
                "U017218J9B3",
                "U0185QQSJBY",
                "U018CH1NLPL",
                "U018R1AULF3",
                "U018XEKMRTM",
                "U0192BC785N",
                "U019B1Q0YHW"
            ],
            "topic": {
                "value": "",
                "creator": "",
            },
            "purpose": {
                "value": "",
                "creator": "",
            }
        }
    ];

    beforeEach(() => {
        useAuth0.mockReturnValue({
          getAccessTokenSilently: jest.fn(),
        });
      });

    const mockBackend = (results) => {
        useSWR.mockImplementation(([endpoint, getToken], fetch) => {
            if (endpoint === "/api/members/channels")
              return {
                data: results,
              };
            else
                fail(`test called on unexpected endpoint: ${endpoint}`);
          });
    }

    test("renders empty list without crashing", () => {
        mockBackend(null);
        render(<ChannelList />);
    });

    test("renders list with results without crashing", () => {
        mockBackend(channels);
        render(<ChannelList />);
    });
});
