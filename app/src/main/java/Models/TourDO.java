package Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBIndexHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.List;
import java.util.Set;

@DynamoDBTable(tableName = "visitr-mobilehub-903820817-Tours")
public class TourDO {
    private int _tour_id;
    private String _creator_id;
    private String _name;
    private String _description;
    private String _genre;
    private String _google_city_id;
    private String _city;
    private Set<String> _keywords;
    private List<Integer> _places;
    private double _rating;
    private int _reviews;
    private double _total_time;

    @DynamoDBHashKey(attributeName = "tour_id")
    @DynamoDBAttribute(attributeName = "tour_id")
    public int get_tour_id() {
        return _tour_id;
    }

    public void set_tour_id(int _tour_id) {
        this._tour_id = _tour_id;
    }

    @DynamoDBRangeKey(attributeName = "creator_id")
    @DynamoDBAttribute(attributeName = "creator_id")
    public String get_creator_id() {
        return _creator_id;
    }

    public void set_creator_id(String _creator_id) {
        this._creator_id = _creator_id;
    }

    @DynamoDBAttribute(attributeName = "description")
    public String get_description() {
        return _description;
    }

    public void set_description(String _description) {
        this._description = _description;
    }

    @DynamoDBIndexHashKey(attributeName = "genre", globalSecondaryIndexName = "GenreIndex")
    public String get_genre() {
        return _genre;
    }

    public void set_genre(String _genre) {
        this._genre = _genre;
    }

    @DynamoDBAttribute(attributeName = "google_city_id")
    public String get_google_city_id() {
        return _google_city_id;
    }

    public void set_google_city_id(String _google_city_id) {
        this._google_city_id = _google_city_id;
    }

    @DynamoDBAttribute(attributeName = "city")
    public String get_city() {
        return _city;
    }

    public void set_city(String city) {
        this._city = city;
    }

    @DynamoDBAttribute(attributeName = "keywords")
    public Set<String> get_keywords() {
        return _keywords;
    }

    public void set_keywords(Set<String> _keywords) {
        this._keywords = _keywords;
    }

    @DynamoDBAttribute(attributeName = "places")
    public List<Integer> getPlaces() {
        return _places;
    }

    public void setPlaces(List<Integer> places) {
        this._places = places;
    }

    @DynamoDBAttribute(attributeName = "rating")
    public double get_rating() {
        return _rating;
    }

    public void set_rating(double rating) {
        this._rating = rating;
    }

    @DynamoDBAttribute(attributeName = "reviews")
    public int get_reviews() {
        return _reviews;
    }

    public void set_reviews(int reviews) {
        this._reviews = reviews;
    }

    @DynamoDBAttribute(attributeName = "total_time")
    public double get_total_time() {
        return _total_time;
    }

    public void set_total_time(double total_time) {
        this._total_time = total_time;
    }

    @DynamoDBAttribute(attributeName = "name")
    public String get_name() {
        return _name;
    }

    public void set_name(String _name) {
        this._name = _name;
    }
}
