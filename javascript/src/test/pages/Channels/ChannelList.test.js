import React from "react";
import { render } from "@testing-library/react";
import ChannelList from "main/pages/Channels/ChannelList";
import useSWR from "swr";
jest.mock("swr");

describe("ChannelList tests", () => {
    test("renders without crashing", () => {
        useSWR.mockReturnValue({});
        render(<ChannelList />);
    });
    
    test("Accesses channels from backend", () => {
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

        useSWR.mockReturnValue({
            'data': [exampleChannel]
        });
        const { getByText } = render(<ChannelList />);
        const nameElement = getByText(/test-name/);
        const purposeElement = getByText(/Test Purpose/);
        const topicElement = getByText(/Test Value/);
        expect(nameElement).toBeInTheDocument();
        expect(purposeElement).toBeInTheDocument();
        expect(topicElement).toBeInTheDocument();
    });

});
