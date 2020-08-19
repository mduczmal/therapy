import {MuiThemeProvider} from "@material-ui/core/styles";
import {TopBar, topBarTheme} from "./topbar";

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
