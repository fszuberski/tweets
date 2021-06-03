import React, { Component } from "react";
import styled from "styled-components";
import { connect } from "react-redux";

import { signOut } from "../../data/actions/authentication";

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
        return (
            <Container>
                <Group>
                    <Tab>
                        Page 1
                    </Tab>
                    <Tab>
                        Page 2
                    </Tab>
                    <Tab>
                        Page 3
                    </Tab>
                </Group>
                <Group>
                    <Button onClick={ this.props.signOut }>
                        Sign out
                    </Button>
                </Group>
            </Container>
        );
    }
}

export default connect(null, { signOut })(Nav);