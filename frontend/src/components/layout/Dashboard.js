import React from 'react';
import {BrowserRouter, Route, Switch} from 'react-router-dom'
import {CssBaseline, withStyles} from '@material-ui/core'
import Unsecured from "../content/Unsecured";
import Home from "../content/Home";
import Register from "../content/Register";
import SignIn from "../content/SignIn";
import Secured from "../content/Secured";
import TopMenu from "../menu/top/TopMenu";
import LeftMenu from "../menu/left/LeftMenu";
import {SecuredRoute} from "./SecuredRoute";
import {compose} from "recompose";
import {connect} from "react-redux";
import {Landing} from "../content/Landing";
import Message from "../Message";

class Dashboard extends React.Component {
    constructor(props) {
        super(props);
        this.state = {
            open: true
        }
    }

    handleDrawerOpen = () => {
        this.setState({open: true});
    };

    handleDrawerClose = () => {
        this.setState({open: false});
    };

    render() {
        const {classes, authenticated} = this.props;

        return (
            <div className={classes.root}>
                <CssBaseline/>

                <BrowserRouter>
                    <TopMenu open={this.state.open}
                             authenticated={authenticated}
                             onDrawerOpen={this.handleDrawerOpen}/>
                    <LeftMenu open={this.state.open}
                              authenticated={authenticated}
                              onDrawerClose={this.handleDrawerClose}/>

                    <main className={classes.content}>
                        <div className={classes.appBarSpacer}/>
                        <Switch>
                            {authenticated && <Route exact path="/" component={Home}/>}
                            {!authenticated && <Route exact path="/" component={Landing}/>}
                            <Route path="/register" component={Register}/>
                            <Route path="/signin"
                                   render={(props) => <SignIn {...props} />}/>
                            <SecuredRoute path="/secured" component={Secured}
                                          isAuthenticated={authenticated}/>
                            <Route path="/unsecured" component={Unsecured}/>
                        </Switch>
                        <Message/>
                    </main>

                </BrowserRouter>
            </div>
        );
    }
}

const styles = theme => ({
    root: {
        display: 'flex',
    },
    appBarSpacer: theme.mixins.toolbar,
    content: {
        flexGrow: 1,
        padding: theme.spacing(3),
        height: '100vh',
        overflow: 'auto',
    },
});

const mapStateToProps = (state) => {
    return {
        authenticated: !!state.user.authenticated,
    };
};

export default compose(
    withStyles(styles),
    connect(mapStateToProps),
)(Dashboard);