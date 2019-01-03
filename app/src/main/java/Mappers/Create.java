package Mappers;

import android.widget.EditText;

import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;

import Models.IndividualReviewDO;
import jackal.visitr.visitr.R;

public class Create {

    public static void createIndividualReview(final DynamoDBMapper dynamoDBMapper, final IndividualReviewDO individualReviewItem) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(individualReviewItem);
                // Item saved
            }
        }).start();
    }
}
