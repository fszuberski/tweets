import React, { Component } from "react";
import styled from "styled-components";
import { connect } from "react-redux";

import { signOut } from "../../data/actions/authentication";
import { getTweets } from "../../data/actions/tweets";
import { GetTweetsQueryTypesEnum } from "../../core/enums/get_tweets_query_types";
import { selectFeedType } from "../../data/actions/ui";

const Container = styled.div`
  padding: 0 30px;
  display: flex;
  justify-content: space-between;
  background-color: #02333b;
  color: #fff;
`;

const Group = styled.div`
  display: flex;
  align-items: center;
`;

const Tab = styled.div`
  padding: 15px;
  margin: 15px;
  border-bottom: 1px solid transparent;
  transition: ease-in-out 400ms;


  &:hover {
    cursor: pointer;
    border-color: #d6e3e4;
  }
  
  ${ ({ selected }) => selected && `
    border-color: #d6e3e4;
  ` } 
`;
export const Button = styled.button`
  min-width: 130px;
  padding: 10px;
  flex-shrink: 0;
  border-radius: 30px;
  font-weight: 600;
  font-size: 20px;
  background-color: #02333b;
  color: #fff;
  cursor: pointer;
  border: 1px solid #1c6a77;
  transition: ease-in-out 400ms;
  
  &:hover {
    border-color: #d6e3e4;
  }
`;

class Nav extends Component {
    render = () => {
        const { selectedFeedType, selectFeedType, getTweets, signOut } = this.props;
        return (
            <Container>
                <Group>
                    <Tab selected={ selectedFeedType === GetTweetsQueryTypesEnum.CREATED_BY_OTHER_USERS }
                         onClick={ () => {
                             return new Promise(resolve => resolve())
                                 .then(selectFeedType(GetTweetsQueryTypesEnum.CREATED_BY_OTHER_USERS))
                                 .then(getTweets(GetTweetsQueryTypesEnum.CREATED_BY_OTHER_USERS));
                         } }>
                        other users tweets
                    </Tab>
                    <Tab selected={ selectedFeedType === GetTweetsQueryTypesEnum.CREATED_BY_USER }
                         onClick={ () => {
                             return new Promise(resolve => resolve())
                                 .then(selectFeedType(GetTweetsQueryTypesEnum.CREATED_BY_USER))
                                 .then(getTweets(GetTweetsQueryTypesEnum.CREATED_BY_USER));
                         } }>
                        my tweets
                    </Tab>
                    <Tab selected={ selectedFeedType === GetTweetsQueryTypesEnum.ALL }
                         onClick={ () => {
                             return new Promise(resolve => resolve())
                                 .then(selectFeedType(GetTweetsQueryTypesEnum.ALL))
                                 .then(getTweets(GetTweetsQueryTypesEnum.ALL));
                         } }>
                        all tweets
                    </Tab>
                </Group>
                <Group>
                    <Button onClick={ signOut }>
                        sign out
                    </Button>
                </Group>
            </Container>
        );
    };
}

const mapStateToProps = (state) => {
    return {
        selectedFeedType: state.ui.feedType.selected
    };
};

export default connect(mapStateToProps, { selectFeedType, getTweets, signOut })(Nav);