import React from 'react';
import {makeStyles, MuiThemeProvider} from "@material-ui/core/styles";
import {Box, Container, Grid, Paper} from "@material-ui/core"
import {TopBar, topBarTheme} from "./topbar";
import {CreateForm} from "./createForm";
import {Cookies} from "./cookies";

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    paper: {
        padding: theme.spacing(2),
        textAlign: 'center',
        color: theme.palette.text.primary,
    },
    button: {
        color: theme.palette.primary.main
    },
    checkbox: {
        color: theme.palette.primary.main
    },
    avatar: {
        height: theme.spacing(10),
        width: theme.spacing(10)
    }
}));

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
        therapyApproach: 'Nurt psychoterapeutyczny',
        submit: 'Dodaj ogłoszenie',
        supervision: 'Korzystam z superwizji',
        onlineSessions: 'Prowadzę sesje online',
        pricing: 'Cennik'
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
                                <CreateForm labels={labels} avatar={classes.avatar}
                                            button={classes.button} checkbox={classes.checkbox}/>
                            </Paper>
                        </Grid>
                    </Grid>
                </Box>
            </Container>
            <Cookies/>
        </div>
    );
}