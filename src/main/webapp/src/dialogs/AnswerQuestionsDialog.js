import React from 'react'
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import {Dialog} from 'primereact/dialog';
import Col from 'react-bootstrap/Col';
import {InputTextarea} from 'primereact/inputtextarea';
import axios from 'axios'
import {Growl} from 'primereact/growl';
import {TabView,TabPanel} from 'primereact/tabview';
import Card from "react-bootstrap/Card";
import { CardContent } from '@material-ui/core';
import { connect } from 'react-redux'

class AnswerQuestionsDialog extends React.Component {
    constructor() {
        super();

        this.state = {
            visibility: false
        }
    }

    showDialog = () => {
        this.setState({
            visibility:true
        })
    }


    answeredQuestions = (item) => {
        return (
            <div key={item.id} >
            {
                (item.answer) ?

                <Card className="my-3" style={{"width":"130%"}}  >
                    <Card.Header>{item.question}</Card.Header>
                    <CardContent>{item.answer}</CardContent>
                </Card>
                :
                null
            }
            </div>
        )
    }
    
    sendAnswer = (id,e) => {
        let answer = document.getElementById(id).value;
        let header = {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem("jwt")
            }
        }
        axios.post("/api/answerQuestion/" + id, {
            answer: answer
        },header)
        .then(response => {
            let responseMessage = response.data
            this.setState({
                visibility:false
            })
            this.growl.show({severity: responseMessage.type, detail: responseMessage.message});
        })
    }


    unansweredQuestions = (item) => {
        return (
            <div key={item.id} >
            {
                (item.answer == null) ?

                <Card className="my-3" style={{"width":"130%"}}  >
                    <Card.Header>{item.question}</Card.Header>
                    <InputTextarea id={item.id} />
                    <Card.Footer><Button onClick={event => this.sendAnswer(item.id)} >Gönder</Button></Card.Footer>
                </Card>
                :
                null
            }
            </div>
        )
    }

    render(){
        return (
            <div>
                <Growl ref={(el) => this.growl = el} />
                <Button className="mx-2" onClick={this.showDialog} disabled={this.props.disabled}  >Soru cevapla</Button>
                <Dialog visible={this.state.visibility} style={{width: '50%'}}
                        onHide={()=>{this.setState({visibility:false})}}
                        header={"Sorular"} >
                {
                (this.props.event ) ?
                <TabView activeIndex={this.state.activeIndex} onTabChange={(e) => this.setState({activeIndex: e.index})}>
                    <TabPanel header="Cevaplanmış Sorular">
                        <div className ="pl-1 py-1 my-1 " style={{"width":"100%"}} >
                            <Row>
                                <Col sm={9}>
                                    {this.props.event.personQuestions.map(item => this.answeredQuestions(item))}
                                </Col>
                            </Row>
                        </div>
                    </TabPanel>
                    <TabPanel header="Cevaplanmamış Sorular ">
                        <div className ="pl-1 py-1 my-1 " style={{"width":"100%"}} >
                            <Row>
                                <Col sm={9}>
                                    {this.props.event.personQuestions.map(item => this.unansweredQuestions(item))}
                                </Col>
                            </Row>
                        </div>
                    </TabPanel>

                </TabView>
                :
                null
                
                }
                </Dialog>
                <Growl ref={(el) => this.growl = el} />
            </div>
        )
    }
    
}

function mapStateToProps(state){
    return { 
        event: state.events.selectedEvent 
    }
}

export default connect(mapStateToProps)(AnswerQuestionsDialog);