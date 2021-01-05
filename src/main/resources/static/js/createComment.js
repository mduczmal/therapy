import React from "react";
import {Button, Card, Grid, TextField} from "@material-ui/core";

export class CreateComment extends React.Component {
    constructor(props) {
        super(props);
    }
    render() {
        const labels = {
            title: "Autor komentarza",
            content: "Treść komentarza",
            submit: "Opublikuj"
        }
        return (
            <Card>
                <form noValidate autoComplete="off" onSubmit={this.props.handleSubmit}>
                    <Grid container spacing={1} alignItems={'center'}>
                        <Grid item xs={12}>
                            <TextField id="title" label={labels.title} variant="filled" name="author"
                                       onChange={this.props.handleChange}/>
                        </Grid>
                        <Grid item xs={12}>
                            <TextField id="content" label={labels.content} variant="filled" name="content"
                                       multiline rows={4} onChange={this.props.handleChange}/>
                        </Grid>
                        <Grid item xs={6}/>
                        <Grid item xs={6}>
                            <Button type="submit">{labels.submit}</Button>
                        </Grid>
                    </Grid>
                </form>
            </Card>
        )
    }
}
