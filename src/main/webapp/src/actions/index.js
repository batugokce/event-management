import { SELECT_EVENT, STORE_EVENTS } from './types'

export function selectEvent(event) {
    return {
        type: SELECT_EVENT,
        payload: event
    };
}

export function storeEvents(eventList) {
    return {
        type: STORE_EVENTS,
        payload: eventList
    };
}