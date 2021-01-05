import React from "react";
import {TextField} from "@material-ui/core";

export function CreateTextField(props) {
    return(
        <TextField id={props.name} label={props.labels[props.name]} variant="filled" name={props.name}
                   defaultValue={props.details[props.name]} onChange={props.handleFieldChange}/>
    )
}