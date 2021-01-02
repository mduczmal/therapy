import React from 'react';
import {MuiThemeProvider} from "@material-ui/core/styles";
import {TopBar, topBarTheme} from "./topbar";
import {Cookies} from "./cookies";
import {getCookie} from "./hello";
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import Typography from "@material-ui/core/Typography";
import {Box, CardContent, CardHeader} from "@material-ui/core";
import {Link as RouterLink} from "react-router-dom";

export class Ads extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            ads : null,
            loaded : false
        }
    }
    componentDidMount() {
        this.getAds();
    }
    getAds() {
        const token = getCookie('XSRF-TOKEN');
        fetch("http://localhost:8080/v2/ads",
            {
                method: 'POST',
                headers: {
                    'X-XSRF-TOKEN': token
                }
            }).then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        ...this.state,
                        ads: result,
                        loaded: true
                    });
                    console.log(this.state.ads);
                },
                (error) => {
                    console.log(error);
                });
    }

    render() {
        return (
            <div>
                <MuiThemeProvider theme={topBarTheme}>
                    <TopBar/>
                </MuiThemeProvider>
                <Box mt={3}>
                    <Grid container spacing={4}>
                        {!this.state.loaded? null : this.state.ads.map((ad) => (
                            <Grid item key={ad.id} xs={12}>
                                <Card>
                                    <CardHeader title={ad.details.name + " " + ad.details.surname}/>
                                    <CardContent>
                                        <Typography color="textPrimary">
                                            {ad.details.address}
                                        </Typography>
                                        <RouterLink to={{
                                            pathname: "/v2/details/"+ad.id,
                                        }}>
                                            {"Szczegóły"}
                                        </RouterLink>
                                    </CardContent>
                                </Card>
                            </Grid>
                        ))}
                    </Grid>
                </Box>
                <Cookies/>
            </div>
        );
    }
}