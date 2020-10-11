import React, { Component } from 'react';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Card from "react-bootstrap/Card";
import Map from "./Map"
import { connect } from 'react-redux'

class EventInfo extends Component {
    render() {
        return (
            
            <Card className ="pl-4 py-3 " >
            {
                (this.props.event) ?
                
                    <Row >
                        <Col sm={3}>
                        Etkinlik Konumu
                        </Col>
                        <Col sm={8}>
                        {<Map onClick={() => console.log(this.props.event)} lat={this.props.event.latitude} lng={this.props.event.longitude}
                                zoomLevel = {14} 
            center={{lat:parseFloat(this.props.event.latitude),lng:parseFloat(this.props.event.longitude)}} />}
                        </Col>
                    </Row>
                
                :
               <Row className ="mx-3" >
                   Bir etkinlik seçiniz.
               </Row>
            }
            </Card>
        )
    }
}

function mapStateToProps(state){
    return { 
        event: state.events.selectedEvent 
    }
}

export default connect(mapStateToProps)(EventInfo);
