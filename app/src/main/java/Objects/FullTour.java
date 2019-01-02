package Objects;

import java.util.List;
import java.util.Set;

import Models.TourDO;

public class FullTour extends BaseTour {

    private int tour_id;
    private String creator_id;
    private String description;
    private String genre;
    private String google_city_id;
    private Set<String> keywords;
    private List<Integer> places;
    private double rating;
    private int reviews;
    private double total_time;

    public FullTour(int ID, String name, String city) {
        super(ID, name, city);
    }

    public FullTour(TourDO tourDO) {
        super(tourDO.get_tour_id(), tourDO.get_name(), tourDO.get_city());
        this.description = tourDO.get_description();
        this.genre = tourDO.get_genre();
        this.google_city_id = tourDO.get_google_city_id();
        this.keywords = tourDO.get_keywords();
        this.places = tourDO.getPlaces();
        this.rating = tourDO.get_rating();
        this.reviews = tourDO.get_reviews();
        this.total_time = tourDO.get_total_time();
    }

    public int getTour_id() {
        return tour_id;
    }

    public void setTour_id(int tour_id) {
        this.tour_id = tour_id;
    }

    public String getCreator_id() {
        return creator_id;
    }

    public void setCreator_id(String creator_id) {
        this.creator_id = creator_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getGoogle_city_id() {
        return google_city_id;
    }

    public void setGoogle_city_id(String google_city_id) {
        this.google_city_id = google_city_id;
    }

    public Set<String> getKeywords() {
        return keywords;
    }

    public void setKeywords(Set<String> keywords) {
        this.keywords = keywords;
    }

    public List<Integer> getPlaces() {
        return places;
    }

    public void setPlaces(List<Integer> places) {
        this.places = places;
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public int getReviews() {
        return reviews;
    }

    public void setReviews(int reviews) {
        this.reviews = reviews;
    }

    public double getTotal_time() {
        return total_time;
    }

    public void setTotal_time(double total_time) {
        this.total_time = total_time;
    }
}