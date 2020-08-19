import React from 'react';
import {MuiThemeProvider} from "@material-ui/core/styles";
import {TextField} from "@material-ui/core"
import {TopBar, topBarTheme} from "./topbar";
import { makeStyles } from "@material-ui/core/styles";
import {getCookie} from "./hello";
import Button from "@material-ui/core/Button";

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.secondary,
    },
}));

class CreateForm extends React.Component {
    constructor(props) {
        super(props);
        this.handleSubmit = this.handleSubmit.bind(this);
    }

    handleSubmit(event) {
        console.log("I am submitting");
        const token = getCookie('XSRF-TOKEN');
        fetch("http://localhost:8080/data",
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-XSRF-TOKEN': token
                },
            })
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                    });
                    console.log(result);
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
            )
        event.preventDefault();
    }

    render() {
        return (
                <form noValidate autoComplete="off" onSubmit={this.handleSubmit}>
                    <TextField id="filled-basic" label="Email" variant="filled" />
                    <Button type="submit" color="inherit">Submit</Button>
                </form>
        )
    }
}

export function Create(props) {
    return (
        <React.Fragment>
            <MuiThemeProvider theme={topBarTheme}>
                <TopBar/>
            </MuiThemeProvider>
            <CreateForm/>
        </React.Fragment>
    );
}