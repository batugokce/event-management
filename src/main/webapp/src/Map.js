import React, { Component } from 'react';
import GoogleMapReact from 'google-map-react';
import RoomIcon from '@material-ui/icons/Room';

const AnyReactComponent = () => <div className="mr-4 mb-4" ><RoomIcon /></div>;

class Map extends Component {
    constructor(){
        super();

        this.state = {
            center: {
                lat: 36.78,
                lng: 34.57
            },
            variableCenter: {
              lat: 36.78,
              lng: 34.57
          }
        }
    }


  render() {
    return (
      <div style={{ height: '60vh', width: '100%' }}>
        <GoogleMapReact
          bootstrapURLKeys={{ key: "your_token_Here",
                              language: 'tr',
                              region: 'tr', }}
          defaultCenter={this.state.center}
          center={this.props.center}

          defaultZoom={this.props.zoomLevel}
          onClick={this.props.onClick}
        >
          <AnyReactComponent
            lat={this.props.lat}
            lng={this.props.lng}
          />
          
        </GoogleMapReact>
      </div>
    );
  }
}

export default Map;