import React from "react";
import { render } from "@testing-library/react";
import ChannelTable from "main/components/Channels/ChannelTable";

describe("ChannelTable tests", () => {

    test("renders without crashing on empty list", () => {
        render(<ChannelTable channels={[]} />);
    });

  test("renders without crashing on non empty list", () => {

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


  test("channel topic includes clickable links", () => {
    const exampleChannel = {
        'id': 1,
        'name' : 'test-name',
        'purpose': {
            'value': 'Test Purpose'
        },
        'topic': {
            'value': 'Course website: <https://ucsb-cs156.github.io/w21/>'
        }
    };
    const { getByText} = render(<ChannelTable channels={[exampleChannel]} />);
    const topicLinkElement = getByText(/https:\/\/ucsb-cs156.github.io\/w21\//);
    expect(topicLinkElement).toBeInTheDocument();
    expect(topicLinkElement.href).toEqual('https://ucsb-cs156.github.io/w21/');
});

test("channel purpose includes clickable links", () => {
    const exampleChannel = {
        'id': 1,
        'name' : 'test-name',
        'purpose': {
            'value': 'Help with: <https://ucsb-cs156.github.io/w21/lab/jpa03>'
        },
        'topic': {
            'value': 'Test topic'
        }
    };
    const { getByText} = render(<ChannelTable channels={[exampleChannel]} />);
    const purposeLinkElement = getByText(/https:\/\/ucsb-cs156.github.io\/w21\/lab\/jpa03/);
    expect(purposeLinkElement).toBeInTheDocument();
    expect(purposeLinkElement.href).toEqual('https://ucsb-cs156.github.io/w21/lab/jpa03');
});

test("multiple links clickable in channel topic or purpose", () => {
    const exampleChannel = {
        'id': 1,
        'name' : 'test-name',
        'purpose': {
            'value': 'Help with: <https://ucsb-cs156.github.io/w21/lab/jpa03> Second link: <https://ucsb-cs156.github.io/w21/lab/jpa02>'
        },
        'topic': {
            'value': 'Course website: <https://ucsb-cs156.github.io/w21/> Zoom link: <https://ucsb.zoom.us/j/89220034995?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09>'
        }
    };
    const { getAllByText} = render(<ChannelTable channels={[exampleChannel]} />);
    const LinkElements = getAllByText(/(http(?:.*))/);
    expect(LinkElements).toHaveLength(4);
    expect(LinkElements[0]).toBeInTheDocument();
    expect(LinkElements[0].href).toEqual('https://ucsb-cs156.github.io/w21/lab/jpa03');
    expect(LinkElements[1]).toBeInTheDocument();
    expect(LinkElements[1].href).toEqual('https://ucsb-cs156.github.io/w21/lab/jpa02');
    expect(LinkElements[2]).toBeInTheDocument();
    expect(LinkElements[2].href).toEqual('https://ucsb-cs156.github.io/w21/');
    expect(LinkElements[3]).toBeInTheDocument();
    expect(LinkElements[3].href).toEqual('https://ucsb.zoom.us/j/89220034995?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09');
});

test("channel names includes clickable links", () => {
    const exampleChannel = {
        'id': 1,
        'name' : 'announcements',
        'purpose': {
            'value': 'Test Purpose'
        },
        'topic': {
            'value': 'Course website: <https://ucsb-cs156.github.io/w21/>'
        }
    };
    const { getByText} = render(<ChannelTable channels={[exampleChannel]} />);
    const channelLinkElement = getByText(/announcements/);
    expect(channelLinkElement).toBeInTheDocument();
    expect(channelLinkElement.href).toEqual('http://localhost/member/channels/announcements');
});

});
