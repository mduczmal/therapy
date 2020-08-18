import {MuiThemeProvider} from "@material-ui/core/styles";
import {TopBar} from "./topbar";
import {topBarTheme} from "./hello";

const React = require('react');

export class AdDetails extends React.Component {
    render() {
        return (
            <MuiThemeProvider theme={topBarTheme}>
                <TopBar/>
            </MuiThemeProvider>
        );
    }
}
