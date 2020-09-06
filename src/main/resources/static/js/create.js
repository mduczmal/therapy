import React from 'react';
import { makeStyles, MuiThemeProvider} from "@material-ui/core/styles";
import {Button, Box, Container, Paper, Grid, TextField} from "@material-ui/core"
import {TopBar, topBarTheme} from "./topbar";
import {getCookie} from "./hello";

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
        this.state = {email: ''};
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        this.setState({email: event.target.value});
    }

    handleSubmit(event) {
        console.log("I am submitting");
        const token = getCookie('XSRF-TOKEN');
        fetch("http://localhost:8080/v2/person",
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-XSRF-TOKEN': token
                },
                body: JSON.stringify({
                    name: 'John',
                    surname: 'Malkovich',
                    email: this.state.email
                })
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
                    <TextField id="filled-basic" label="Email" variant="filled" onChange={this.handleChange}/>
                    <Button type="submit" color="inherit">Submit</Button>
                </form>
        )
    }
}

export function Create(props) {
    const classes = useStyles();
    return (
        <div className={classes.root}>
            <MuiThemeProvider theme={topBarTheme}>
                <TopBar/>
            </MuiThemeProvider>
            <Container>
                <Box mt={3}>
                    <Grid container spacing={2} justify={'center'}>
                        <Grid item xs={6}>
                            <Paper className={classes.paper}>
                                <CreateForm/>
                            </Paper>
                        </Grid>
                    </Grid>
                </Box>
            </Container>
        </div>
    );
}