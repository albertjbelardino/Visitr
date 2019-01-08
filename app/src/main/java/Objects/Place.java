package Objects;

import Models.PlaceDO;

public class Place {
    private int id;
    private String name;
    private String description;
    private String google_city_id;
    private String city_name;
    private String google_places_id;
    private Double latitude;
    private Double longitude;
    private int time_spent;

    public Place() {

    }

    public Place(String name, String google_places_id) {
        this.name = name;
        this.google_places_id = google_places_id;
    }

    public Place(PlaceDO pdo)
    {
        this.id = pdo.get_id();
        this.name = pdo.get_name();
        this.description = pdo.get_description();
        this.google_city_id  = pdo.get_google_city_id();
        this.google_places_id = pdo.get_google_places_id();
        this.latitude = pdo.get_latitude();
        this.longitude = pdo.get_longitude();
        this.time_spent = pdo.get_time_spent();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGoogle_city_id() {
        return google_city_id;
    }

    public void setGoogle_city_id(String google_city_id) {
        this.google_city_id = google_city_id;
    }

    public String getCity_name() {
        return city_name;
    }

    public void setCity_name(String city_name) {
        this.city_name = city_name;
    }

    public String getGoogle_places_id() {
        return google_places_id;
    }

    public void setGoogle_places_id(String google_places_id) {
        this.google_places_id = google_places_id;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public int getTime_spent() {
        return time_spent;
    }

    public void setTime_spent(int time_spent) {
        this.time_spent = time_spent;
    }
}

