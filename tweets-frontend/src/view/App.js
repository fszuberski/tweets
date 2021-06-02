import './App.css';
import Login from "./login/Login";
import {createGlobalStyle} from 'styled-components'
import Feed from './feed/Feed';

const GlobalStyle = createGlobalStyle`
  * {
    box-sizing: border-box;
    margin: 0;
    padding: 0;
    font-family: Roboto;
  }
`

const App = () => {
    return <>
        <GlobalStyle/>
        {/*<Feed/>*/}
        <Login/>
    </>
}

export default App;
