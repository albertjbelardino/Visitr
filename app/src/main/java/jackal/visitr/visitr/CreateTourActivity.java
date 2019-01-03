package jackal.visitr.visitr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.gson.Gson;

import Mappers.Create;
import Models.IndividualReviewDO;
import Models.TourDO;
import Objects.FullTour;

public class CreateTourActivity extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;
    Toolbar menuToolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);

        initializeTextViews();
        inititalizeMapper();
        initializeMenu();
    }

    public void initializeMenu() {
        menuToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(menuToolbar);
    }

    public void inititalizeMapper() {
        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();

        AWSMobileClient.getInstance().initialize(this).execute();

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();

        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();
    }

    public void initializeTextViews() {
        EditText tourNameEditText, tourLocationEditText, tourDescriptionEditText;

        tourNameEditText        = (EditText) findViewById(R.id.tourNameEditText);
        tourLocationEditText    = (EditText) findViewById(R.id.tourLocationEditText);
        tourDescriptionEditText = (EditText) findViewById(R.id.tourDescriptionEditText);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onLocalToursClicked(MenuItem item) {
        Intent i = new Intent(this, LocalToursActivity.class);
        startActivity(i);
        finish();
        return true;
    }

    public boolean onProfileClicked(MenuItem item) {
        Intent i = new Intent(this, ProfileActivity.class);

        startActivity(i);
        finish();
        return(true);
    }

    public boolean onYourTourClicked(MenuItem item) {
        Intent i = new Intent(this, YourTourActivity.class);

        startActivity(i);
        finish();
        return(true);
    }

    public boolean onCreateClicked(MenuItem item) {
        return false;
    }
}
