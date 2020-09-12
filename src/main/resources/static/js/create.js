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
        color: theme.palette.text.primary,
    },
}));

class CreateForm extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            num: 0,
            data: {
                name: 'John',
                surname: 'Malkovich',
                address: '',
                description: '',
                pricing: {},
                therapyCenter: '',
                imagePath: '',
                telephoneNumber: '',
                email: '',
                therapyApproach: '',
                training: '',
                supervision: false,
                onlineSessions: false
            }
        };
        this.handleSubmit = this.handleSubmit.bind(this);
        this.handleChange = this.handleChange.bind(this);
    }

    handleChange(event) {
        const name = event.target.name;
        this.setState({
            ...this.state,
            data: {
                ...this.state.data,
                [name]: event.target.value
            }
        });
    }

    handleSubmit(event) {
        console.log("I am submitting");
        const token = getCookie('XSRF-TOKEN');
        fetch("http://localhost:8080/v2/ad",
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-XSRF-TOKEN': token
                },
                body: JSON.stringify(this.state.data)
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
                    <Grid container spacing={1} alignItems={'center'}>
                        <Grid item xs={12} style={{textAlign: 'left'}}>
                            <p>{this.props.labels.personalInfo}</p>
                        </Grid>
                        <Grid item xs={6}>
                            <TextField id="filled-basic" label={this.props.labels.name} variant="filled" name="name" onChange={this.handleChange}/>
                        </Grid>
                        <Grid item xs={6}>
                            <TextField id="filled-basic" label={this.props.labels.surname} variant="filled" name="surname" onChange={this.handleChange}/>
                        </Grid>
                        <Grid item xs={6}>
                            <TextField id="filled-basic" label={this.props.labels.email} variant="filled" name="email" onChange={this.handleChange}/>
                        </Grid>
                        <Grid item xs={6}>
                            <TextField id="filled-basic" label={this.props.labels.telephoneNumber} variant="filled" name="telephoneNumber" onChange={this.handleChange}/>
                        </Grid>
                        <Grid item xs={12}>
                            <p>{this.props.labels.therapyInfo}</p>
                        </Grid>
                        <Grid item xs={6}>
                            <TextField id="filled-basic" label={this.props.labels.address} variant="filled" name="address" onChange={this.handleChange}/>
                        </Grid>
                        <Grid item xs={6}>
                            <Button type="submit" color="inherit">{this.props.labels.submit}</Button>
                        </Grid>
                    </Grid>
                </form>
        )
    }
}

export function Create(props) {
    const classes = useStyles();
    const labels = {
        personalInfo: 'Informacje o Tobie',
        name: 'Imię',
        surname: 'Nazwisko',
        email: 'Email',
        telephoneNumber: 'Telefon',
        address: 'Adres gabinetu',
        therapyInfo: 'Informacje o terapii',
        submit: 'Dodaj ogłoszenie'
    }
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
                                <CreateForm labels={labels}/>
                            </Paper>
                        </Grid>
                    </Grid>
                </Box>
            </Container>
        </div>
    );
}