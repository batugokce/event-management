import React, { Component } from 'react';
import axios from 'axios';
import { Dialog } from 'primereact/dialog';
import Button from 'react-bootstrap/Button';
import { prepareSecChartData } from '../Utilities'
import CenterView from '../CenterView'

import {XAxis, Bar, BarChart, YAxis, Brush } from 'recharts';
import { connect } from 'react-redux'


class SecondChart extends Component {
    constructor() {
        super();

        this.state = {
            data: [],
            visibility: false
        }
    }

    prepareData = () => {
        axios.get("/api/admin/secondChart/"+this.props.event.id, {
            headers: {
                Authorization: 'Bearer ' + localStorage.getItem("jwt")
            }
        })
        .then(response => {
            console.log(response.data)
            const result = prepareSecChartData(response.data)
            this.setState({
                data: result,
                visibility: true
            })
        })
    }


    render() {
        let height = this.state.data.length >=2 ? 30 : 0;
        return ( 
            <div>
                <Button className="mx-2" onClick={this.prepareData} disabled={this.props.disabled}  >Etkinlik Grafiği</Button>
                <Dialog visible={this.state.visibility} style={{width: '80%'}}
                        onHide={()=>{this.setState({visibility:false})}}
                        header={"Etkinlikliğin Günlere Göre Başvuru Sayıları"} >
                <CenterView>
                <BarChart width={600} height={400} data={this.state.data}
                            margin={{top: 5, right: 30, left: 20, bottom: 5}}>
                    <XAxis dataKey="name"  />
                    <YAxis allowDecimals={false} />
                    <Bar dataKey="uv" fill="#8884d8" />
                    <Brush dataKey="name" height={height} stroke="#8884d8" />
                </BarChart>
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

export default connect(mapStateToProps)(SecondChart);