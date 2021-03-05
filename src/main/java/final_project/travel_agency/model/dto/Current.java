package final_project.travel_agency.model.dto;

import java.util.List;

public class Current {
    private Integer temperature;
    private Integer cloudcover;
    private Integer wind_speed;
    private List<String> weather_descriptions;
    private Integer humidity;

    public Current() {
    }

    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    public Integer getCloudcover() {
        return cloudcover;
    }

    public void setCloudcover(Integer cloudcover) {
        this.cloudcover = cloudcover;
    }

    public Integer getWind_speed() {
        return wind_speed;
    }

    public void setWind_speed(Integer wind_speed) {
        this.wind_speed = wind_speed;
    }

    public List<String> getWeather_descriptions() {
        return weather_descriptions;
    }

    public void setWeather_descriptions(List<String> weather_descriptions) {
        this.weather_descriptions = weather_descriptions;
    }

    public Integer getHumidity() {
        return humidity;
    }

    public void setHumidity(Integer humidity) {
        this.humidity = humidity;
    }
}
