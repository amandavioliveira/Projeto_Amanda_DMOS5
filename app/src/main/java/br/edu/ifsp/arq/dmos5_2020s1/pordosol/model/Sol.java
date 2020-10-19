package br.edu.ifsp.arq.dmos5_2020s1.pordosol.model;

import java.io.Serializable;

public class Sol implements Serializable {
    private String sunrise;
    private String sunset;

    public Sol(String sunrise, String sunset) {
        this.sunrise = sunrise;
        this.sunset = sunset;
    }

    public String getSunrise() {
        return sunrise;
    }
    public String getSunset() {
        return sunset;
    }
}
