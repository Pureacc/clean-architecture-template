import React from 'react';

import {Link} from 'react-router-dom'
import {ListItem, ListItemIcon, ListItemText, ListSubheader} from '@material-ui/core'
import HomeIcon from '@material-ui/icons/Home';
import AddIcon from '@material-ui/icons/Add';
import GavelIcon from '@material-ui/icons/Gavel';
import AssignmentIcon from '@material-ui/icons/Assignment';
import List from "@material-ui/core/List";

export function MainListItems(props) {
    const {isAuthenticated} = props;

    return <List>
        <div>
            <ListItem button component={Link} to="/">
                <ListItemIcon>
                    <HomeIcon/>
                </ListItemIcon>
                <ListItemText primary="Home"/>
            </ListItem>
            {isAuthenticated && <ListItem button component={Link} to="/secured">
                <ListItemIcon>
                    <AddIcon/>
                </ListItemIcon>
                <ListItemText primary="Secured Page"/>
            </ListItem>}
            <ListItem button component={Link} to="/unsecured">
                <ListItemIcon>
                    <GavelIcon/>
                </ListItemIcon>
                <ListItemText primary="Unsecured Page"/>
            </ListItem>
        </div>
    </List>
}

export function SecondaryListItems(props) {
    return <div>
        <ListSubheader inset>Saved reports</ListSubheader>
        <ListItem button>
            <ListItemIcon>
                <AssignmentIcon/>
            </ListItemIcon>
            <ListItemText primary="Current month"/>
        </ListItem>
        <ListItem button>
            <ListItemIcon>
                <AssignmentIcon/>
            </ListItemIcon>
            <ListItemText primary="Last quarter"/>
        </ListItem>
        <ListItem button>
            <ListItemIcon>
                <AssignmentIcon/>
            </ListItemIcon>
            <ListItemText primary="Year-end sale"/>
        </ListItem>
    </div>
}