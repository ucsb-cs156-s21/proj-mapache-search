import React from "react";
import { render } from "@testing-library/react";
import ChannelTable from "main/components/Channels/ChannelTable";
import userEvent from "@testing-library/user-event";

describe("ChannelTable tests", () => {

    test("renders without crashing on empty list", () => {
        render(<ChannelTable channels={[]} />);
    });

    test("includes link to channel", () => {
        const exampleChannel = {
            'id': 1,
            'name' : 'test-name',
            'purpose': {
                'value': 'Test Purpose'
            },
            'topic': {
                'value': 'Test Value'
            }
        };
        const expectedLink = `/member/channels/${exampleChannel.name}`;
        const { getByText} = render(<ChannelTable channels={[exampleChannel]} />);
        const linkElement = getByText(/click here/);
        expect(linkElement.href).toMatch(expectedLink);
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

  test("ascending and descending buttons can be pressed", () => {
    
    const channels = [
        {
            'id': 1,
            'name' : 'announcements',
            'purpose': {
                'value': 'This is the one channel that will always include everyone. It’s a great spot for announcements and team-wide conversations.'
            },
            'topic': {
                'value': 'Zoom link: Website: '
            }
        },
        {
            'id': 2,
            'name' : 'class-notes',
            'purpose': {
                'value': 'Instructor notes from lecture/discussion'
            },
            'topic': {
                'value': 'Course website: '
            }
        },
        {
            'id': 3,
            'name' : 'general',
            'purpose': {
                'value': 'This channel is for working on a project. Hold meetings, share docs, and make decisions together with your team.'
            },
            'topic': {
                'value': 'What’s up for discussion?'
            }
        },
    ];
    
    const { getByText } = render(<ChannelTable channels={channels}/>)
    const purposeHeader = getByText(/Purpose/);
    
    userEvent.click(purposeHeader);
    userEvent.click(purposeHeader);
    const descendingOFF = String.fromCharCode(0x25bd);
    expect(getByText(descendingOFF)).toBeInTheDocument();
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
    expect(topicLinkElement.href).toMatch('https://ucsb-cs156.github.io/w21/');
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
    expect(purposeLinkElement.href).toMatch('https://ucsb-cs156.github.io/w21/lab/jpa03');
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
    expect(LinkElements[0].href).toMatch('https://ucsb-cs156.github.io/w21/lab/jpa03');
    expect(LinkElements[1]).toBeInTheDocument();
    expect(LinkElements[1].href).toMatch('https://ucsb-cs156.github.io/w21/lab/jpa02');
    expect(LinkElements[2]).toBeInTheDocument();
    expect(LinkElements[2].href).toMatch('https://ucsb-cs156.github.io/w21/');
    expect(LinkElements[3]).toBeInTheDocument();
    expect(LinkElements[3].href).toMatch('https://ucsb.zoom.us/j/89220034995?pwd=VTlHNXJpTVgrSEs5QUtlMDdqMC9wQT09');
});

});
