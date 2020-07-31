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
            <Button color="inherit">Login</Button>
        </Toolbar>
    </AppBar>
    );

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
