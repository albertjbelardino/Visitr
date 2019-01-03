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
import com.amazonaws.mobileconnectors.dynamodbv2.dynamodbmapper.PaginatedList;
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClient;
import com.amazonaws.services.dynamodbv2.model.AttributeValue;
import com.amazonaws.services.dynamodbv2.model.ComparisonOperator;
import com.amazonaws.services.dynamodbv2.model.Condition;

import java.util.HashSet;
import java.util.Set;

import Adapters.GenreDropdownAdapter;
import Adapters.TourListAdapter;
import Models.TourDO;
import Objects.FullTour;


public class LocalToursActivity extends AppCompatActivity {

    DynamoDBMapper dynamoDBMapper;
    Spinner genreSpinner;
    Toolbar menuToolbar;
    RecyclerView tourRecyclerView;
    TourListAdapter tourListAdapter;
    private final String START_TOUR_PASS = "temporarypassword";

    //genre dropdown menu
    //on dropdown menu click, query database for that genre, parse array result, create new tour list adapter, put new adapter in recycler view

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_tours);

        inititalizeMapper();
        initializeMenu();
        initializeRecyclerView();
        initializeGenreSpinner();
    }

    public void initializeMenu() {
        menuToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(menuToolbar);
    }

    //menu onClick functions

    public boolean onLocalToursClicked(MenuItem item) {
        return false;
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
        Intent i = new Intent(this, CreateTourActivity.class);

        startActivity(i);
        finish();
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
                        .withConsistentRead(false);

                PaginatedList<TourDO> result = dynamoDBMapper.query(TourDO.class, queryExpression);
                final FullTour[] tour_array = new FullTour[10];

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
        genreSpinner = (Spinner) findViewById(R.id.genre_spinner);
        genreSpinner.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_dropdown_item_1line,
                getResources().getStringArray(R.array.genre_string_array)));

        genreSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                queryGenre(parent, position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    public void initializeRecyclerView() {
        //setting up recyclerView
        tourRecyclerView = (RecyclerView) findViewById(R.id.tour_list);

        FullTour[] test = new FullTour[2];
        Set<String> t = new HashSet<String>();
        t.add("this");
        t.add("testword");
        t.add("wow");

        FullTour testing1 = new FullTour(new TourDO());
        testing1.setName("Setting name");
        testing1.setID(1);
        testing1.setCity("place");
        testing1.setDescription("This us the first test description.");
        testing1.setGenre("testgenre");
        testing1.setKeywords(t);
        testing1.setGoogle_city_id("the city");
        testing1.setRating(5);
        testing1.setTotal_time(12);

        test[0] = testing1;

        FullTour testing2 = new FullTour(new TourDO());
        testing2.setID(2);
        testing2.setName("test2 with name");
        testing2.setCity("this is a city");
        testing2.setDescription("This us the second test description.");
        testing2.setGenre("testgenre2");
        testing2.setKeywords(t);
        testing2.setGoogle_city_id("the city two");
        testing2.setRating(2);
        testing2.setTotal_time(18);

        test[1] = testing2;

        tourListAdapter = new TourListAdapter(test, this);
        LinearLayoutManager llm = new LinearLayoutManager(this);
        tourRecyclerView.setAdapter(tourListAdapter);
        tourRecyclerView.setLayoutManager(llm);
        tourRecyclerView.setHasFixedSize(true);
    }

    public void makePopUp(View view, FullTour tourfromadapter, final LocalToursActivity currentactivity)
    {

        final FullTour currenttour = tourfromadapter;

        final AlertDialog.Builder alertbuilder = new AlertDialog.Builder(view.getContext());
        alertbuilder.setCancelable(true);
        LayoutInflater layoutinflater = LayoutInflater.from(view.getContext());
        final View popupview = layoutinflater.inflate(R.layout.tourpopup, null);

        TextView tourname = (TextView)popupview.findViewById(R.id.tourName);
        TextView genre = (TextView)popupview.findViewById(R.id.genre);
        TextView totaltime = (TextView)popupview.findViewById(R.id.totalTime);
        TextView tourcity = (TextView)popupview.findViewById(R.id.tourCity);
        TextView rating = (TextView)popupview.findViewById(R.id.rating);
        TextView keywords = (TextView)popupview.findViewById(R.id.keywords);
        TextView tourdescription = (TextView)popupview.findViewById(R.id.tourdesciption);
        Button exitbutton = (Button)popupview.findViewById(R.id.button);

        //get tour info from server here

        if(currenttour.getName() != null) tourname.setText(currenttour.getName());
        if(currenttour.getGenre() != null)genre.setText("Genre: " + currenttour.getGenre());
        if(currenttour.getTotal_time() != null) totaltime.setText("Time Estimate: " + Double.toString(currenttour.getTotal_time()) + " minutes");
        if(currenttour.getCity() != null)tourcity.setText("City: " + currenttour.getCity());
        if(currenttour.getRating() != null)  rating.setText("Rating: : " + Double.toString(currenttour.getRating()));
        if(currenttour.getKeywords() != null) {
            String keywordtotal = "";
            for (String s : currenttour.getKeywords()) {
                keywordtotal = keywordtotal + (String) s + ", ";
            }
            keywords.setText(keywordtotal);
        }
        if(currenttour.getDescription() != null) tourdescription.setText(currenttour.getDescription());

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
                starttourintent.putExtra(START_TOUR_PASS, currenttour);
                startActivity(starttourintent);
                finish();
            }
        });
        alertbuilder.setView(popupview);
        alertbuilder.create();
        alertbuilder.show();
    }
}
