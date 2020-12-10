import React from 'react';
import { render } from '@testing-library/react';
import CountMessagesByUser from 'main/pages/AnalyzeMessageData/CountMessagesByUser';
import aggregateUserMessageCount from 'main/utils/aggregateUserMessageCount';
describe('Count Messages By User tests', () => {

  test('renders without errors', () => {
    render(<CountMessagesByUser />);
  });

  test('array test', () =>{
    var message = {user_profile: {real_name: 'Jane Doe'}}; 
    var message2 = {user_profile: {real_name: 'John Doe'}}; 
    const messages = [];
    messages.push(message);
    messages.push(message);
    messages.push(message2);
    const slackUsers = [{real_name:'Jane Doe'}];
    const answer = aggregateUserMessageCount(messages, slackUsers);
    const expected = [{name:'Jane Doe', count:'2'}];
    expect(answer).toMatchObject(expected);
  });

});

