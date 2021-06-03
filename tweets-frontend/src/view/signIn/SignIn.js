import React, { Component } from "react";
import { connect } from "react-redux";
import styled from "styled-components";
import { Field, Form, Formik } from "formik";

import { signIn } from "../../data/actions/authentication";

const Container = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  min-height: 100vh;
  width: 100%;
  background: url(https://images7.alphacoders.com/380/thumb-1920-380540.jpg);
  background-size: cover;
`;

const Box = styled.div`
  width: 600px;
  height: 500px;
  border-radius: 30px;
  background: #fff;
  box-shadow: 5px 10px 15px 0 rgba(0,0,0,0.3);
`;

const Header = styled.h1`
  margin: 80px 0 30px;
  text-align: center;
`;

const FormWrapper = styled.div`
  width: 80%;
  padding: 10px;
  margin: 0 auto;
`;

const Input = styled.input`
  width: 100%;
  height: 55px;
  padding: 15px 30px;
  margin-bottom: 15px;
  border: 1px solid #a7a8af;
  border-radius: 30px;
  font-size: 18px;
  background: #fdfdfd;
`;

export const Button = styled.input`
  height: 55px;
  min-width: 130px;
  width: 100%;
  padding: 15px;
  flex-shrink: 0;
  border-radius: 30px;
  font-weight: 600;
  font-size: 20px;
  background-color: #1c6a77;
  color: #fff;
  cursor: pointer;
  border: none;
  transition: ease-in-out 400ms;

  &:hover {
    background-color: #023b43;

  }
`;

const initialFormValues = {
    username: "",
    password: ""
};

class SignIn extends Component {

    render = () => {
        return (
            <Formik
                initialValues={ initialFormValues }
                onSubmit={ values => this._onSubmit(values) }
                render={ () => (
                    <Container>
                        <Box>
                            <Header>Sign in</Header>
                            <FormWrapper>
                                <Form>

                                    <Field
                                        name="username"
                                        render={ ({ field }) => (
                                            <Input
                                                placeholder="username"
                                                type="text"
                                                { ...field }/>
                                        ) }/>

                                    <Field
                                        name="password"
                                        render={ ({ field }) => (
                                            <Input
                                                placeholder="password"
                                                type="password"
                                                { ...field }/>
                                        ) }/>
                                    <Button type="submit" value="Sign in"/>
                                </Form>
                            </FormWrapper>
                        </Box>
                    </Container>
                ) }
            />
        );
    };

    _onSubmit = (values) => {
        this.props.signIn({
            username: values.username,
            password: values.password
        });
    };
}

export default connect(null, { signIn })(SignIn);