
import React, { Component } from 'react';
import { Dialog } from 'primereact/dialog';
import Button from 'react-bootstrap/Button';
import { prepareChartData } from '../Utilities'
import CenterView from '../CenterView'
import { XAxis, Bar, BarChart, YAxis, Brush } from 'recharts';
import { connect } from 'react-redux'

class FirstChart extends Component {
    constructor() {
        super();

        this.state = {
            data: [],
            visibility: false
        }
    }

    prepareData = () => {
        console.log(this.props.events)
        const result = prepareChartData(this.props.events)

        this.setState({
            data: result,
            visibility: true
        })
    }


    render() {
        let variableHeight = this.state.data.length >=2 ? 30 : 0;
        return ( 
            <div>
                <Button onClick={this.prepareData}  >Genel grafik</Button>
                <Dialog visible={this.state.visibility} style={{width: '80%'}}
                        onHide={()=>{this.setState({visibility:false})}}
                        header={"Etkinliklere Katılım Sayıları"} >
                <CenterView>
                <BarChart width={600} height={400} data={this.state.data}
                            margin={{top: 5, right: 30, left: 20, bottom: 5}}>
                    <XAxis dataKey="name"  />
                    <YAxis allowDecimals={false} />
                    <Bar dataKey="uv" fill="#8884d8" />
                    <Brush dataKey="name" stroke="#8884d8" height={variableHeight} />
                </BarChart>
                </CenterView>
                </Dialog>
            </div>
        )
    }
}

function mapStateToProps(state){
    return { 
        events: state.events.allEvents 
    }
}

export default connect(mapStateToProps)(FirstChart);