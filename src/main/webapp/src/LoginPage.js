import React, { Component } from 'react'

import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Form from "react-bootstrap/Form";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import axios from 'axios'
import {Growl} from 'primereact/growl';
import {
    Link,
    Redirect
  } from "react-router-dom";

import { connect } from 'react-redux'
import * as actions from './actions'

class LoginPage extends Component {
    state = {
        type: "",
        tc: "",
        password: "",
        username: ""
    }


    userLogin = (event) => {
        event.preventDefault();
        event.stopPropagation();
        if (this.state.username.length === 0){
            this.showFailure("Kullanıcı adı boş bırakılamaz.")
            return;
        }
        if (this.state.password.length === 0){
            this.showFailure("Şifre boş bırakılamaz.")
            return;
        }
        axios.post("/api/authenticate",{
            username : this.state.username,
            password: this.state.password
        })
        .then(response => {
            console.log(response)
            localStorage.setItem("jwt",response.data.jwt)
            localStorage.setItem("tcno",response.data.tcNo)
            this.setState({
                type: response.data.type
            })
        })
        .catch(error => {
            if (error.response){
                let statusCode = error.response.status;
                switch (statusCode){
                    case 400:
                        this.showFailure("400: İsteği düzgün atamadın.");
                        break;
                    case 404:
                        this.showFailure("Verilen kullanıcı adıyla bir kayıt bulunamadı.");
                        break;
                    case 403:
                        this.showFailure("Hatalı şifre girdiniz.");
                        break;
                    case 500:
                        this.showFailure("500: Sunucuya bağlanılamadı.");
                        break;
                    default:
                        this.showFailure(statusCode + ": Belirlenemeyen hata");
                }
            }
            
        })
        
    }

    showFailure = (message) => {
        this.growl.show({severity: 'error', summary: 'İşlem başarısız', detail: message});
    }

    handleReduxx = () => {
        const newState = this.props.saveEvent("xxx");
        console.log(newState)
    }

    render() {
        return (
            <Container className = "my-5 py-3" >
                {
                    this.state.type === "ADMIN" ? <Redirect push to="/adminlist" /> : ( this.state.type === "USER" ? <Redirect push to="/list" /> : null ) 
                }
                <Growl ref={(el) => this.growl = el} />
                
                    <Col className = "mx-7"  >
                        <Card className = "py-3 px-4 mx-auto " >
                            <Form.Label style = {{"fontSize" : "x-large"}} className = "mx-auto" >Kullanıcı Girişi</Form.Label>
                            <Form>
                                <Form.Group controlId="formGroupUsernama 2">
                                    <Form.Label>Kullanıcı Adı</Form.Label>
                                    <Form.Control type="text" placeholder="Kullanıcı adınızı giriniz"
                                    value = {this.state.username} onChange={event => {this.setState({username: event.target.value})}} />
                                </Form.Group>
                        
                                <Form.Group controlId="formGroupPassword 2">
                                    <Form.Label>Şifre</Form.Label>
                                    <Form.Control type="password" placeholder="Şifrenizi giriniz" 
                                    value = {this.state.password} onChange={event => {this.setState({password: event.target.value})}} />
                                </Form.Group>
                                
                                <Row>
                                    
                                <Button className="ml-3" variant="primary" type="submit" onClick = {this.userLogin}  >
                                    Giriş yap
                                </Button>
                                <div className = "ml-3 mt-2" >
                                <Link  to="/signup">Henüz hesabın yok mu? Kaydol.</Link>
                                </div>
                                </Row>
                                
                            </Form>
                        </Card>
                        
                    </Col>
                    
            </Container>
        )
    }
}

function mapStateToProps(state){
    console.log(state)
    return { events: state.events.allEvents }
}

export default connect(mapStateToProps,actions)(LoginPage);