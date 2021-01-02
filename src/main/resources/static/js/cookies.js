import React from 'react';
import {Button, Card, CardContent, CardHeader, Drawer, makeStyles} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import Grid from "@material-ui/core/Grid";

export function Cookies(props) {

    const useStyles = makeStyles({
        root: {
            width: '30%',
            'border-top': '1px solid rgba(0, 0, 0, 0.30)',
            'border-right': '1px solid rgba(0, 0, 0, 0.30)'
            }
    });
    const classes = useStyles()
    const [state, setState] = React.useState({isOpen: true});
    const toggleDrawer = (open) => (event) => {
        if (event.type === 'keydown' && (event.key === 'Tab' || event.key === 'Shift')) {
            return;
        }
        setState({ ...state, isOpen: open });
    };
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
                                <Button onClick={toggleDrawer(false)}>{labels.accept}</Button>
                            </Grid>
                        </Grid>
                    </CardContent>
                </Card>
            </Drawer>
        </React.Fragment>
    );
}
