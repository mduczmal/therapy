import Button from "@material-ui/core/Button";
import {getCookie} from "./hello";

const React = require('react');

export class LoginButton extends React.Component {
    constructor(props) {
        super(props);
        this.handleClick = this.handleClick.bind(this);
    }

    handleClick() {
        console.log("I am clicked");
        const token = getCookie('XSRF-TOKEN');
        fetch("http://localhost:8080/data",
            {
                method: 'POST',
                headers: {
                    'Content-Type': 'application/json',
                    'X-XSRF-TOKEN': token
                },
            })
            .then(res => res.json())
            .then(
                (result) => {
                    this.setState({
                        isLoaded: true,
                    });
                    console.log("I am loaded");
                    console.log(result);
                },
                (error) => {
                    this.setState({
                        isLoaded: true,
                        error
                    });
                }
            )
    }

    render() {
        return (
            <Button color="inherit" onClick={this.handleClick}>Login</Button>
        )
    }
}