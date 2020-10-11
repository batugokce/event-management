import React from 'react'
import Navbar from 'react-bootstrap/Navbar';
import Nav from 'react-bootstrap/Nav';
import Form from 'react-bootstrap/Form';

import { BrowserRouter as Router } from "react-router-dom";

function NavBar(props) {
        return (
            <Router>
            <Navbar bg="dark" variant="dark">
                <Navbar.Brand>Etkinlik Yönetim Sistemi</Navbar.Brand>
                <Nav className="mr-auto">
                    {
                        props.page === "login"     ? <Nav.Link href="/signup">Kaydol</Nav.Link>
                        : 
                        props.page === "signup"    ? <Nav.Link href="/login">Giriş yap</Nav.Link>
                        : 
                        props.page === "loggedin"  ? <Nav.Link href="/list">Etkinlik Listele</Nav.Link>
                        : 
                        props.page === "adminpage" ? <Nav>
                                                        <Nav.Link href="/newevent">Yeni Etkinlik</Nav.Link>
                                                        <Nav.Link href="/adminlist">Etkinlik Listele</Nav.Link>
                                                        <Nav.Link href="/about">Hakkında</Nav.Link>
                                                    </Nav>
                        :
                        null
                    }
                </Nav>
                {
                    props.page === "loggedin" ? <Form inline><Nav.Link href="/login" style = {{"color" : "rgba(255,255,255,.8)"}} 
                                    onClick = {() => {localStorage.clear()}} >Oturumu Kapat</Nav.Link></Form>
                    :
                    props.page === "adminpage" ? <Form inline><Nav.Link href="/login" style = {{"color" : "rgba(255,255,255,.8)"}} 
                                    onClick = {() => {localStorage.clear()}} >Oturumu Kapat</Nav.Link></Form>
                    :
                    null
                }
                
                
            </Navbar>
            </Router>
        )
}

export default NavBar;