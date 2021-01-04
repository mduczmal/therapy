import React from 'react';
import {MuiThemeProvider} from "@material-ui/core/styles";
import {TopBar, topBarTheme} from "./topbar";
import Card from "@material-ui/core/Card";
import {CardContent, CardHeader} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import {getCookie} from "./hello";
import Box from "@material-ui/core/Box";
import Avatar from "@material-ui/core/Avatar";
import {Cookies} from "./cookies";
import {Comments} from "./comments";

export class Details extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            ad: null,
            loaded: false,
        }
        this.getAd = this.getAd.bind(this);
        this.getId = this.getId.bind(this);
    }

    componentDidMount() {
        this.getAd();
    }

    getId() {
        return this.state.ad === null ? this.props.id : this.state.ad.id;
    }

    getAd() {
        const token = getCookie('XSRF-TOKEN');
        fetch("/v2/ad?id=" + this.getId(),
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
                        ad: result,
                        loaded: true
                    });
                },
                (error) => {
                    console.log(error);
                });
    }

    render() {
        return (
            <div>
                {!this.state.loaded ? null : (
                    <Box>
                        <MuiThemeProvider theme={topBarTheme}>
                            <TopBar/>
                        </MuiThemeProvider>
                        <Box mt={3}>
                            <Card>
                                <CardHeader title={this.state.ad.details.name + " " + this.state.ad.details.surname}
                                            avatar={
                                                <Avatar aria-label="therapistImage" height={10} width={10}
                                                        src={"/images/" + this.state.ad.details.imageId}/>
                                            }
                                />
                                <CardContent>
                                    <Typography color="textPrimary">
                                        {this.state.ad.details.address}
                                    </Typography>
                                    <Typography color="textPrimary">
                                        {this.state.ad.details.email}
                                    </Typography>
                                    <Typography color="textPrimary">
                                        {this.state.ad.details.telephoneNumber}
                                    </Typography>
                                    <Typography color="textPrimary">
                                        {this.state.ad.details.therapyInfo}
                                    </Typography>
                                    <Typography color="textPrimary">
                                        {this.state.ad.details.therapyApproach}
                                    </Typography>
                                </CardContent>
                            </Card>
                            <Comments ad={this.state.ad} reload={this.getAd}/>
                        </Box>
                        <Cookies/>
                    </Box>
                )
                }
            </div>
        );
    }
}
