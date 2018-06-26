package com.example.acer.callingapp;

/**
 * Created by acer on 16-Jun-18.
 */

class CallSample {
    private String month;
    private String rain;

    public String getMonth()
    {
        return month;
    }

    public void setName(String month) {
        this.month = month;

    }

    @Override
    public String toString() {
        return "WeatherSample{" +
                "Name='" + month + '\'' +
                ", Phone=" + rain +
                '}';
    }

    public String getRain()
    {
        return rain;
    }
    public void setPhone(String rain)
    {
        this.rain = rain;
    }
}