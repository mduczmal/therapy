import React from 'react';
import {MuiThemeProvider} from "@material-ui/core/styles";
import {TopBar, topBarTheme} from "./topbar";
import Card from "@material-ui/core/Card";
import {CardContent, CardHeader} from "@material-ui/core";
import Typography from "@material-ui/core/Typography";
import {getCookie} from "./hello";
import Box from "@material-ui/core/Box";

export class Details extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            ad: null,
            loaded: false
        }
    }

    componentDidMount() {
        this.getAd();
    }

    getAd() {
        const token = getCookie('XSRF-TOKEN');
        fetch("http://localhost:8080/v2/ad?id=" + this.props.id,
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
                        <Card>
                            <CardHeader title={this.state.ad.details.name + " " + this.state.ad.details.surname}/>
                            <CardContent>
                                <Typography color="textPrimary">
                                    {this.state.ad.details.address}
                                </Typography>
                            </CardContent>
                        </Card>
                    </Box>
                )
                }
            </div>
        );
    }
}
