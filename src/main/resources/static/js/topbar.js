import AppBar from "@material-ui/core/AppBar";
import Toolbar from "@material-ui/core/Toolbar";
import IconButton from "@material-ui/core/IconButton";
import HomeIcon from "@material-ui/icons/Home";
import Typography from "@material-ui/core/Typography";
import {LoginButton} from "./loginbutton";
import {CreateAdButton} from "./createAdButton";
import {createMuiTheme, makeStyles} from "@material-ui/core/styles";
import teal from "@material-ui/core/colors/teal";
import green from "@material-ui/core/colors/green";

const React = require('react');

const useStyles = makeStyles((theme) => ({
    root: {
        flexGrow: 1,
    },
    menuButton: {
        marginRight: theme.spacing(2),
    },
    title: {
        flexGrow: 1,
    },
}));

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


export function TopBar(props) {
    const classes = useStyles();
    const labels = {
        "login": "Zaloguj",
        "logout": "Wyloguj",
        "createAd": "Dodaj ogłoszenie",
        "pageTitle": "Terapia"
    }
    return (<AppBar position="static">
            <Toolbar>
                <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="home"
                            href={"/v2/ads"}>
                    <HomeIcon/>
                </IconButton>
                <Typography variant="h6" className={classes.title}>
                    {labels.pageTitle}
                </Typography>
                <CreateAdButton labels={labels}/>
                <LoginButton labels={labels}/>
            </Toolbar>
        </AppBar>
    );
}