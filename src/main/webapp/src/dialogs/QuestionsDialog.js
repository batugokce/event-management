import React, { Component } from 'react';
import Button from 'react-bootstrap/Button';
import Row from 'react-bootstrap/Row';
import {Dialog} from 'primereact/dialog';
import Form from "react-bootstrap/Form";
import Col from 'react-bootstrap/Col';
import {Messages} from 'primereact/messages';

class QuestionsDialog extends Component {

    isFormValid = () => {
        let nOfQuestions = this.props.eventItem.question.counter;
        if (this.props.q1.length === 0){
            return false;
        }
        else if ((nOfQuestions > 1) && (this.props.q2.length === 0)){
            return false;
        }
        else if ((nOfQuestions === 3) && (this.props.q3.length === 0)){
            return false;
        }
        return true;
    }
    
    preventRefresh = (event) => {
        event.preventDefault();
        if (this.isFormValid()){
            this.props.onSubmit();
        }
        else {
            this.messages.show({severity: 'error', summary: 'İşlem başarısız', detail: 'Cevaplar boş bırakılamaz.'});
        }
        
    }

    render(){
        const footer = (
            <div><Button type="Submit" >Değişikliği Onayla</Button> </div>
        );


        return (
            <Form onSubmit={this.preventRefresh} >
                  <Dialog header="Katılım Soruları" footer={footer} visible={this.props.questionsVisibility} 
                                  style={{width: '70%'}} maximizable modal={true} 
                                  onHide={this.props.hideQuestions}>
                          {
                              this.props.eventItem && this.props.eventItem.question ?
                              (
                                  <div>
                              <Form.Group as={Row} className ="my-3" style = {{width: '100%'}} controlId="q1row">
                                  <Form.Label column sm={3}>{this.props.eventItem.question.q1}</Form.Label>
                                  <Col sm={6}>
                                      <Form.Control value = {this.props.q1} onChange={this.props.q1change} type="text"  />
                                  </Col>
                              </Form.Group>
                          
                          {
                              this.props.eventItem.question.counter > 1 ?
                              (
                                  <Form.Group as={Row} className ="my-3" style = {{width: '100%'}} controlId="q2row">
                                  <Form.Label column sm={3}>{this.props.eventItem.question.q2}</Form.Label>
                                  <Col sm={6}>
                                      <Form.Control value = {this.props.q2} onChange={this.props.q2change} type="text"  />
                                  </Col>
                                  </Form.Group>
                              )
                              :
                              null
                          }
      
                          {
                              this.props.eventItem.question.counter > 2 ?
                              (
                                  <Form.Group as={Row} className ="my-3" style = {{width: '100%'}} controlId="q3row">
                                  <Form.Label column sm={3}>{this.props.eventItem.question.q3}</Form.Label>
                                  <Col sm={6}>
                                      <Form.Control value = {this.props.q3} onChange={this.props.q3change} type="text"  />
                                  </Col>
                                  </Form.Group>
                              )
                              :
                              null
                          }
                              </div>
                              )
                              :
                              null
                          }
                          <Messages ref={(el) => this.messages = el}></Messages>
                  </Dialog >
              </Form>
          )
    }

    
}

export default QuestionsDialog;