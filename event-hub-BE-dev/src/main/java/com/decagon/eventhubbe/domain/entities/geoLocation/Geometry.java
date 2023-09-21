package com.decagon.eventhubbe.domain.entities.geoLocation;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Geometry {
    @JsonProperty("location")
    private GeoLocation location;
}
