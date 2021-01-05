import React, {useEffect} from "react";
import Button from "@material-ui/core/Button";
import {getCookie} from "./hello";

export function LoginButton(props) {
    const [state, setState] = React.useState({loaded: false, isLogged: false});

    const isLogged = () => {
        const token = getCookie('XSRF-TOKEN');
        fetch("/v2/logged",
            {
                method: 'GET',
                headers: {
                    'X-XSRF-TOKEN': token
                }
            }).then(res => res.json())
            .then(
                (result) => {
                    setState({
                        isLogged: result.logged,
                        loaded: true
                    });
                },
                (error) => {
                    console.log(error);
                });
    }
    useEffect(isLogged, [])

    return (<React.Fragment>{!state.loaded ? null : (
        <React.Fragment>{state.isLogged ?
            (<Button color="inherit" href={"/logout"}>{props.labels.logout}</Button>)
            : (<Button color="inherit" href={"/login"}>{props.labels.login}</Button>)
        }
        </React.Fragment>
    )}</React.Fragment>)
}