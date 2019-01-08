package jackal.visitr.visitr;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

import com.amazonaws.mobile.config.AWSConfiguration;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBMapper;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBQueryExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.DynamoDBScanExpression;
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import Adapters.GenreDropdownAdapter;
import Adapters.TourListAdapter;
import AndroidFactories.MenuFactory;
import AndroidFactories.PopupFactory;
import AndroidFactories.PreferenceFactory;
import Mappers.Create;
import Models.TourDO;
import Objects.FullTour;


public class LocalToursActivity extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;
    Spinner genreSpinner;
    Toolbar menuToolbar;
    RecyclerView tourRecyclerView;
    TourListAdapter tourListAdapter;
    private final String START_TOUR_PASS = "temporarypassword";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_tours);

        dynamoDBMapper = Create.inititalizeMapper(this);
        initializeMenu();
        initializeRecyclerView();
        initializeGenreSpinner();
        initializeFullTour();
    }

    public void initializeMenu() {
        menuToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(menuToolbar);
    }


    public void queryGenre(final AdapterView<?> parent, final int position) {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String genre_query = parent.getItemAtPosition(position).toString();

                TourDO tourDO = new TourDO();
                tourDO.set_genre(genre_query);

                Condition rangeKeyCondition = new Condition()
                        .withComparisonOperator(ComparisonOperator.CONTAINS)
                        .withAttributeValueList(new AttributeValue().withS(genre_query));

                DynamoDBQueryExpression<TourDO> queryExpression = new DynamoDBQueryExpression<TourDO>()
                        .withHashKeyValues(tourDO)
                        .withIndexName("GenreIndex")
                        .withConsistentRead(false)
                        .withLimit(10);

                PaginatedList<TourDO> result = dynamoDBMapper.query(TourDO.class, queryExpression);
                final FullTour[] tour_array = new FullTour[result.size()];

                for (int i = 0; i < result.size(); i++)
                    tour_array[i] = new FullTour(result.get(i));

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final FullTour[] final_tour_array = tour_array;
                        tourListAdapter.setTourlist(final_tour_array);
                        tourRecyclerView.setAdapter(tourListAdapter);
                    }
                });
            }
        }).start();
    }

    private void initializeGenreSpinner() {
        genreSpinner = (Spinner) findViewById(R.id.genre_spinner_local_tours);
        genreSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.genre_string_array)));

        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                if(position != 0)
                    queryGenre(parent, position);
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
        genreSpinner.setEnabled(true);
    }

    public void initializeRecyclerView() {
        //setting up recyclerView

        tourRecyclerView = (RecyclerView) findViewById(R.id.tour_list);
        FullTour[] placehold = new FullTour[0];
        tourListAdapter = new TourListAdapter(placehold, this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        tourRecyclerView.setAdapter(tourListAdapter);
        tourRecyclerView.setLayoutManager(llm);
        tourRecyclerView.setHasFixedSize(true);

        new Thread(new Runnable() {
            @Override

            public void run() {

                PaginatedList<TourDO> alltours = dynamoDBMapper.scan(TourDO.class, new DynamoDBScanExpression().withLimit(10));
                final FullTour[] alltoursarray = new FullTour[alltours.size()];
                Log.d("see", Integer.toString(alltours.size()));
                for (int i = 0; i < alltours.size(); i++) {
                    alltoursarray[i] = new FullTour(alltours.get(i));
                }

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        final FullTour[] final_tour_array_all = alltoursarray;
                        tourListAdapter.setTourlist(final_tour_array_all);
                        tourRecyclerView.setAdapter(tourListAdapter);
                    }
                });
            }
        }).start();

    }

    public void makePopUp(View view, FullTour tourfromadapter, final LocalToursActivity currentactivity)
    {
        final FullTour currenttour = tourfromadapter;
        final AlertDialog.Builder alertbuilder = new AlertDialog.Builder(view.getContext());
        alertbuilder.setCancelable(true);
        LayoutInflater layoutinflater = LayoutInflater.from(view.getContext());
        final View popupview = layoutinflater.inflate(R.layout.tourpopup, null);

        /**************************************************/

        PopupFactory.fillTourPopup(popupview, currenttour);

        /****************************************************/

        alertbuilder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
            }
        });

        alertbuilder.setPositiveButton("Start Tour", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                Intent starttourintent = new Intent(currentactivity, YourTourActivity.class);
                starttourintent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
                starttourintent.putExtra(START_TOUR_PASS, currenttour);
                startActivity(starttourintent);
                finish();
            }
        });
        alertbuilder.setView(popupview);
        alertbuilder.create();
        alertbuilder.show();
    }

    public boolean onLocalToursClicked(MenuItem item) {
        return false;
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
        MenuFactory.startCreateTourActivity(this);
        //finish();
        return(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    private void initializeFullTour() {

        if(PreferenceFactory
                .getSavedObjectFromPreference(
                        this, "ApplicationTour",
                        "ApplicationTourKey", FullTour.class) == null)

            PreferenceFactory.saveObjectToSharedPreference(this, "ApplicationTour",
                    "ApplicationTourKey", new FullTour());

    }
































}
