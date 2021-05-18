package ru;

import java.util.Objects;

public class Parameters {

    String region;
    int lat;
    int lon;

    public Parameters(int lat, int lon, String region) {
        this.lat = lat;
        this.lon = lon;
        this.region = region;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Parameters that = (Parameters) o;
        return lat == that.lat && lon == that.lon && Objects.equals(region, that.region);
    }

    @Override
    public int hashCode() {
        return Objects.hash(lat, lon, region);
    }
}
