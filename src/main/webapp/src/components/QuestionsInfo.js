import React, { Component } from 'react';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Card from "react-bootstrap/Card";
import { CardContent } from '@material-ui/core';
import { connect } from 'react-redux'

class QuestionsInfo extends Component {

    QuestionCard = (item) => {
        return (
        <Card className="my-3" style={{"width":"120%"}} key={item.id}  >
            <Card.Header>{item.question}</Card.Header>
            <CardContent>{item.answer ? item.answer : "Henüz cevaplandırılmamış."}</CardContent>
        </Card>
        )
    }

    render() {
        return (
            <div>
            {
                (this.props.event && this.props.event.personQuestions.length > 0) ?
                 <Card className ="pl-4 py-3 my-3 " style={{"width":"100%"}} >
                    <Row>
                        <Col sm={9}>
                            {this.props.event.personQuestions.map(item => this.QuestionCard(item))}
                        </Col>
                    </Row>
                    </Card>
                :
                null
            }
            </div>
        )
    }
}

function mapStateToProps(state){
    return { 
        event: state.events.selectedEvent 
    }
}

export default connect(mapStateToProps)(QuestionsInfo);
