import React from "react";
import Button from "@material-ui/core/Button";

export function CreateAdButton(props) {
    return (
        <Button color="inherit" href={"http://localhost:8080/v2/create"}>{props.labels.createAd}</Button>
    )
}
