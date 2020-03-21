import {
    AUTHENTICATE_FULFILLED,
    AUTHENTICATE_PENDING,
    GET_USER_FULFILLED,
    GET_USER_PENDING,
    LOG_OUT_FULFILLED,
    LOG_OUT_PENDING,
    RESTORE_SESSION
} from "../actions/user";
import {deleteSession, saveSession} from "../infra/session";

export default function user(state = {}, action) {
    switch (action.type) {
        case RESTORE_SESSION:
            return {
                ...state,
                authenticated: true,
                id: action.userId
            };
        case AUTHENTICATE_PENDING:
            return state;
        case AUTHENTICATE_FULFILLED:
            saveSession(action.payload.data);
            return {
                ...state,
                authenticated: true,
                id: action.payload.data.userId
            };
        case GET_USER_PENDING:
            return state;
        case GET_USER_FULFILLED:
            return {
                ...state,
                name: action.payload.data.username,
                balance: action.payload.data.balance
            };
        case LOG_OUT_PENDING:
            return state;
        case LOG_OUT_FULFILLED:
            deleteSession();
            return {
                authenticated: false
            };
        default:
            return state;
    }
}