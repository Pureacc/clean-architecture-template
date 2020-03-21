import {AUTHENTICATE_REJECTED, GET_USER_REJECTED, LOG_OUT_FULFILLED, LOG_OUT_REJECTED} from "../actions/user";

export default function message(state = {}, action) {
    switch (action.type) {
        case LOG_OUT_FULFILLED:
            return setMessage("You are now logged out.", "success");
        case AUTHENTICATE_REJECTED:
        case GET_USER_REJECTED:
        case LOG_OUT_REJECTED:
            return setMessage(action.payload.response.data, "error");
        default:
            return state;
    }
}

function setMessage(message, variant) {
    return {
        id: Math.random(),
        message: message,
        variant: variant
    };
}