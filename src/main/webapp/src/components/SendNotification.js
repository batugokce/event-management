import React, { Component } from 'react';
import axios from 'axios';
import Button from 'react-bootstrap/Button';
import { connect } from 'react-redux'

class SendNotification extends Component {
    constructor() {
        super();

        this.state = {
            data: [],
            visibility: false
        }
    }

    sendNotification = () => {
        axios.post("/api/admin/sendNotification/" + this.props.event.id, {}, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem("jwt")
            }
        })
        .then(response => {
            console.log(response.data)
            this.props.showGrowl(response.data.type,response.data.message)
        })
    }


    render() {
        return ( 
            <div>
                <Button className="mx-2" onClick={this.sendNotification} disabled={this.props.disabled}  >Hatırlatıcı Gönder</Button>
            </div>
        )
    }
}

function mapStateToProps(state){
    return { 
        event: state.events.selectedEvent 
    }
}

export default connect(mapStateToProps)(SendNotification);