package Models;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

import java.util.Set;

@DynamoDBTable(tableName = "visitr-mobilehub-903820817-Reviews")
public class ReviewDO {

    private int _id;
    private int _tour_id;
    private Set<Integer> _reviews;

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
    public int get_tour_id() {
        return _tour_id;
    }

    public void set_tour_id(int _tour_id) {
        this._tour_id = _tour_id;
    }

    @DynamoDBAttribute(attributeName = "reviews")
    public Set<Integer> getReviews() {
        return _reviews;
    }

    public void setReviews(Set<Integer> reviews) {
        this._reviews = reviews;
    }
}

