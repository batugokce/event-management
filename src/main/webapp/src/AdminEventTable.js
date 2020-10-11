import {DataTable} from 'primereact/datatable';
import React, { Component } from 'react';
import axios from 'axios';
import {Column} from 'primereact/column';
import {Growl} from 'primereact/growl';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import EditEventDialog from './dialogs/EditEventDialog'
import { convertStrToDate } from './Utilities'
import FirstChart from './charts/FirstChart';
import SecondChart from './charts/SecondChart';
import SampleComp from "./SampleComp"
import DrawDialog from "./dialogs/DrawDialog";
import AnswerQuestionsDialog from "./dialogs/AnswerQuestionsDialog"
import SurveyResultsChart from "./charts/SurveyResultsChart"
import SendNotification from "./components/SendNotification"
import { connect } from 'react-redux'
import * as actions from './actions'

class AdminEventTable extends Component {

    constructor() {
        super();
        this.state = {
            events: [
            ],
            participants: [
            ],
            selected: null,
            editVisibility: false,
            eventname: "",
            city: "",
            capacity : "",
            date1: "",
            date2: "",
            validated: false,
            isEventSelected: false
        };

        let today = new Date();
        this.minDate = new Date();
        this.minDate.setMonth(today.getMonth());
        this.minDate.setFullYear(today.getFullYear());

        this.minDate2 = new Date();
        this.maxDate = new Date(2147483647000);
    }

    tableSelectionChange = (event) => {
        this.props.selectEvent(event.value)
        if (event.value == null){
            this.setState({isEventSelected:false, selected: event.value })
        }
        else {
            this.setState({
                participants: event.value.participants,
                isEventSelected: true,
                selected: event.value
            })
        }
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
        const form = event.currentTarget;
        event.preventDefault();
        event.stopPropagation();

        if (form.checkValidity() === false) {
            this.showDialog("error","Form boş bırakılamaz.")
            console.log("invaliiid");
        }
        else if (this.state.date2 === "" || this.state.date1 === ""){
            this.showDialog("error","Tarihler boş bırakılamaz.")
            console.log("invalid date");
            console.log("date1: " + this.state.date1);
            console.log("date2: " + this.state.date2);
        }
        else {
            console.log("okeey");
            this.editEventHandler(event);
        }

        this.setState({
            validated: true
        })
    };

