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
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import java.util.Arrays;

import Adapters.GenreDropdownAdapter;
import Adapters.TourListAdapter;
import Models.TourDO;
import Objects.BaseTour;
import Objects.FullTour;

public class LocalToursActivity extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;
    Spinner genreSpinner;
    Toolbar menuToolbar;
    RecyclerView tourRecyclerView;
    TourListAdapter tourListAdapter;

    //genre dropdown menu
    //on dropdown menu click, query database for that genre, parse array result, create new tour list adapter, put new adapter in recycler view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_tours);

        Log.d("LocalTours", "initialize menu");
        initializeMenu();
        Log.d("LocalTours", "initialize menu");
        initializeRecyclerView();
        Log.d("LocalTours", "initialize menu");
        initializeGenreSpinner();
        Log.d("LocalTours", "initialize menu");
        inititalizeMapper();
    }

    private void initializeMenu() {
        menuToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(menuToolbar);
    }

    //menu onClick functions

    public boolean onLocalToursClicked(MenuItem item) {
        return false;
    }

    public boolean onProfileClicked(MenuItem item) {
        Intent i = new Intent(this, ProfileActivity.class);
        finish();
        startActivity(i);
        return(true);
    }

    public boolean onYourTourClicked(MenuItem item) {
        Intent i = new Intent(this, YourTourActivity.class);
        finish();
        startActivity(i);
        return(true);
    }

    public boolean onCreateClicked(MenuItem item) {
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

    private void inititalizeMapper() {
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

    private void initializeGenreSpinner() {
        genreSpinner = (Spinner) findViewById(R.id.genre_spinner);
        GenreDropdownAdapter genreDropdownAdapter = new GenreDropdownAdapter(this, android.R.layout.simple_spinner_dropdown_item);
        genreSpinner.setAdapter(genreDropdownAdapter);
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String genre_query = parent.getItemAtPosition(position).toString();

                Condition rangeKeyCondition = new Condition()
                                                .withComparisonOperator(ComparisonOperator.CONTAINS)
                                                .withAttributeValueList(new AttributeValue().withS(genre_query));

                DynamoDBQueryExpression queryExpression = new DynamoDBQueryExpression()
                        .withRangeKeyCondition("genre", rangeKeyCondition)
                        .withConsistentRead(false);

                PaginatedList<TourDO> result = dynamoDBMapper.query(TourDO.class, queryExpression);
                FullTour[] tour_array = new FullTour[10];

                for(int i = 0; i < result.size(); i++)
                    tour_array[i] = new FullTour(result.get(i));

                tourListAdapter.setTourlist(tour_array);
                tourRecyclerView.setAdapter(tourListAdapter);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void initializeRecyclerView() {
        //setting up recyclerView
        tourRecyclerView = (RecyclerView) findViewById(R.id.tour_list);

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


        tourListAdapter = new TourListAdapter(test);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        tourRecyclerView.setAdapter(tourListAdapter);
        tourRecyclerView.setLayoutManager(llm);
        tourRecyclerView.setHasFixedSize(true);
    }
}
