import { SELECT_EVENT, STORE_EVENTS } from '../actions/types'

const intialState = { allEvents: null, selectedEvent: null }

export default function(state = intialState, action) {
    switch (action.type) {
        case STORE_EVENTS:
            return {
                allEvents: action.payload,
                selectedEvent: state.selectedEvent
            };
        case SELECT_EVENT:
            return {
                allEvents: state.allEvents,
                selectedEvent: action.payload
            };
        default:
            return state;
    }
}