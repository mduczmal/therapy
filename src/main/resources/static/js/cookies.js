import React, {useEffect} from 'react';
import {Button, Card, CardContent, CardHeader, Drawer, makeStyles} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";
import {getCookie} from "./hello";

export function Cookies(props) {

    const useStyles = makeStyles({
        root: {
            width: '30%',
            'border-top': '1px solid rgba(0, 0, 0, 0.30)',
            'border-right': '1px solid rgba(0, 0, 0, 0.30)'
            }
    });
    const classes = useStyles()
    const [state, setState] = React.useState({isOpen: false});
    const toggleDrawer = (open) => (event) => {
        console.log("called");
        if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
        }
        setState({ ...state, isOpen: open });
    };

    const checkCookies = () => {
        const token = getCookie('XSRF-TOKEN');
        fetch("http://localhost:8080/v2/cookies",
            {
                method: 'GET',
                headers: {
                    'X-XSRF-TOKEN': token
                }
            }).then(res => res.json())
            .then(
                (result) => {
                    console.log(result);
                    setState({ ...state, isOpen: !result.cookies });
                },
                (error) => {
                    console.log(error);
                });
    }
    useEffect(checkCookies, []);

    const acceptCookies = () => {
        setState({ ...state, isOpen: false });
        const token = getCookie('XSRF-TOKEN');
        fetch("http://localhost:8080/v2/cookies",
            {
                method: 'POST',
                headers: {
                    'X-XSRF-TOKEN': token
                }
            }).then(res => res.json())
            .then(
                (result) => {
                    console.log(result);
                },
                (error) => {
                    console.log(error);
                });
    }

    const labels = {
        cookiesTitle: "Informacja o ciasteczkach",
        cookiesInfo: "Nasza strona korzysta tylko z niezbędnych plików cookies.",
        accept: "Rozumiem"
    }
    return(
        <React.Fragment>
            <Drawer classes={{
               paperAnchorBottom: classes.root
            }} anchor="bottom" variant={"persistent"}
                    open={state["isOpen"]} onClose={toggleDrawer(false)}>
                <Card>
                    <CardHeader title={labels.cookiesTitle} />
                    <CardContent width="auto">
                        <Grid container>
                            <Grid item xs={10}>
                                <Typography color="textPrimary">
                                    {labels.cookiesInfo}
                                </Typography>
                            </Grid>
                            <Grid item xs={2}>
                                <Button onClick={acceptCookies}>{labels.accept}</Button>
                            </Grid>
                        </Grid>
                    </CardContent>
                </Card>
            </Drawer>
        </React.Fragment>
    );
}
