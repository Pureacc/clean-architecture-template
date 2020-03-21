import * as React from "react";
import classNames from "classnames";
import {AppBar, IconButton, Toolbar, Typography, withStyles} from "@material-ui/core";
import MenuIcon from '@material-ui/icons/Menu';
import ProfileMenu from "./ProfileMenu";
import AuthenticateMenu from "./AuthenticateMenu";

class TopMenu extends React.Component {
    render() {
        const {authenticated, classes} = this.props;

        let rightMenu;
        if (authenticated) {
            rightMenu = <ProfileMenu {...this.props}/>;
        } else {
            rightMenu = <AuthenticateMenu/>;
        }

        return (
            <AppBar
                position="absolute"
                className={classNames(classes.appBar, this.props.open && classes.appBarShift)}
            >
                <Toolbar disableGutters={!this.props.open} className={classes.toolbar}>
                    <IconButton
                        color="inherit"
                        aria-label="Open drawer"
                        onClick={this.props.onDrawerOpen}
                        className={classNames(
                            classes.menuButton,
                            this.props.open && classes.menuButtonHidden,
                        )}
                    >
                        <MenuIcon/>
                    </IconButton>
                    <Typography
                        component="h1"
                        variant="h6"
                        color="inherit"
                        noWrap
                        className={classes.title}
                    >
                        My App
                    </Typography>
                    {rightMenu}
                </Toolbar>
            </AppBar>
        )
    }
}

const drawerWidth = 240;

const styles = theme => ({
    toolbar: {
        paddingRight: 24, // keep right padding when drawer closed
    },
    appBar: {
        zIndex: theme.zIndex.drawer + 1,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.leavingScreen,
        }),
    },
    appBarShift: {
        marginLeft: drawerWidth,
        width: `calc(100% - ${drawerWidth}px)`,
        transition: theme.transitions.create(['width', 'margin'], {
            easing: theme.transitions.easing.sharp,
            duration: theme.transitions.duration.enteringScreen,
        }),
    },
    menuButton: {
        marginLeft: 12,
        marginRight: 36,
    },
    menuButtonHidden: {
        display: 'none',
    },
    title: {
        flexGrow: 1,
    },
});

export default withStyles(styles)(TopMenu)