    componentDidMount() {
        axios.get("/api/admin/events/", {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem("jwt")
            }
        })
        .then(response => {
            let tmpData = response.data.body;
            console.log(tmpData);
            for (var i = 0; i<tmpData.length;i++){
                tmpData[i].startDate = tmpData[i].startDate.slice(0,-3).replace('T',' ');
                tmpData[i].endDate = tmpData[i].endDate.slice(0,-3).replace('T',' ');
            }
            this.props.storeEvents(tmpData)
            this.setState({
                events: tmpData
            })
        })
        .catch(error => {
            if (error.response){
                let statusCode = error.response.status;
                switch (statusCode){
                    case 404:
                        this.showDialog("error","404: Kayıt bulunamadı.")
                        break;
                    default:
                        this.showDialog("error",statusCode + ": Belirlenemeyen hata")
                }
            }
            
        })

    }

    deleteEvent = () => {
        if (this.state.selected){
            let url = "/api/admin/events/" + this.state.selected.id;
            axios.delete(url, {
                headers: {
                    Authorization: 'Bearer ' + localStorage.getItem("jwt")
                }
            })
            .then(response => {
                let tmpData = response.data.body;
                this.showDialog(response.data.type,response.data.message)
                for (var i = 0; i<tmpData.length;i++){
                    tmpData[i].startDate = tmpData[i].startDate.slice(0,-3).replace('T',' ');
                    tmpData[i].endDate = tmpData[i].endDate.slice(0,-3).replace('T',' ');
                }
                this.setState({
                    events: tmpData,
                    participants: []
                })
            })
            .catch(error => {
                if (error.response){
                    let statusCode = error.response.status;
                    switch (statusCode){
                        case 404:
                            this.showDialog("error","404: Kayıt bulunamadı.")
                            break;
                        default:
                            this.showDialog("error",statusCode + ": Hata")
                    }
                }
                
            })
        }
    }

    editEventHandler = (event) => {
        let url = "/api/admin/events/" + this.state.selected.id;
        axios.put(url,{
            eventName : this.state.eventname,
            city : this.state.city,
            capacity : this.state.capacity,
            startDate : convertStrToDate(this.state.date1),
            endDate : convertStrToDate(this.state.date2),
        }, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem("jwt")
            }
        })
        .then(response => {
            console.log(response.data)
            
            this.showDialog(response.data.type,response.data.message)
            if (response.data.type === "success"){
                let tmpData = response.data.body;
                for (var i = 0; i<tmpData.length;i++){
                    tmpData[i].startDate = tmpData[i].startDate.slice(0,-3).replace('T',' ');
                    tmpData[i].endDate = tmpData[i].endDate.slice(0,-3).replace('T',' ');
                }
                this.setState({
                    events: tmpData,
                    editVisibility: false
                })
            }  
        })
    }

    showDialog = (type,message) => {
        this.growl.show({severity: type, detail: message});
    }
    editOnClick = () => { if (this.state.selected) {this.setState({editVisibility: true})} }
    editOnHide = () => {this.setState({editVisibility: false})}
    editHandleEventName = (event) => {this.setState({eventname: event.target.value})}
    editHandleCity = (event) => {this.setState({city: event.target.value})}
    editHandleCapacity = (event) => {this.setState({capacity: event.target.value})}
    
    render() {
        return (
            <Container fluid className = "mt-4"  >
                <Growl ref={(el) => this.growl = el} />
                <SampleComp />
                <Row>
                    <Col xs={6} className = "mx-4"  >
                        <Row>
                            <DataTable value={this.state.events} selectionMode="single"
                            selection = {this.state.selected} onSelectionChange= {this.tableSelectionChange} >
                                <Column selectionMode="single" style={{width:'3em'}}/>
                                <Column field="eventName" header="Etkinlik Adı" sortable filter filterMatchMode="contains" />
                                <Column field="startDate" header="Başlangıç Tarihi" sortable filter />
                                <Column field="endDate" header="Bitiş Tarihi" sortable filter />
                                <Column field="personCount" header="Katılan Sayısı" sortable />
                                <Column field="capacity" header="Kontenjan" sortable />
                            </DataTable>
                        </Row>
                        <Row className="mt-3" >
                            <Button className="mr-2" onClick = {this.deleteEvent} disabled={!this.state.isEventSelected} >
                                Etkinlik sil</Button>
                            <Button className="mx-2" onClick = {this.editOnClick} disabled={!this.state.isEventSelected} >
                                Etkinlik düzenle</Button>
                            <DrawDialog disabled={!this.state.isEventSelected} />
                            <AnswerQuestionsDialog   disabled={!this.state.isEventSelected} />
                            <SendNotification showGrowl={this.showDialog} disabled={!this.state.isEventSelected} />
                        </Row>
                        <Row className="mt-3">
                            <FirstChart  />
                            <SecondChart disabled={!this.state.isEventSelected} />
                            <SurveyResultsChart disabled={!this.state.isEventSelected} />
                        </Row>
                    </Col>
                    <Col>
                        <Row className = "justify-content-md-center" style={{"color":"#343a40"}} ><h3>Katılımcı Bilgileri</h3></Row>
                        <Row className = "mx-2 mt-2" >
                            <DataTable value={this.state.participants} rowHover>
                                <Column field="tcNo" header="TC No" sortable filter />
                                <Column field="nameSurname" header="Adı Soyadı" sortable filter filterMatchMode="contains" />
                            </DataTable>
                        </Row>
                    </Col>
                </Row>
                
                <EditEventDialog validated ={this.state.validated} handleSubmit = {this.handleSubmit} editVisibility = {this.state.editVisibility} 
                                eventname = {this.state.eventname} editEventname = {this.editHandleEventName} city = {this.state.city}
                                editCity = {this.editHandleCity} capacity = {this.state.capacity}editCapacity = {this.editHandleCapacity} date1 = {this.state.date1}
                                minDate = {this.minDate} calendarChange = {this.calendarChange}maxDate = {this.maxDate} date2 = {this.state.date2}
                                minDate2 = {this.minDate2} calendarChange2 = {this.calendarChange2}secondCalendarDisability = {this.state.secondCalendarDisability}
                                editOnHide = {this.editOnHide}  />
                
                
            </Container>
            
        );
    }
}

export default connect(null,actions)(AdminEventTable);