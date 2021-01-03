import {Box, CardContent, CardHeader} from "@material-ui/core";
import Grid from "@material-ui/core/Grid";
import Card from "@material-ui/core/Card";
import Typography from "@material-ui/core/Typography";
import React from "react";

export class Comments extends React.Component {

    constructor(props) {
        super(props);
    }

    render() {
        const labels = {
            delete: "Usuń komentarz"
        }
        return(
                <Box my={1}>
                    <Grid container spacing={1}>
                        {this.props.comments.map((comment) => (
                            <Grid item key={comment.id} xs={12}>
                                <Card>
                                    {/*comment.author !== null && this.state.user === comment.creator ?
                                        (<Button type='button' onClick={() => this.handleDelete(comment.id)}
                                                 startIcon={<DeleteIcon/>}>{labels.delete}</Button>) : null
                                    */}
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
                </Box>
        );
    }
}
