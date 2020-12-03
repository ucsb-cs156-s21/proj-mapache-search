import React from "react";
import { render } from "@testing-library/react";
import ChannelTable from "main/components/Channels/ChannelTable";

describe("ChannelTable tests", () => {
    test("renders without crashing", () => {
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

});
