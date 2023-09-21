// eslint-disable-next-line no-unused-vars
import React, { Component } from 'react';
import PropTypes from 'prop-types';
import { Map, Marker, GoogleApiWrapper } from 'google-maps-react';

class MapOfEvent extends Component {
  render() {
    const mapStyles = {
      width: '100%',
      height: '385px'
    };

    const markerPosition = {
      lat: 6.4281,
      lng: 3.5946
    };

    return (
      <Map
        google={this.props.google}
        zoom={14}
        style={mapStyles}
        initialCenter={markerPosition}
      >
        <Marker position={markerPosition} />
      </Map>
    );
  }
}

MapOfEvent.propTypes = {
  google: PropTypes.object.isRequired
};
// eslint-disable-next-line react-refresh/only-export-components
  export default GoogleApiWrapper({
    apiKey: 'AIzaSyA22GBhIK3LwSHcYDlB8UYJ4x1IoGeuqvM'
  })(MapOfEvent);