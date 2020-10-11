import React from 'react';
import {Growl} from 'primereact/growl';
//import SockJsClient from 'react-stomp';
var Stomp = require('stompjs');

class SampleComp extends React.Component {
    incomingMessageHandler = (mess) => {
        this.growl.show({sticky: true, severity: 'success', summary: 'Yeni katılımcı', detail: mess.body});
    }

    componentDidMount() {
        var socket = new WebSocket("ws://localhost:8080/greeting/websocket");
        var client = Stomp.over(socket);

        client.connect({}, (frame) => {
        client.subscribe('/queue/reply',(message) => this.incomingMessageHandler(message))})
    }

  
    render() {
        return (
            <div>
            <Growl ref={(el) => this.growl = el} />
            </div>
        );
    }
}

export default SampleComp;