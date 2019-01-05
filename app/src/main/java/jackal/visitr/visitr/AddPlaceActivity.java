package jackal.visitr.visitr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import Adapters.PlaceListAdapter;
import Adapters.TourListAdapter;
import AndroidFactories.MenuFactory;
import Objects.Place;

public class AddPlaceActivity extends AppCompatActivity {

    Toolbar menuToolbar;
    RecyclerView placeRecyclerView;
    PlaceListAdapter placeListAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_place);

        initializeMenu();
        initializeRecyclerView();

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
}
