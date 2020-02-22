package com.stream_work.ch08.job;

public class LatLongHolder {
    private Double latitude;
    private Double longitude;

    public LatLongHolder(Double latitude, Double longitude) {
        this.latitude = latitude;
        this.longitude = longitude;
    }

    public Double getLatitude() {
        return latitude;
    }

    public Double getLongitude() {
        return longitude;
    }
}
