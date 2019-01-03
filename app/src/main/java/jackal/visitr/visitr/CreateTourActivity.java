package jackal.visitr.visitr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.gson.Gson;

import AndroidFactories.MenuFactory;
import AndroidFactories.PreferenceFactory;
import Mappers.Create;
import Models.IndividualReviewDO;
import Models.TourDO;
import Objects.FullTour;

public class CreateTourActivity extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;
    Toolbar menuToolbar;
    EditText tourNameEditText, tourLocationEditText, tourDescriptionEditText;
    Spinner genreSpinner;
    String genreString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);

        initializeTextViews();
        inititalizeMapper();
        initializeGenreSpinner();
        initializeMenu();
    }

    private void initializeGenreSpinner() {
        genreSpinner = (Spinner) findViewById(R.id.genre_spinner_create_tour);
        genreSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.genre_string_array)));
        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                genreString = parent.getItemAtPosition(position).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    @Override
    protected void onStop() {

        FullTour tour = PreferenceFactory.getSavedObjectFromPreference(this,
                getResources().getString(R.string.ApplicationTour),
                getResources().getString(R.string.ApplicationTourKey), FullTour.class);

        tour.setName(tourNameEditText.getText().toString());
        tour.setGoogle_city_id(tourLocationEditText.getText().toString());
        tour.setDescription(tourDescriptionEditText.getText().toString());
        tour.setGenre(genreString);

        PreferenceFactory.saveObjectToSharedPreference(this,
                getResources().getString(R.string.ApplicationTour),
                getResources().getString(R.string.ApplicationTourKey), tour);

        super.onStop();
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

        tourNameEditText        = (EditText) findViewById(R.id.tourNameEditText);
        tourLocationEditText    = (EditText) findViewById(R.id.tourLocationEditText);
        tourDescriptionEditText = (EditText) findViewById(R.id.tourDescriptionEditText);

        FullTour fullTour = PreferenceFactory.getSavedObjectFromPreference(this,
                getResources().getString(R.string.ApplicationTour),
                getResources().getString(R.string.ApplicationTourKey), FullTour.class);

        if(fullTour != null) {
            if(fullTour.getName() != null) {
                tourNameEditText.setText(fullTour.getName());
            }
            if(fullTour.getDescription() != null) {
                tourDescriptionEditText.setText(fullTour.getDescription());
            }
            if(fullTour.getGoogle_city_id() != null) {
                tourLocationEditText.setText(fullTour.getGoogle_city_id());
            }
            if(fullTour.getGenre() != null) {
                //TODO: make it so that the genre spinner returns to its dropdown position
                //TODO: i.e. : change genre while making a tour, on return don't return to default position
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onLocalToursClicked(MenuItem item) {
        MenuFactory.startLocalToursActivity(this);
        //finish();
        return true;
    }

    public boolean onProfileClicked(MenuItem item) {
        MenuFactory.startProfileActivity(this);
        //finish();
        return(true);
    }

    public boolean onYourTourClicked(MenuItem item) {
        MenuFactory.startYourTourActivity(this);
        //finish();
        return(true);
    }

    public boolean onCreateClicked(MenuItem item) {
        return false;
    }
}
