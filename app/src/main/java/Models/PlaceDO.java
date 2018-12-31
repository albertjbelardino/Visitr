package Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "visitr-mobilehub-903820817-Places")
public class PlaceDO {
    private int _id;
    private String _name;
    private String _description;
    private String _google_city_id;
    private String _google_places_id;
    private Double _latitude;
    private Double _longitude;
    private int _time_spent;

    @DynamoDBHashKey(attributeName = "id")
    @DynamoDBAttribute(attributeName = "id")
    public int get_id() {
        return _id;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    @DynamoDBRangeKey(attributeName = "tour_id")
    @DynamoDBAttribute(attributeName = "tour_id")
    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    @DynamoDBAttribute(attributeName = "google_city_id")
    public String get_google_city_id() {
        return _google_city_id;
    }

    public void set_google_city_id(String _google_city_id) {
        this._google_city_id = _google_city_id;
    }

    @DynamoDBAttribute(attributeName = "google_places_id")
    public String get_google_places_id() {
        return _google_places_id;
    }

    public void set_google_places_id(String _google_places_id) {
        this._google_places_id = _google_places_id;
    }

    @DynamoDBAttribute(attributeName = "latitude")
    public Double get_latitude() {
        return _latitude;
    }

    public void set_latitude(Double _latitude) {
        this._latitude = _latitude;
    }

    @DynamoDBAttribute(attributeName = "longitude")
    public Double get_longitude() {
        return _longitude;
    }

    public void set_longitude(Double _longitude) {
        this._longitude = _longitude;
    }

    @DynamoDBAttribute(attributeName = "time_spent")
    public int get_time_spent() {
        return _time_spent;
    }

    public void set_time_spent(int _time_spent) {
        this._time_spent = _time_spent;
    }
}
