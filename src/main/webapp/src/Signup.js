import React, { Component } from 'react'

import Container from 'react-bootstrap/Container';
import Row from 'react-bootstrap/Row';
import Col from 'react-bootstrap/Col';
import Form from "react-bootstrap/Form";
import Card from "react-bootstrap/Card";
import Button from "react-bootstrap/Button";
import axios from 'axios';
import {Growl} from 'primereact/growl';
import {
    Link,
  } from "react-router-dom";

class Signup extends Component {
    constructor(props){
        super();
        this.state = {
            namesurname: "",
            email: "",
            tcno: "",
            password: "",
            username: ""
        }
    }
    

    handleSubmit = (event) => {
        event.preventDefault();
        if (!this.state.namesurname.length || !this.state.email.length || !this.state.tcno.length || !this.state.password.length) {
            event.stopPropagation();
            this.showDialog("error",'Hesabınız oluşturulamadı. Bilgiler boş bırakılamaz.');
        }
        else if (   !/^[A-Z0-9._%+-]+@[A-Z0-9.-]+\.[A-Z]{2,}$/i.test(this.state.email)    ){
            event.stopPropagation();
            this.showDialog("error",'Hesabınız oluşturulamadı. E-posta adresinizi kontrol ediniz.');
        }
        else if ( this.state.tcno.length !== 11 ){
            event.stopPropagation();
            this.showDialog("error",'Hesabınız oluşturulamadı. TC kimlik numaranız 11 haneli olmalıdır.');
        }
        else if ( this.state.password.length <=5 ){
            event.stopPropagation();
            this.showDialog("error",'Hesabınız oluşturulamadı. Şifreniz minimum 6 haneli olmalıdır.');
        }
        else {
            axios.post("/api/createPerson",{
                username: this.state.username,
                tcNo: this.state.tcno,
                email: this.state.email,
                nameSurname: this.state.namesurname,
                password: this.state.password
            })
            .then(response => {
                console.log(response);
                this.showDialog(response.data.type,response.data.message);
                if (response.data.type === "success"){
                    this.setState({
                        namesurname: "",
                        email: "",
                        tcno: "",
                        password: "", 
                        username: ""
                    })
                }
            })            
        }
    };

    showDialog = (type,message) => {
        this.growl.show({severity: type, detail: message});
    }

    render() {
        return (
            <Container  >
                <Growl ref={(el) => this.growl = el} />
                
                <Card className ="mt-5 pl-4 py-3 " >
                <Form onSubmit={this.handleSubmit} >
                    <Form.Group as={Row} controlId="namesurname">
                        <Form.Label column sm={3}>
                        Ad Soyad
                        </Form.Label>
                        <Col sm={8}>
                        <Form.Control value = {this.state.namesurname} onChange={event => {this.setState({namesurname: event.target.value})}} 
                                     type="text" placeholder="Adınızı ve soyadınızı giriniz."  />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="tcno">
                        <Form.Label column sm={3}>
                        TC Kimlik Numarası
                        </Form.Label>
                        <Col sm={8}>
                        <Form.Control value = {this.state.tcno} onChange={event => {this.setState({tcno: event.target.value})}} 
                                    type="text" maxLength={11} placeholder="TC kimlik numaranızı giriniz"  />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="email">
                        <Form.Label column sm={3}>
                        E-posta Adresi
                        </Form.Label>
                        <Col sm={8}>
                        <Form.Control value = {this.state.email} onChange={event => {this.setState({email: event.target.value})}} 
                                    type="email" placeholder="E-posta adresinizi giriniz"  />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="username">
                        <Form.Label column sm={3}>
                        Kullanıcı Adı
                        </Form.Label>
                        <Col sm={8}>
                        <Form.Control value = {this.state.username} onChange={event => {this.setState({username: event.target.value})}} 
                                    type="text" maxLength={15} placeholder="Kullanıcı adı belirleyiniz"  />
                        </Col>
                    </Form.Group>

                    <Form.Group as={Row} controlId="password">
                        <Form.Label column sm={3}>
                        Şifre
                        </Form.Label>
                        <Col sm={8}>
                        <Form.Control value = {this.state.password} onChange={event => {this.setState({password: event.target.value})}} 
                                    type="password" placeholder="Güçlü bir şifre belirleyiniz"  />
                        </Col>
                    </Form.Group>


                    <Form.Group as={Row} className = "mt-3" >
                        <Col sm={{ span: 2, offset: 3 }}>
                        <Button onClick = {this.handleSubmit} type="Submit" style={{"width":"150px"}} >Oluştur</Button>
                        </Col>
                        <Col>
                        <div className = "ml-3 mt-2" >
                            <Link  to="/login">Hesabın var mı? Giriş yap.</Link>
                        </div></Col>
                    </Form.Group>
                    </Form>
                    </Card>
            </Container>
        )
    }
}

export default Signup;
