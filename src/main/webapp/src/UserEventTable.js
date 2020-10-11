import {DataTable} from 'primereact/datatable';
import React, { Component } from 'react';
import axios from 'axios';
import {Column} from 'primereact/column';
import Container from 'react-bootstrap/Container';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import QRDialog from './dialogs/QRDialog'
import QuestionsDialog from './dialogs/QuestionsDialog'
import {Growl} from 'primereact/growl';
import EventInfo from './EventInfo'
import { isAttendingEventNow } from './Utilities'
import AskingDialog from './dialogs/AskingDialog'
import QuestionsInfo from './components/QuestionsInfo'
import SurveyDialog from './dialogs/SurveyDialog'
import WeatherStatus from './components/WeatherStatus'

import { connect } from 'react-redux'
import * as actions from './actions'

class UserEventTable extends Component {
    constructor() {
        super();
        this.state = {
            events: [
            ],
            selected: null,
            attendDisability: true,
            tcNo: "",
            qrvisibility: false,
            qrsource: "",
            questionsVisibility: false,
            isQuestionEnabled: false,
            isSurveyEnabled: false,
            answer1: "",
            answer2: "",
            answer3: ""
        };
    }

    componentDidMount() {
        axios.get("/api/events/for/"+localStorage.getItem("tcno"), {
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
                    case 500:
                        this.showDialog("error","500: Sunucuya bağlanılamadı.")
                        break;
                    default:
                        this.showDialog("error",statusCode + ": Belirlenemeyen hata")
                }
            }
        })
    }

    attendEvent = (event) => {
        console.log("EVENT: " + event);
        let url = "/api/people/attendEvent"
        let data = {
            eventId: this.state.selected.id,
            personTc: localStorage.getItem("tcno"),
            answers: [
                this.state.answer1, this.state.answer2, this.state.answer3
            ]
        }
        let header = {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem("jwt")
            }
        }
        axios.post(url, data, header)
        .then(response => {
            let type = response.data.type;
            this.showDialog(type,response.data.message)
            if (type === "success" || type === "warn"){
                this.setState({
                    questionsVisibility: false,
                    answer1: "",
                    answer2: "",
                    answer3: "",
                    qrsource: "data:image/png;base64," + response.data.body,
                    qrvisibility: true
                })
            }  
        })
        .catch(error => {
            this.setState({
                questionsVisibility: false,
                answer1: "",
                answer2: "",
                answer3: ""
            })
            if (error.response){
                let statusCode = error.response.status;
                switch (statusCode){
                    case 500:
                        this.showDialog("error","Sunucuya bağlanılamadı.")
                        break;
                    default:
                        this.showDialog("error",statusCode + ": Belirlenemeyen bir hata oluştu.")
                }
            }
            
        })
    }

    attendClicked = (event) => {
        console.log(this.state.selected)
        if (this.state.selected.question){
            this.setState({
                questionsVisibility:true,
                questions: this.state.selected.question
            })
        }
        else {
            this.attendEvent();
        }
    }

    tableSelectionChange = (event) => {
        console.log(event.value);
        this.props.selectEvent(event.value)
        if (event.value === null){
            this.setState({
                selected: event.value,
                attendDisability: true
            })
        }
        else {
            let isQuestionEnabled = isAttendingEventNow(event);
            let isSurveyEnabled = isQuestionEnabled && !event.value.isCompletedSurvey
            this.setState({
                selected: event.value,
                attendDisability: false,
                isQuestionEnabled: isQuestionEnabled,
                isSurveyEnabled : isSurveyEnabled
            })
        }
    }
    showDialog = (type,message) => {
        this.growl.show({severity: type, detail: message});
    }
    hideQr = () => {
        this.setState({qrvisibility:false})
    }
    hideQuestions = () => {
        this.setState({questionsVisibility:false})
    }
    q1Change = (event) => {
        this.setState({answer1:event.target.value})
    }
    q2Change = (event) => {
        this.setState({answer2:event.target.value})
    }
    q3Change = (event) => {
        this.setState({answer3:event.target.value})
    }


    render() {
        return (
            <Container  fluid className = "mt-4 "  >
                <Growl ref={(el) => this.growl = el} />
                <Row>
                    <Col xs={6} className = "mx-4"  >
                    <Row >
                        <DataTable value={this.state.events} selectionMode="single"
                                    selection = {this.state.selected} onSelectionChange= {this.tableSelectionChange} >
                            <Column selectionMode="single" style={{width:'3em'}}/>
                            <Column field="eventName" header="Etkinlik Adı" filter filterMatchMode="contains" />
                            <Column field="city" header="Şehir" filter sortable />
                            <Column field="startDate" header="Başlangıç Tarihi" sortable filter />
                            <Column field="endDate" header="Bitiş Tarihi" sortable filter />
                            <Column field="personCount" header="Katılan Sayısı" sortable />
                            <Column field="capacity" header="Kontenjan" sortable />
                        </DataTable>
                    </Row>
                    <Row className="mt-3" >
                        <Button className="mr-2" onClick = {this.attendClicked} 
                                disabled= {this.state.attendDisability}>Etkinliğe katıl</Button>
                        <AskingDialog disabled={!this.state.isQuestionEnabled} />
                        <SurveyDialog disabled={!this.state.isSurveyEnabled} />
                    </Row>
                    </Col>
                    <Col>
                        <EventInfo  />
                        <WeatherStatus />
                        <QuestionsInfo />
                    </Col>
                </Row>
                
                <QRDialog source={this.state.qrsource} qrvisibility={this.state.qrvisibility}
                            hideQr={this.hideQr} event={this.state.selected} />
                <QuestionsDialog questionsVisibility={this.state.questionsVisibility} hideQuestions={this.hideQuestions}
                                eventItem={this.state.selected} onSubmit={this.attendEvent} q1={this.state.answer1}
                                q2={this.state.answer2} q3={this.state.answer3} q1change={this.q1Change} 
                                q2change={this.q2Change} q3change={this.q3Change}  />
                
            </Container>
        )
    }
}

export default connect(null,actions)(UserEventTable);