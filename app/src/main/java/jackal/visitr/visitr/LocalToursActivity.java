package jackal.visitr.visitr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;

public class LocalToursActivity extends AppCompatActivity {

    //home computer test

    DynamoDBMapper dynamoDBMapper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_tours);

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();
        //setting up toolbar
        Toolbar menuToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(menuToolbar);
        //setting up recyclerView
        RecyclerView rv = (RecyclerView) findViewById(R.id.tour_list);
        BaseTour[] test = new BaseTour[10];
        test[0] = new BaseTour(1, "A Tour", "A Place");
        test[1] = new BaseTour(2, "Brick walls to hit your head against", "Dumbtown USA");
        test[2] = new BaseTour(3, "tesing", "what");
        test[3] = new BaseTour(4, "a", "y");
        test[4] = new BaseTour(5, "b", "x");
        test[5] = new BaseTour(6, "c", "z");
        test[6] = new BaseTour(7, "cccccccccccccc", "qwe");
        test[7] = new BaseTour(8, "vvvvvvvvvvvvvvv", "bgggggg");
        test[8] = new BaseTour(9, "ggggg", "addfsdf");
        test[9] = new BaseTour(10, "Cheesesteak Tour", "Philadelphia, PA");


        TourListAdapter tla = new TourListAdapter(test);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        rv.setAdapter(tla);
        rv.setLayoutManager(llm);
        rv.setHasFixedSize(true);





        // AWSMobileClient enables AWS user credentials to access your table
        AWSMobileClient.getInstance().initialize(this).execute();

        AWSCredentialsProvider credentialsProvider = AWSMobileClient.getInstance().getCredentialsProvider();
        AWSConfiguration configuration = AWSMobileClient.getInstance().getConfiguration();


        // Add code to instantiate a AmazonDynamoDBClient
        AmazonDynamoDBClient dynamoDBClient = new AmazonDynamoDBClient(credentialsProvider);

        this.dynamoDBMapper = DynamoDBMapper.builder()
                .dynamoDBClient(dynamoDBClient)
                .awsConfiguration(configuration)
                .build();


        //key = AIzaSyCI80Oyo1ovK5M2gk-1WNUQdTvK6Plt3sw

        //test again

    }

    //menu onClick functions

    public boolean onLocalToursClicked(MenuItem item)
    {
        return false;
    }

    public boolean onProfileClicked(MenuItem item)
    {
        Intent i = new Intent(this, ProfileActivity.class);
        finish();
        startActivity(i);
        return(true);
    }

    public boolean onYourTourClicked(MenuItem item)
    {
        Intent i = new Intent(this, YourTourActivity.class);
        finish();
        startActivity(i);
        return(true);
    }

    public boolean onCreateClicked(MenuItem item)
    {
        Intent i = new Intent(this, CreateTourActivity.class);
        finish();
        startActivity(i);
        return(true);
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }
}
