import React from 'react'
import Button from 'react-bootstrap/Button';
import {Dialog} from 'primereact/dialog';
import {InputTextarea} from 'primereact/inputtextarea';
import axios from 'axios'
import {Growl} from 'primereact/growl';

import { connect } from 'react-redux'
//import * as actions from './actions'

class AskingDialog extends React.Component {
    constructor() {
        super();

        this.state = {
            visibility: false,
            value: ""
        }
    }

    showDialog = () => {
        this.setState({
            visibility:true
        })
    }

    askQuestion = () => {
        if (this.state.value === "") {
            this.growl.show({ severity: 'error', summary: 'İşlem başarısız.', detail: "Soru boş bırakılamaz." });
            return;
        }
        let header = {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem("jwt")
            }
        }
        let data = {
            question: this.state.value
        }
        let url = "/api/askQuestion/" + this.props.event.id + '/' + localStorage.getItem("tcno")

        console.log(url)
        console.log(header)
        axios.post(url,data,header)
            .then(response => {
                let messageResponse = response.data
                console.log(messageResponse)
                this.setState({
                    value: "",
                    visibility: false
                })
                this.growl.show({ severity: messageResponse.type, detail: messageResponse.message });
            })
            .catch(error => {
                console.log(error)
            })
    }

    render(){
        const footer = (
            <div><Button onClick={this.askQuestion} type="Submit" >Gönder</Button> </div>
        );

        return (
            <div>
                    <Button onClick={this.showDialog} disabled={this.props.disabled} className="mr-2"  >Soru sor</Button>
                    <Dialog visible={this.state.visibility} style={{width: '50%'}}
                            onHide={()=>{this.setState({visibility:false})}}
                            header={"Soru Sor"} footer={footer} >
                    <InputTextarea rows={5} cols={80} value={this.state.value} onChange={(e) => this.setState({value: e.target.value})} autoResize={true} />

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

export default connect(mapStateToProps)(AskingDialog);