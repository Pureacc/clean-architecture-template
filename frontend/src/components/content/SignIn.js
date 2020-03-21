import React from 'react';
import PropTypes from 'prop-types';
import Avatar from '@material-ui/core/Avatar';
import Button from '@material-ui/core/Button';
import CssBaseline from '@material-ui/core/CssBaseline';
import TextField from '@material-ui/core/TextField';
import Link from '@material-ui/core/Link';
import Grid from '@material-ui/core/Grid';
import Typography from '@material-ui/core/Typography';
import {withStyles} from '@material-ui/core/styles';
import Container from '@material-ui/core/Container';
import {Link as RouterLink} from "react-router-dom";
import {ExitToApp} from "@material-ui/icons";
import {connect} from "react-redux";
import {compose} from "recompose";
import {authenticate, getUser} from "../../actions/user";

class SignIn extends React.Component {
    constructor(props) {
        super(props);
        this.state = {username: "", password: ""};
    }

    componentDidUpdate(prevProps) {
        if (this.props.userId !== prevProps.userId) {
            this.props.actions.getUser(this.props.userId);
            this.props.history.push("/");
        }
    }

    handleChangeUsername = event => {
        this.setState({username: event.target.value});
    };

    handleChangePassword = event => {
        this.setState({password: event.target.value});
    };

    handleSubmit = event => {
        event.preventDefault();
        this.props.actions.authenticate(this.state.username, this.state.password);
    };

    render() {
        const {classes} = this.props;

        return (
            <Container component="main" maxWidth="xs">
                <CssBaseline/>
                <div className={classes.paper}>
                    <Avatar className={classes.avatar}>
                        <ExitToApp/>
                    </Avatar>
                    <Typography component="h1" variant="h5">
                        Sign In
                    </Typography>
                    <form className={classes.form} noValidate>
                        <Grid container spacing={2}>
                            <Grid item xs={12}>
                                <TextField
                                    id="userName"
                                    name="userName"
                                    label="Username"
                                    value={this.state.username}
                                    onChange={this.handleChangeUsername}
                                    autoComplete="uname"
                                    variant="outlined"
                                    required
                                    fullWidth
                                    autoFocus
                                />
                            </Grid>
                            <Grid item xs={12}>
                                <TextField
                                    id="password"
                                    name="password"
                                    label="Password"
                                    value={this.state.password}
                                    onChange={this.handleChangePassword}
                                    variant="outlined"
                                    required
                                    fullWidth
                                    type="password"
                                    autoComplete="current-password"
                                />
                            </Grid>
                        </Grid>
                        <Button
                            type="submit"
                            onClick={this.handleSubmit}
                            fullWidth
                            variant="contained"
                            color="primary"
                            className={classes.submit}
                        >
                            Sign In
                        </Button>
                        <Grid container justify="flex-end">
                            <Grid item>
                                <Link component={RouterLink} to="/register" variant="body2">
                                    Don't have an account yet? Register
                                </Link>
                            </Grid>
                        </Grid>
                    </form>
                </div>
            </Container>
        )
    }
}

SignIn.propTypes = {
    classes: PropTypes.object.isRequired
};

const styles = theme => ({
    '@global': {
        body: {
            backgroundColor: theme.palette.common.white,
        },
    },
    paper: {
        marginTop: theme.spacing(8),
        display: 'flex',
        flexDirection: 'column',
        alignItems: 'center',
    },
    avatar: {
        margin: theme.spacing(1),
        backgroundColor: theme.palette.secondary.main,
    },
    form: {
        width: '100%', // Fix IE 11 issue.
        marginTop: theme.spacing(3),
    },
    submit: {
        margin: theme.spacing(3, 0, 2),
    },
});

const mapStateToProps = state => {
    const {user} = state;
    return {
        userId: user.id
    }
};

const mapDispatchToProps = dispatch => {
    return {
        actions: {
            authenticate: (username, password) => dispatch(authenticate(username, password)),
            getUser: (userId) => dispatch(getUser(userId))
        }
    }
};

export default compose(
    withStyles(styles),
    connect(mapStateToProps, mapDispatchToProps),
)(SignIn);