import React, { Component } from 'react'
import axios from 'axios'
import {temperatureConversion} from '../Utilities'
import Card from "react-bootstrap/Card";
import { connect } from 'react-redux'

class WeatherStatus extends Component {
    constructor(){
        super();
        this.state = {
            tempMax: 0,
            tempMin: 0,
            precProb: 0,
            isweatherInfoFound: false
        }
    }

    componentWillReceiveProps(e){
        this.setState({
            isweatherInfoFound: false,
            precProb: 0,
            tempMax: 0,
            tempMin: 0
        })
        if(e.event){ //e.event
            let startDateString = e.event.startDate.substr(0,10)
            let startDate = new Date(startDateString)
            startDate.setTime(startDate.getTime() + startDate.getTimezoneOffset()*60*1000);
            let startDateTimeStamp = startDate.getTime()/1000;
            let url = "forecast/your-token-here/"+ e.event.latitude + ',' + e.event.longitude;
            axios.get(url)
            .then(response => {
                let arrayOfDays = response.data.daily.data
                console.log(arrayOfDays)
                arrayOfDays.forEach(element => {
                    if (element.time === startDateTimeStamp){
                        this.setState({
                            isweatherInfoFound: true,
                            precProb: element.precipProbability,
                            tempMax: temperatureConversion(element.temperatureMax),
                            tempMin: temperatureConversion(element.temperatureMin)
                        })
                    }
                });
            })
            .catch(error=> {
                console.log(error)
            })
        }
    }
    
    render() {
        return (
            <div>
            {
                (this.state.isweatherInfoFound) ?
                <Card className ="pl-4 py-3 my-3 " style={{"width":"100%"}} >
                        <center><b>Etkinlik günü maksimum sıcaklık:</b>  {this.state.tempMax}  </center>
                        <center><b>Etkinlik günü minimum sıcaklık:</b>  {this.state.tempMin}  </center>
                        <center><b>Etkinlik günü yağış ihtimali:</b>  {this.state.precProb}  </center>
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

export default connect(mapStateToProps)(WeatherStatus);