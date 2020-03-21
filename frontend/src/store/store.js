import {applyMiddleware, createStore} from 'redux'
import rootReducer from "../reducers/root";
import {composeWithDevTools} from "redux-devtools-extension";
import promise from 'redux-promise-middleware'
import thunk from 'redux-thunk';
import ImmutableState from 'redux-immutable-state-invariant'

export const store = createStore(rootReducer, composeWithDevTools(
    applyMiddleware(thunk, promise, ImmutableState())
));