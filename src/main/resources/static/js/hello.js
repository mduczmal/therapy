import {createMuiTheme, MuiThemeProvider} from '@material-ui/core/styles';
import green from '@material-ui/core/colors/green';
import teal from '@material-ui/core/colors/teal';
import {TopBar} from "./topbar";

const React = require('react');

export const topBarTheme = createMuiTheme({
    palette: {
        primary: {
            main: teal[500],
        },
        secondary: {
            main: green[500],
        },
    },
});

export function getCookie(n) {
    //https://stackoverflow.com/questions/5639346/what-is-the-shortest-function-for-reading-a-cookie-by-name-in-javascript
    let c = document.cookie.match('(^|;)\\s*' + n + '\\s*=\\s*([^;]+)');
    return c ? c.pop() : '';
}

export class Hello extends React.Component {
    render() {
        return (
            <MuiThemeProvider theme={topBarTheme}>
                <TopBar/>
            </MuiThemeProvider>
        );
    }
}
