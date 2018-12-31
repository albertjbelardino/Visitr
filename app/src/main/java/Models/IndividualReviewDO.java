package Models;


import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBAttribute;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBHashKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBRangeKey;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBTable;

@DynamoDBTable(tableName = "visitr-mobilehub-903820817-Individual_Reviews")
public class IndividualReviewDO {

    private int _id;
    private int _tour_id;
    private String _creator_id;
    private String _content;

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

    @DynamoDBAttribute(attributeName = "creator_id")
    public String get_creator_id() {
        return _creator_id;
    }

    public void set_creator_id(String _creator_id) {
        this._creator_id = _creator_id;
    }

    @DynamoDBAttribute(attributeName = "content")
    public String get_content() {
        return _content;
    }

    public void set_content(String _content) {
        this._content = _content;
    }
}
