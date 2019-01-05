package jackal.visitr.visitr;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.location.places.AutocompletePrediction;
import com.google.android.gms.location.places.Places;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.LatLngBounds;

import Adapters.PlaceAutocompleteAdapter;
import Adapters.PlaceListAdapter;
import Adapters.TourListAdapter;
import AndroidFactories.MenuFactory;
import Objects.Place;

public class AddPlaceActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{

    Toolbar menuToolbar;
    RecyclerView placeRecyclerView;
    PlaceListAdapter placeListAdapter;
    AutoCompleteTextView placeAutoCompleteTextView;
    GoogleApiClient googleApiClient;
    PlaceAutocompleteAdapter placeAutocompleteAdapter;
    LatLngBounds LAT_LNG_BOUNDS = new LatLngBounds(
            new LatLng(-40, -168), new LatLng(71, 136));

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        initializeMenu();
        initializeRecyclerView();
        initializeAutoCompleteTextView();
    }

    private void initializeAutoCompleteTextView() {
        placeAutoCompleteTextView = (AutoCompleteTextView) findViewById(R.id.placeSearchAutocompleteTextView);

        googleApiClient = new GoogleApiClient
                .Builder(this)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .enableAutoManage(this, this)
                .build();

        placeAutocompleteAdapter = new PlaceAutocompleteAdapter(this, googleApiClient,
                LAT_LNG_BOUNDS, null);

        placeAutoCompleteTextView.setAdapter(placeAutocompleteAdapter);

        placeAutoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                AutocompletePrediction autocompletePrediction = (AutocompletePrediction) parent.getItemAtPosition(position);

                String name = autocompletePrediction.getFullText(null).toString();
                String google_places_id = autocompletePrediction.getPlaceId();

                addPlaceToRecyclerView(name, google_places_id);
            }
        });
    }

    private void addPlaceToRecyclerView(String name, String google_places_id) {

        Place[] adapter_places = ((PlaceListAdapter)placeRecyclerView.getAdapter()).getPlacelist();

        if(adapter_places.length < 10) {
            for(int i = 0; i < adapter_places.length; i++) {
                if(adapter_places[i] == null) {
                    adapter_places[i] = new Place(name, google_places_id);
                    break;
                }
            }
        }

        placeListAdapter = new PlaceListAdapter(adapter_places, this);
        placeRecyclerView.setAdapter(placeListAdapter);
    }

    private void initializeRecyclerView() {
        placeRecyclerView = (RecyclerView) findViewById(R.id.tourStopsRecyclerView);

        Place[] places = new Place[10];

        places[0] = new Place("Cheesecake Factory", "GhjoDo9r");
        places[1] = new Place("New York", "AwqWso72");

        placeListAdapter = new PlaceListAdapter(places, this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        placeRecyclerView.setAdapter(placeListAdapter);
        placeRecyclerView.setLayoutManager(llm);
        placeRecyclerView.setHasFixedSize(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public void initializeMenu() {
        menuToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(menuToolbar);
    }

    public boolean onLocalToursClicked(MenuItem item) {
        MenuFactory.startLocalToursActivity(this);
        finish();
        return(true);
    }

    public boolean onProfileClicked(MenuItem item)
    {
        return false;
    }

    public boolean onYourTourClicked(MenuItem item) {
        MenuFactory.startYourTourActivity(this);
        finish();
        return(true);
    }

    public boolean onCreateClicked(MenuItem item) {
        MenuFactory.startCreateTourActivity(this);
        finish();
        return(true);
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
