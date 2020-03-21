import * as React from "react";
import {Link} from "react-router-dom";
import Button from "@material-ui/core/Button";
import ButtonGroup from "@material-ui/core/ButtonGroup";

class AuthenticateMenu extends React.Component {
    render() {
        return (
            <ButtonGroup
                variant="contained"
                color="secondary"
                aria-label="full-width contained primary button group"
            >
                <Button component={Link} to="/signin">Sign In</Button>
                <Button component={Link} to="/register">Register</Button>
            </ButtonGroup>
        );
    }
}

export default AuthenticateMenu