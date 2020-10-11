import React from 'react'
import {Dialog} from 'primereact/dialog';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';

function QRDialog(props) {
    return (
        <Dialog header="Etkinlik Kaydınız" visible={props.qrvisibility} style={{width: '50vw'}} 
                modal={true}  onHide = {props.hideQr} >
                    <Row style = {{width: '100%'}} >
            <Col sm={4} >
                <img src = {props.source} alt = "QR Code" />
            </Col>
            <Col sm={8} className="mt-4" >
                <b>Etkinlik ismi:</b> {props.event ? props.event.eventName : null}
                <br/>
                <b>Etkinlik yeri:</b> {props.event ? props.event.city : null}
                <br/>
                <b>Etkinlik Başlangıç Tarihi:</b> {props.event ? props.event.startDate : null}
                <br/>
                <b>Etkinlik Bitiş Tarihi:</b> {props.event ? props.event.endDate : null}
                <br/><br/>
                <b>Katılımcı TC Kimlik Numarası:</b> {localStorage.getItem("tcno")}
            </Col>
            </Row>
        </Dialog>
    )
}

export default QRDialog