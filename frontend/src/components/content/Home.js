import React from "react";
import {Typography} from "@material-ui/core";
import TextField from "@material-ui/core/TextField";
import Grid from "@material-ui/core/Grid";
import {connect} from "react-redux";

class Home extends React.Component {
    render() {
        return <Typography variant="h4" gutterBottom component="h2">
            Home
            <Grid item xs container direction="column">
                <TextField
                    id="username"
                    label="Username"
                    value={this.props.username}
                    margin="normal"
                    InputProps={{
                        readOnly: true,
                    }}
                />
            </Grid>
        </Typography>
    }
}

const mapStateToProps = state => {
    const {user} = state;
    return {
        username: typeof user.name !== "undefined" ? user.name : ""
    }
};

export default connect(mapStateToProps)(Home)