import React, { Component } from "react";
import { connect } from "react-redux";
import { Field, Form, Formik } from "formik";
import styled from "styled-components";

import { createTweet } from "../../data/actions/tweets";

const Wrapper = styled.div`
  width: 600px;
  max-width: 100%;
  display: flex;
  padding: 25px;
  margin-bottom: 15px;
  background-color: #fff;
  box-shadow: 0 5px 5px 0 rgba(0, 0, 0, 0.30);
  border-radius: 5px;
`;

const Avatar = styled.img`
  display: block;
  width: 50px;
  height: 50px;
  flex-shrink: 0;
  margin-right: 15px;
  border-radius: 50%;
`;

const Username = styled.span`
  width: 100%;
  display: block;
  font-weight: 600;
  margin-bottom: 15px;
  padding-left: 10px;
  padding-bottom: 10px;
  border-bottom: 1px solid #d6e3e4;
`;

const MessageInput = styled.textarea`
  width: 100%;
  min-height: 75px;
  padding: 15px;
  border-radius: 5px;
  border-color: #02333b;
`;

const SubmitButton = styled.input`
  display: block;
  min-width: 130px;
  padding: 10px;
  margin-top: 15px;
  margin-left: auto;
  margin-bottom: -10px;
  border-radius: 30px;
  font-size: 16px;
  background-color: #1c6a77;
  color: #fff;
  cursor: pointer;
  border: none;
  transition: ease-in-out 400ms;


  &:hover {
    background-color: #023b43;
  }
`;

const GroupRight = styled.div`
  flex-grow: 1;
`;

const initialFormValues = {
    text: ""
}

class  FeedInput extends Component {

    state = {
        isSubmitting: false
    };

    render = () => {
        const { isSubmitting } = this.state;
        const { username, profilePictureUrl } = this.props

        return (
            <Formik
                initialValues={ initialFormValues }
                onSubmit={ values => this._onSubmit(values)}
                render={ () => (
                    <Form>
                        <Wrapper>
                            <Avatar src={ profilePictureUrl } alt=""/>
                            <GroupRight>
                                <Username>
                                    { username }
                                </Username>

                                <Field
                                    name="text"
                                    render={ ({ field }) => (
                                        <MessageInput
                                            { ...field }/>
                                    )}/>

                                <SubmitButton
                                    type="submit"
                                    value={ isSubmitting ? "submitting..." : "submit" }
                                    disabled={ isSubmitting }/>
                            </GroupRight>
                        </Wrapper>
                    </Form>
                )}
            />
        );
    }

    _onSubmit = (values) => {
        this.setState({ isSubmitting: true });
        this.props.createTweet({
            text: values.text
        }).then(() => this.setState({ isSubmitting: false }));
    };
}

export default connect(null, { createTweet })(FeedInput);