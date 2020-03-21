import * as api from "../api";

import {ActionType} from 'redux-promise-middleware';

export const RESTORE_SESSION = "RESTORE_SESSION";
const AUTHENTICATE = "AUTHENTICATE";
export const AUTHENTICATE_PENDING = `${AUTHENTICATE}_${ActionType.Pending}`;
export const AUTHENTICATE_FULFILLED = `${AUTHENTICATE}_${ActionType.Fulfilled}`;
export const AUTHENTICATE_REJECTED = `${AUTHENTICATE}_${ActionType.Rejected}`;
const GET_USER = "GET_USER";
export const GET_USER_PENDING = `${GET_USER}_${ActionType.Pending}`;
export const GET_USER_FULFILLED = `${GET_USER}_${ActionType.Fulfilled}`;
export const GET_USER_REJECTED = `${GET_USER}_${ActionType.Rejected}`;
const LOG_OUT = "LOG_OUT";
export const LOG_OUT_PENDING = `${LOG_OUT}_${ActionType.Pending}`;
export const LOG_OUT_FULFILLED = `${LOG_OUT}_${ActionType.Fulfilled}`;
export const LOG_OUT_REJECTED = `${LOG_OUT}_${ActionType.Rejected}`;

export function restoreSession(userId) {
    return (dispatch) => {
        dispatch(doRestoreSession(userId));
        dispatch(getUser(userId));
    }
}

export function authenticate(username, password) {
    return {
        type: AUTHENTICATE,
        payload: api.authenticate(username, password)
    }
}

export function getUser(userId) {
    return {
        type: GET_USER,
        payload: api.getUser(userId)
    }
}

export function logOut() {
    return {
        type: LOG_OUT,
        payload: api.logOut()
    }
}

function doRestoreSession(userId) {
    return {
        type: RESTORE_SESSION,
        userId: userId
    }
}