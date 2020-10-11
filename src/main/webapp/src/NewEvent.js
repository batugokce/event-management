import React, { Component } from 'react'
import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Form from "react-bootstrap/Form";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import { Calendar } from 'primereact/calendar';
import axios from 'axios';
import { convertStrToDate } from './Utilities'
import ExtraInputs from './ExtraInputs';
import Map from './Map'
import SampleComp from "./SampleComp"
import {Growl} from 'primereact/growl';

class NewEvent extends Component {
    constructor(props){
        super();
        this.state = {
            date1: "",
            date2: "",
            eventname: "",
            city: "",
            capacity: "",
            extra1: "",
            extra2: "",
            extra3: "",
            secondCalendarDisability: true,
            validated: false,
            coordLat: 0.0,
            coordLng: 0.0
        }

        let today = new Date();
        this.minDate = new Date();
        this.minDate.setMonth(today.getMonth());
        this.minDate.setFullYear(today.getFullYear());

        this.minDate2 = new Date();
        this.maxDate = new Date(2147483647000);
    }
    

    createEvent = (e) => {
        axios.post("/api/admin/events/create",{
            eventName : this.state.eventname,
            city : this.state.city,
            capacity : this.state.capacity,
            startDate : convertStrToDate(this.state.date1),
            endDate : convertStrToDate(this.state.date2),
            personCount: 0,
            latitude: this.state.coordLat,
            longitude: this.state.coordLng,
            questions: [
                this.state.extra1,
                this.state.extra2,
                this.state.extra3,
            ]}, {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem("jwt")
                }
            })
        .then((response) => {
            console.log(response);
            this.setState({
                eventname: "",
                city: "",
                capacity : "",
                date1 : "",
                date2: "",
                minDate2: new Date(),
                maxDate: new Date(2147483647000)
            })
            this.showDialog(response.data.type, response.data.message)
        })   
    }

    calendarChange = (e) => {
        console.log(e.value);
        this.setState({
            date1: e.value,
            secondCalendarDisability: false
        })
        this.minDate2 = new Date(e.value);
    }

    calendarChange2 = (e) => {
        this.setState({
            date2: e.value,
        })
        this.maxDate = new Date(e.value);
    }


    handleSubmit = (event) => {
        event.preventDefault();
        event.stopPropagation();
        if (!this.state.eventname.length || !this.state.city.length || !this.state.capacity) {
            this.showDialog("error","Formdaki alanlar boş bırakılamaz.");
        }
        else if (this.state.date2 === "" || this.state.date1 === ""){
            this.showDialog("error","Tarih seçilmesi gerekiyor.")
        }
        else {
            this.createEvent();
        }

        this.setState({
            validated: true
        })
    };

    mapOnClick = event => {
        console.log(event);
        this.setState({
            coordLat: event.lat,
            coordLng: event.lng
        })
    }
    showDialog = (type,message) => {
        this.growl.show({severity: type, detail: message});
    }
    onChangeExtra1 = event => {
        this.setState({extra1:event.target.value})
    }
    resetExt1 = () => {this.setState({extra1:""})}

    onChangeExtra2 = event => {
        this.setState({extra2:event.target.value})
    }
    resetExt2 = () => {this.setState({extra2:""})}
    
    onChangeExtra3 = event => {
        this.setState({extra3:event.target.value})
    }
    resetExt3 = () => {this.setState({extra3:""})}

    render() {
        return (
            
            <Container  >
                <Growl ref={(el) => this.growl = el} />
                <Card className ="mt-5 pl-4 py-3 " >
                <Form noValidate validated={this.state.validated} onSubmit={this.handleSubmit} >
                    <Form.Group as={Row} controlId="formHorizontalName">
                        <Form.Label column sm={3}>
                        Etkinlik İsmi
                        </Form.Label>
                        <Col sm={8}>
                        <Form.Control value = {this.state.eventname} required onChange={event => {this.setState({eventname: event.target.value})}} 
                                     type="text" placeholder="Etkinlik ismini giriniz"  />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="formHorizontalPlace">
                        <Form.Label column sm={3}>
                        Şehir
                        </Form.Label>
                        <Col sm={8}>
                        <Form.Control value = {this.state.city} onChange={event => {this.setState({city: event.target.value})}} 
                                    type="text" placeholder="Etkinliğin gerçekleşeceği şehri giriniz" required />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="formHorizontalCapacity">
                        <Form.Label column sm={3}>
                        Kontenjan
                        </Form.Label>
                        <Col sm={8}>
                        <Form.Control value = {this.state.capacity} onChange={event => {this.setState({capacity: event.target.value})}} 
                                    type="number" min="0" placeholder="Etkinliğin kontenjanını belirleyiniz" required />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="formHorizontalStartDate">
                        <Form.Label column sm={3}>
                        Etkinlik Başlangıç Tarihi
                        </Form.Label>
                        <Col sm={8}>
                        <Calendar value={this.state.date1} readOnlyInput minDate = {this.minDate} dateFormat="dd/mm/yy" 
                                 onChange={this.calendarChange} showTime={true} hourFormat="24" 
                                 maxDate = {this.maxDate} />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="formHorizontalEndDate">
                        <Form.Label column sm={3}>
                        Etkinlik Bitiş Tarihi
                        </Form.Label>
                        <Col sm={8}>
                        <Calendar value={this.state.date2} readOnlyInput minDate = {this.minDate2} dateFormat="dd/mm/yy"  
                                 onChange={this.calendarChange2} showTime={true} hourFormat="24" 
                                 disabled = {this.state.secondCalendarDisability} />
                        </Col>
                    </Form.Group>

                    

                    <ExtraInputs extra1={this.state.extra1} onChange1 ={this.onChangeExtra1} resetExt1={this.resetExt1}
                                 extra2={this.state.extra2} onChange2 ={this.onChangeExtra2} resetExt2={this.resetExt2}
                                 extra3={this.state.extra3} onChange3 ={this.onChangeExtra3} resetExt3={this.resetExt3} />

                    <Form.Group as={Row} controlId="formHorizontalEndDate">
                        <Col sm={3}>Etkinlik Yeri</Col>
                        <Col  sm={8}  >
                        <Map onClick={this.mapOnClick} lat={this.state.coordLat} lng={this.state.coordLng}
                                zoomLevel = {7} />
                        </Col>
                    </Form.Group> 

                    <Form.Group as={Row} className = "mt-3" >
                        <Col sm={{ span: 8, offset: 3 }}>
                        <Button onClick = {this.handleSubmit} type="Submit" style={{"width":"150px"}} >Oluştur</Button>
                        </Col>
                    </Form.Group>
                    <SampleComp/>
                    </Form>
                    </Card>
            </Container>
        )
    }
}

export default NewEvent;
