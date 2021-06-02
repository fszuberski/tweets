import { Component } from "react";
import { connect } from "react-redux";
import { Router, Switch } from "react-router-dom";

import "./App.css";
import { createGlobalStyle } from "styled-components";
import history from "../core/history";
import SignIn from "./signIn/SignIn";
import Feed from "./feed/Feed";
import AppRoute from "../core/routes/app_route";
import SignedInAppRoute from "../core/routes/signed_in_app_route";

const GlobalStyle = createGlobalStyle`
  * {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    font-family: Roboto;
  }
`;

class App extends Component {

    render = () => {
        const { authentication, storageLoaded } = this.props;

        return (
            <Router history={ history }>
                <>
                    <GlobalStyle/>
                    <Switch>
                        <AppRoute
                            exact path={ "/" }
                            component={ SignIn }
                            authentication={ authentication }
                            storageLoaded={ storageLoaded }/>

                        <SignedInAppRoute
                            exact path={ "/feed" }
                            component={ Feed }
                            authentication={ authentication }
                            storageLoaded={ storageLoaded }

                        />
                    </Switch>
                </>
            </Router>
        );
    };
}

const mapStateToProps = (state) => {
    return {
        authentication: state.authentication,
        storageLoaded: state.misc.storage.loaded
    };
};


export default connect(mapStateToProps)(App);
