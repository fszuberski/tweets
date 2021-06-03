import React, { Component } from "react";
import styled from "styled-components";
import FeedItem from "./FeedItem";
import Nav from "./Nav";
import { connect } from "react-redux";
import FeedInput from "./FeedInput";
import { getTweets } from "../../data/actions/tweets";
import { isNullOrUndefined } from "../../core/utils/misc_utils";

const Container = styled.div`
  min-height: 100vh;
  background: #02333b;
`;

const List = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 80px 0;
`;

const LoadingIndicator = styled.div`
  color: white;
`;

class Feed extends Component {

    render = () => {
        const { username, profilePictureUrl } = this.props.authentication.data;
        return (
            <Container>
                <Nav/>
                <List>
                    <FeedInput
                        username={ username }
                        profilePictureUrl={ profilePictureUrl }/>
                    {
                        this.renderTweets()
                    }
                </List>
            </Container>
        );
    };

    renderTweets = () => {
        const { data } = this.props.tweets;

        if (isNullOrUndefined(data)) {
            return <LoadingIndicator>loading Tweets...</LoadingIndicator>;
        }

        return data.map(tweet => {
            return <FeedItem key={ tweet.id } tweet={ tweet }/>
        })
    };

    componentDidMount() {
        this.props.getTweets();
    }
}

const mapStateToProps = (state) => {
    return {
        authentication: state.authentication,
        tweets: state.entities.tweets.get
    };
};

export default connect(mapStateToProps, { getTweets })(Feed);
