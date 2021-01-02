import React from 'react';
import {MuiThemeProvider} from '@material-ui/core/styles';
import {TopBar, topBarTheme} from "./topbar";


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
