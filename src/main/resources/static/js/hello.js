const React = require('react');
const ReactDOM = require('react-dom');
import Button from '@material-ui/core/Button';
import AppBar from '@material-ui/core/AppBar';
import Toolbar from '@material-ui/core/Toolbar';
import Typography from '@material-ui/core/Typography';
import IconButton from '@material-ui/core/IconButton';
import MenuIcon from '@material-ui/icons/Menu';
import { makeStyles } from '@material-ui/core/styles';
import { MuiThemeProvider, createMuiTheme } from '@material-ui/core/styles';
import green from '@material-ui/core/colors/green';
import teal from '@material-ui/core/colors/teal';

const theme = createMuiTheme({
    palette: {
        primary: {
            main: teal[500],
        },
        secondary: {
            main: green[500],
        },
    },
});

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

function TopBar(props) {
    const classes = useStyles();
    return (<AppBar position="static">
        <Toolbar>
            <IconButton edge="start" className={classes.menuButton} color="inherit" aria-label="menu">
                <MenuIcon/>
            </IconButton>
            <Typography variant="h6" className={classes.title}>
                Terapia
            </Typography>
            <LoginButton/>
        </Toolbar>
    </AppBar>
    );

}

function getCookie(n) {
    //https://stackoverflow.com/questions/5639346/what-is-the-shortest-function-for-reading-a-cookie-by-name-in-javascript
    let c = document.cookie.match('(^|;)\\s*' + n + '\\s*=\\s*([^;]+)');
    return c ? c.pop() : '';
}

class LoginButton extends React.Component {
    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }
    handleClick() {
        console.log("I am clicked");
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
                    console.log("I am loaded");
                    console.log(result);
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
            )
    }
    render() {
        return (
            <Button color="inherit" onClick={this.handleClick}>Login</Button>
        )
    }
}
class Hello extends React.Component {
    render() {
        return (
            <MuiThemeProvider theme={theme}>
                <TopBar/>
            </MuiThemeProvider>
        );
    }
}
ReactDOM.render( <Hello />, document.getElementById('react') );
