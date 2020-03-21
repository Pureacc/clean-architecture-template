import React, {Component} from 'react';
import {connect} from "react-redux";
import Toast from "./Toast";

class Message extends Component {
    render() {
        return (
            <Toast {...this.props}/>
        );
    }
}

const mapStateToProps = (state) => {
    const {message} = state;
    return {
        id: message.id,
        message: message.message,
        variant: message.variant
    }
};

export default connect(mapStateToProps)(Message);