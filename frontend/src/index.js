import React from 'react';
import ReactDOM from 'react-dom';
import App from './components/App';
import {store} from "./store/store";
import {Provider} from "react-redux";
import {restoreSession} from "./actions/user";
import {getSession} from "./infra/session";

const session = getSession();
if (session.active) {
    store.dispatch(restoreSession(session.userId))
}

ReactDOM.render(<Provider store={store}><App/></Provider>, document.getElementById('root'));
