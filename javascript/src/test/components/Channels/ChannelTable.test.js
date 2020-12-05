import React from "react";
import { render } from "@testing-library/react";
import ChannelTable from "main/components/Channels/ChannelTable";

describe("ChannelTable tests", () => {
  test("renders without crashing", () => {

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

    render(<ChannelTable channels={channels}/>);
  });
});
