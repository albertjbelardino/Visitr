package jackal.visitr.visitr;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;
import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;
import com.google.gson.Gson;
import com.google.maps.PlacesApi;

import java.util.ArrayList;

import Adapters.PlaceAutocompleteAdapter;
import AndroidFactories.MenuFactory;
import AndroidFactories.PreferenceFactory;
import Mappers.Create;
import Models.IndividualReviewDO;
import Models.TourDO;
import Objects.FullTour;
import Objects.Place;

public class CreateTourActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    DynamoDBMapper dynamoDBMapper;
    Toolbar menuToolbar;
    EditText tourNameEditText, tourDescriptionEditText;
    AutoCompleteTextView tourLocationAutoCompleteTextView;
    Spinner genreSpinner;
    String genreString;
    GoogleApiClient googleApiClient;
    PlaceAutocompleteAdapter placeAutocompleteAdapter;
    LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));
    private String city_name;
    private String google_places_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);

        initializeAutoCompleteTextViews();
        initializeTextViews();
        inititalizeMapper();
        initializeGenreSpinner();
        initializeMenu();
    }

    @Override
    protected void onResume() {
        initializeTextViews();
        super.onResume();
    }

    private void initializeAutoCompleteTextViews() {

        tourLocationAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.tourLocationAutoCompleteTextView);

        googleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(this, googleApiClient,
                LAT_LNG_BOUNDS, null);

        tourLocationAutoCompleteTextView.setAdapter(placeAutocompleteAdapter);

        tourLocationAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutocompletePrediction autocompletePrediction = (AutocompletePrediction) parent.getItemAtPosition(position);

                city_name = autocompletePrediction.getFullText(null).toString();
                google_places_id = autocompletePrediction.getPlaceId();
            }
        });
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
        tour.setDescription(tourDescriptionEditText.getText().toString());
        tour.setGenre(genreString);
        tour.setGoogle_city_id(google_places_id);
        tour.setCity(city_name);

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
        tourDescriptionEditText = (EditText) findViewById(R.id.tourDescriptionEditText);

        FullTour fullTour = PreferenceFactory.getSavedObjectFromPreference(this,
                getResources().getString(R.string.ApplicationTour),
                getResources().getString(R.string.ApplicationTourKey), FullTour.class);

        if(fullTour != null) {
            if(fullTour.getName() != null) {
                tourNameEditText.setText(fullTour.getName());
            }
            if(fullTour.getDescription() != null) {
                tourDescriptionEditText.setText(fullTour.getGoogle_city_id());
            }
            if(fullTour.getGoogle_city_id() != null) {
                tourLocationAutoCompleteTextView.setText(fullTour.getCity());
            }
            if(fullTour.getGenre() != null) {
                //TODO: make it so that the genre spinner returns to its dropdown position
                //TODO: i.e. : change genre while making a tour, on return don't return to default position
            }
        }

        Toast.makeText(this, fullTour.getGoogle_city_id(),Toast.LENGTH_LONG).show();
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
        //TODO change back when nav menu added
        MenuFactory.startAddPlaceActivity(this);
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

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
