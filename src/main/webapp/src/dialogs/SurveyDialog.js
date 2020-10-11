import React from 'react'
import Button from 'react-bootstrap/Button';
import {Dialog} from 'primereact/dialog';
import axios from 'axios'
import * as Survey from 'survey-react';
import 'survey-react/survey.css';
import { castValuesToIntegerOfJSONObject } from '../Utilities'
import { connect } from 'react-redux'

class SurveyDialog extends React.Component {
    constructor(){
        super();
        this.state = {
            visibility: false,
            surveyJSON: {"locale":"tr","pages":[{"name":"page1","elements":[{"type":"rating","name":"question1","title":{"tr":"Etkinlikten memnun kaldınız mı?"},"isRequired":true,"requiredErrorText":"Sorular boş bırakılamaz.","rateMax":10,"minRateDescription":"Çok kötüydü","maxRateDescription":"Çok iyiydi"},{"type":"rating","name":"question2","title":"Etkinlik içeriği sizce yeterli miydi?","isRequired":true,"requiredErrorText":"Sorular boş bırakılamaz.","rateMax":10,"minRateDescription":"Çok yetersizdi","maxRateDescription":"Fazlasıyla yeterliydi"},{"type":"rating","name":"question3","title":"Etkinliğin konumu sizce iyi miydi?","isRequired":true,"requiredErrorText":"Sorular boş bırakılamaz.","rateMax":10,"minRateDescription":"Çok kötüydü","maxRateDescription":"Çok iyiydi"},{"type":"rating","name":"question4","title":"Etkinlik organizasyonunu nasıl buldunuz?","isRequired":true,"requiredErrorText":"Sorular boş bırakılamaz.","rateMax":10,"minRateDescription":"Organizasyon çok kötüydü","maxRateDescription":"Çok iyi organize edilmiş"},{"type":"rating","name":"question5","title":"Etkinliğe kaydolurken problem yaşadınız mı?","isRequired":true,"requiredErrorText":"Sorular boş bırakılamaz.","rateMax":10,"minRateDescription":"Çok problemliydi","maxRateDescription":"Hiç problem yaşamadım"},{"type":"rating","name":"question6","title":"Aynı etkinlik tekrar gerçekleşirse katılır mısınız?","isRequired":true,"requiredErrorText":"Sorular boş bırakılamaz.","rateMax":10,"minRateDescription":"Asla katılmam","maxRateDescription":"Kesinlikle katılırım"},{"type":"rating","name":"question7","title":"Bu etkinliği bir arkadaşına önerir misiniz?","isRequired":true,"requiredErrorText":"Sorular boş bırakılamaz.","rateMax":10,"minRateDescription":"Asla önermem","maxRateDescription":"Kesinlikle öneririm"}],"title":"Etkinlik Geri Bildirim Anketi"}]}
        }
    }

    completeSurvey = (survey) => {
        let results = castValuesToIntegerOfJSONObject(survey.data)
        console.log(results)
        axios.post("/api/completeSurvey/" + this.props.event.id + '/' + localStorage.getItem("tcno"),results,{
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem("jwt")
            }
        })
        .then(response => {
            console.log(response)
        })
    }

    closeDialog = () => {
        this.setState({
            visibility:false
        })
    }
    
    render() {
        return (
            <div>
                <Button onClick={() => this.setState({visibility:true})} disabled={this.props.disabled}  >Ankete katıl</Button>
                <Dialog visible={this.state.visibility} header={"Etkinlik Değerlendirme Anketi"}
                        onHide={this.closeDialog} >
                <Survey.Survey json = {this.state.surveyJSON} onComplete={ this.completeSurvey }  />
                </Dialog>
            </div>
        )
    }
}

function mapStateToProps(state){
    return { 
        event: state.events.selectedEvent 
    }
}

export default connect(mapStateToProps)(SurveyDialog);