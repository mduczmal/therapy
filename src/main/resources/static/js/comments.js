import {Box, CardContent, CardHeader} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import Typography from "@material-ui/core/Typography";
import React from "react";
import Button from "@material-ui/core/Button";
import {getCookie} from "./hello";
import DeleteIcon from "@material-ui/icons/Delete";
import {CreateComment} from "./createComment";

export class Comments extends React.Component {

    constructor(props) {
        super(props);
        this.state = {
            loaded: false,
            moderator: false,
            creating: false,
            data: {
                author: "",
                content: ""
            }
        }
        this.handleCommentChange = this.handleCommentChange.bind(this);
        this.handleCommentSubmit = this.handleCommentSubmit.bind(this);
        this.handleCreate = this.handleCreate.bind(this);
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
                    this.props.reload();
                },
                (error) => {
                    console.log(error);
                });
    }

    handleCommentSubmit(event) {
        const token = getCookie('XSRF-TOKEN');
        fetch("/v2/comment/create?ad=" + this.props.ad.id,
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-XSRF-TOKEN': token
                },
                body: JSON.stringify(this.state.data)
            })
            .then(res => res.json())
            .then(
                (result) => {
                    console.log(result);
                    this.setState({
                        ...this.state,
                        creating: false
                    });
                },
                (error) => {
                    this.setState({
                        ...this.state,
                        creating: false
                    });
                    console.log(error);
                }
            ).then(() => {
            this.props.reload();
        });
        event.preventDefault();
    }

    handleCommentChange(event) {
        const name = event.target.name;
        this.setState({
            ...this.state,
            data: {
                ...this.state.data,
                [name]: event.target.value
            }
        });
    }



    handleCreate() {
        this.setState({
            ...this.state,
            creating: true
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
                        <CardHeader title={labels.comments} action={<Button onClick={this.handleCreate}>{labels.createComment}</Button>}/>
                        {!this.state.loaded? null : (
                            <CardContent>
                                <Grid container spacing={1}>
                                        {!this.state.creating? null : (
                                            <Grid item xs={4}>
                                            <CreateComment ad={this.props.ad}
                                                           handleSubmit={(event) => this.handleCommentSubmit(event)}
                                                           handleChange={(event) => this.handleCommentChange(event)}/>
                                            </Grid>
                                        )}
                                    {this.props.ad.comments.map((comment) => (
                                        <Grid item key={comment.id} xs={4}>
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
