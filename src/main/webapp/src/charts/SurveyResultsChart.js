import React, { Component } from 'react';
import axios from 'axios';
import { Dialog } from 'primereact/dialog';
import Button from 'react-bootstrap/Button';
import { getSurveyChartData } from '../Utilities'
import CenterView from '../CenterView'
import {PolarGrid, PolarAngleAxis, Radar, Legend, RadarChart } from 'recharts';
import { connect } from 'react-redux'

class SurveyResultsChart extends Component {
    constructor() {
        super();

        this.state = {
            data: [],
            visibility: false,
            existsContent: false,
            message: ""
        }
    }

    prepareData = () => {
        axios.get("/api/admin/surveyResults/"+this.props.event.id, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem("jwt")
            }
        })
        .then(response => {
            console.log(response.data)
            let messageResponse = response.data
            let result = []
            if (messageResponse.type === "error"){
                this.setState({
                    existsContent: false,
                    data: result,
                    visibility: true,
                    message: messageResponse.message
                })
            }
            else {
                result = getSurveyChartData(response.data.body)
                this.setState({
                    existsContent: true,
                    data: result,
                    visibility: true
                })
            }
        })
    }

    render() {
        return ( 
            <div>
                <Button className="mx-2" onClick={this.prepareData} disabled={this.props.disabled}  >Anket Sonuçları</Button>
                {
                    this.props.event ? 
                    <Dialog visible={this.state.visibility} style={{width: '80%'}}
                        onHide={()=>{this.setState({visibility:false})}}
                        header={"Etkinlik Anket Sonuçları"} >
                    
                        {
                            this.state.existsContent ?
                            <CenterView>
                            <RadarChart outerRadius={90} width={730} height={250} data={this.state.data}>
                                <PolarGrid />
                                <PolarAngleAxis dataKey="subject" />
                                <Radar name={this.props.event.eventName} dataKey="A" stroke="#8884d8" fill="#8884d8" fillOpacity={0.6} />
                                <Legend />
                            </RadarChart>
                            </CenterView>
                            :
                            <h6 className = "my-5" style={{"textAlign":"center"}} >{this.state.message}</h6>
                        }
                    
                    
                    </Dialog>
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

export default connect(mapStateToProps)(SurveyResultsChart);