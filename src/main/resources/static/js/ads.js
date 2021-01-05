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
import Button from "@material-ui/core/Button";
import DeleteIcon from "@material-ui/icons/Delete";
import EditIcon from "@material-ui/icons/Edit";

export class Ads extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            ads : null,
            loaded : false,
            user: null
        };
        this.handleDelete = this.handleDelete.bind(this);
    }
    componentDidMount() {
        this.getAds();
        this.getUser();
    }
    getAds() {
        const token = getCookie('XSRF-TOKEN');
        fetch("/v2/ads",
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
    getUser() {
        const token = getCookie('XSRF-TOKEN');
        fetch("/v2/user",
            {
                method: 'GET',
                headers: {
                    'X-XSRF-TOKEN': token
                }
            }).then(res => res.json())
            .then(
                (result) => {
                    console.log(result);
                    this.setState({
                        ...this.state,
                        user: result.hasOwnProperty("user") ? result.user : null,
                    });
                },
                (error) => {
                    console.log(error);
                });
    }
    handleDelete(id) {
        const token = getCookie('XSRF-TOKEN');
        fetch("/v2/ad?id=" + id,
            {
                method: 'DELETE',
                headers: {
                    'X-XSRF-TOKEN': token
                }
            }).then(res => res.json()).then(
            (result) => {
                if(result.success) {
                    const ads = this.state.ads;
                    this.setState({
                            ...this.state,
                            ads: ads.filter((a) => a.id !== id)
                        }
                    )
                }
            },
            (error) => {
                console.log(error);
            });
    }

    render() {
        const labels = {
            details: "Szczegóły",
            delete: "Usuń ogłoszenie",
            edit: "Edytuj ogłoszenie"
        }
        return (
            <Box>
                <MuiThemeProvider theme={topBarTheme}>
                    <TopBar/>
                </MuiThemeProvider>
                <Box mt={3}>
                    <Grid container spacing={4}>
                        {!this.state.loaded? null : this.state.ads.map((ad) => (
                            <Grid item key={ad.id} xs={4}>
                                <Card>
                                    {this.state.user !== null && this.state.user === ad.creator ?
                                        (<React.Fragment>
                                                <Button type='button' onClick={() => this.handleDelete(ad.id)}
                                                        startIcon={<DeleteIcon/>}>{labels.delete}</Button>
                                                <Button type='button' href={"/v2/edit/" + ad.id}
                                                        startIcon={<EditIcon/>}>{labels.edit}</Button>
                                            </React.Fragment>
                                        ) : null
                                    }
                                    <CardHeader title={ad.details.name + " " + ad.details.surname}/>
                                    <CardContent>
                                        <Typography color="textPrimary">
                                            {ad.details.address}
                                        </Typography>
                                        <RouterLink to={{
                                            pathname: "/v2/details/"+ad.id,
                                        }}>
                                            {labels.details}
                                        </RouterLink>
                                    </CardContent>
                                </Card>
                            </Grid>
                        ))}
                    </Grid>
                </Box>
                <Cookies/>
            </Box>
        );
    }
}