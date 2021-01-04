import {Box, CardContent, CardHeader} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import Typography from "@material-ui/core/Typography";
import React from "react";
import Button from "@material-ui/core/Button";
import {getCookie} from "./hello";
import DeleteIcon from "@material-ui/icons/Delete";

export class Comments extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            comments: this.props.ad.comments,
            loaded: false,
            moderator: false
        }
    }

    componentDidMount() {
        this.isModerator();
    }

    isModerator() {
        const token = getCookie('XSRF-TOKEN');
        fetch("/v2/has?role=moderator",
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
                        moderator: result.role,
                        loaded: true
                    });
                },
                (error) => {
                    console.log(error);
                });
    }
    handleDelete(id) {
        const token = getCookie('XSRF-TOKEN');
        fetch("/v2/comment/delete?id=" + id ,
            {
                method: 'POST',
                headers: {
                    'X-XSRF-TOKEN': token
                }
            }).then(res => res.json())
            .then(
                (result) => {
                    console.log(result);
                    this.setState({
                            ...this.state,
                            comments: this.state.comments.filter((c) => c.id !== id)
                        }
                    )
                },
                (error) => {
                    console.log(error);
                });
    }

    render() {
        const labels = {
            delete: "Usuń komentarz",
            comments: "Komentarze",
            createComment: "Dodaj komentarz"
        }
        return(
                <Box my={1}>
                    <Card width={"50%"}>
                        <CardHeader title={labels.comments} action={<Button>{labels.createComment}</Button>}/>
                        {!this.state.loaded? null : (
                            <CardContent>
                                <Grid container spacing={1}>
                                    {this.state.comments.map((comment) => (
                                        <Grid item key={comment.id} xs={12}>
                                            <Card>
                                                {this.state.moderator ?
                                                    (<Button type='button' onClick={() => this.handleDelete(comment.id)}
                                                             startIcon={<DeleteIcon/>}>{labels.delete}</Button>) : null
                                                }
                                                <CardHeader title={comment.author}/>
                                                <CardContent>
                                                    <Typography color="textPrimary">
                                                        {comment.content}
                                                    </Typography>
                                                </CardContent>
                                            </Card>
                                        </Grid>
                                    ))}
                    </Grid>
                        </CardContent>
                            )}
                    </Card>
                </Box>
        );
    }
}
