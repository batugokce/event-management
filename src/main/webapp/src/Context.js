import React, { Component } from 'react'

const UserContext = React.createContext();

const reducer = (state, action) => {
    switch (action.type) {
        case "COOKIE":
            return {
                ...state,
                foo: action.payload
            }
        case "USER_LOGIN":
            return {
                ...state,
                userObject: action.payload
            }
        default:
            return state
    } 
}

export class UserProvider extends Component {
    state = {
        foo: "abc",
        userObject: "",
        cookiee: "",
        dispatch: action => {
            this.setState(state => reducer(state,action))
        }
    }

    render() {
        return (
            <UserContext.Provider value = {this.state} >
                {this.props.children}
            </UserContext.Provider>
        )
    }
}

const UserConsumer = UserContext.Consumer;

export default UserConsumer;



