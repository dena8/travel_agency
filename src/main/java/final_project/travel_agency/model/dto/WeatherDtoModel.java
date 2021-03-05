package final_project.travel_agency.model.dto;

public class WeatherDtoModel {
    private Current current;
    private Location location;
    private boolean success;

    public WeatherDtoModel() {
    }

    public Current getCurrent() {
        return current;
    }

    public void setCurrent(Current current) {
        this.current = current;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }
}
