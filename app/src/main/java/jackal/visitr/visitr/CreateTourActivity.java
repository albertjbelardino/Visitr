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

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

import Models.IndividualReviewDO;

public class CreateTourActivity extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);

        inititalizeMapper();
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(menuToolbar);

        Button button = (Button) findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createIndividualReview();
            }
        });
    }

    public void createIndividualReview() {
        final IndividualReviewDO individualReviewItem = new IndividualReviewDO();

        individualReviewItem.set_id(Integer.parseInt(((EditText) findViewById(R.id._id)).getText().toString()));
        individualReviewItem.set_tour_id(Integer.parseInt(((EditText) findViewById(R.id._id)).getText().toString()));
        individualReviewItem.set_creator_id(((EditText) findViewById(R.id._creator_id)).getText().toString());
        individualReviewItem.set_content(((EditText) findViewById(R.id._content)).getText().toString());

        new Thread(new Runnable() {
            @Override
            public void run() {
                dynamoDBMapper.save(individualReviewItem);
                // Item saved
            }
        }).start();
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
