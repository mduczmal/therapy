import Button from "@material-ui/core/Button";

const React = require('react');

export class LoginButton extends React.Component {
    constructor(props) {
        super(props);
    }

    render() {
        return (
            <Button color="inherit" href={"http://localhost:8080/login"}>Login</Button>
        )
    }
}