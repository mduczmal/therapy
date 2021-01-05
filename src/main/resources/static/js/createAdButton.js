import React from "react";
import Button from "@material-ui/core/Button";

export function CreateAdButton(props) {
    return (
        <Button color="inherit" href={"/v2/create"}>{props.labels.createAd}</Button>
    )
}
