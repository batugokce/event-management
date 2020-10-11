import React, { Component } from 'react';
import {Dialog} from 'primereact/dialog';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Form from "react-bootstrap/Form";
import { Calendar } from 'primereact/calendar';

export default class EditEventDialog extends Component {
    render() {
        const footer = (
            <div><Button type="Submit" >Değişikliği Onayla</Button> </div>
        );

        return (
            <Form noValidate validated={this.props.validated} onSubmit={this.props.handleSubmit} >
                <Dialog header="Etkinlik Düzenle" footer={footer} visible={this.props.editVisibility} 
                        style={{width: '70%', height: '100%'}} maximizable modal={true} 
                        onHide={this.props.editOnHide}>
                            
                    <Form.Group as={Row} className ="my-3" style = {{width: '100%'}} controlId="formHorizontalName">
                        <Form.Label column sm={2}>
                        Etkinlik İsmi
                        </Form.Label>
                        <Col sm={6}>
                        <Form.Control value = {this.props.eventname} onChange={this.props.editEventname} 
                                     required type="text" placeholder="Etkinlik ismini giriniz" />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} className ="my-3" style = {{width: '100%'}} controlId="formHorizontalPlace">
                        <Form.Label column sm={2}>
                        Şehir
                        </Form.Label>
                        <Col sm={6}>
                        <Form.Control value = {this.props.city} onChange={this.props.editCity} 
                                    required type="text" placeholder="Etkinliğin gerçekleşeceği şehri giriniz" />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} className ="my-3" style = {{width: '100%'}} controlId="formHorizontalCapacity">
                        <Form.Label column sm={2}>
                        Kontenjan
                        </Form.Label>
                        <Col sm={6}>
                        <Form.Control value = {this.props.capacity} onChange={this.props.editCapacity}
                                    required type="number" min="0" placeholder="Etkinliğin kontenjanını belirleyiniz" />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} className ="mt-4 justify-content-md-center" style = {{width: '100%'}} controlId="formHorizontalStartDate">
                        <Col sm={4} className = "mx-5" >
                            <Row className = "justify-content-md-center" ><h5>Etkinlik Başlangıç Tarihi</h5></Row>
                            <Row className = "justify-content-md-center" >
                                <Calendar value={this.props.date1} inline readOnlyInput minDate = {this.props.minDate} dateFormat="dd/mm/yy" 
                                onChange={this.props.calendarChange} showTime={true} hourFormat="24"
                                maxDate = {this.props.maxDate} />
                            </Row>
                            
                        </Col>
                        <Col sm={4} className = "mx-5"  >
                            <Row className = "justify-content-md-center" ><h5>Etkinlik Bitiş Tarihi</h5></Row>
                            <Row className = "justify-content-md-center" >
                                <Calendar  value={this.props.date2} inline readOnlyInput minDate = {this.props.minDate2} dateFormat="dd/mm/yy"
                                    onChange={this.props.calendarChange2} showTime={true} hourFormat="24"
                                    disabled = {this.props.secondCalendarDisability} />
                            </Row>
                        </Col>
                    </Form.Group>
                </Dialog>
                </Form>
        )
    }
}
