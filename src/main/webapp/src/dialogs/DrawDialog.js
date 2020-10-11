import React, { Component } from 'react';
import axios from 'axios';
import { Dialog } from 'primereact/dialog';
import Button from 'react-bootstrap/Button';
import CenterView from '../CenterView'
import Col from 'react-bootstrap/Col';
import { connect } from 'react-redux'

class DrawDialog extends Component {
    constructor() {
        super();

        this.state = {
            data: [],
            visibility: false
        }
    }

    getResult = () => {
        axios.get("/api/admin/draw/" + this.props.event.id, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem("jwt")
            }
        })
        .then(response => {
            console.log(response.data)
            let winner = response.data
            this.setState({
                person: winner,
                visibility: true
            })
        })
    }


    render() {
        return ( 
            <div>
                <Button className="mx-2" onClick={this.getResult} disabled={this.props.disabled}  >Çekiliş</Button>
                <Dialog visible={this.state.visibility} style={{width: '50%'}}
                        onHide={()=>{this.setState({visibility:false})}}
                        header={"Çekiliş Sonucu"} >
                <CenterView>
                    {
                        this.state.person ? 
                        <Col sm={12} className="my-4" >
                            <b>Kazanan Adı Soyadı:</b> {this.state.person.nameSurname}
                            <br/>
                            <b>TC Kimlik Numarası:</b>  {this.state.person.tcNo}
                            <br/>
                            <b>İletişim E-posta Adresi:</b>  {this.state.person.email}
                            <br/>
                        </Col>
                        :
                        null
                    }
                </CenterView>
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

export default connect(mapStateToProps)(DrawDialog);