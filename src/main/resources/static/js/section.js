import {Box, Grid} from "@material-ui/core";
import FormLabel from "@material-ui/core/FormLabel";
import React from "react";

export function Section(props) {
    return (
        <Grid item xs={12}>
            <Box mt={4} mb={2} mx={4}>
                <Grid container justify={'flex-start'} alignItems={'center'}>
                    <FormLabel component={'legend'}>{props.title}</FormLabel>
                </Grid>
            </Box>
        </Grid>
    );
}
