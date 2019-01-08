package Mappers;

import android.content.Context;
import android.util.Log;
import android.widget.EditText;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

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

    public static DynamoDBMapper inititalizeMapper(Context context) {
        AWSMobileClient.getInstance().initialize(context, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();

        AWSMobileClient.getInstance().initialize(context).execute();

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();

        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        DynamoDBMapper db = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();

        return db;
    }
}
