import React from 'react';
import styled from 'styled-components';
import FeedItem from './FeedItem';
import { FeedInput } from './FeedItem';
import Nav from './Nav';

const Container = styled.div`
  min-height: 100vh;
  background: #02333b;
`

const List = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 80px 0;
`

const Feed = () => {
    return (
        <Container>
            <Nav/>
            <List>
                <FeedInput/>
                <FeedItem/>
                <FeedItem/>
                <FeedItem/>
                <FeedItem/>
            </List>
        </Container>
    );
}

export default Feed;
