package jackal.visitr.visitr;

import android.Manifest;
import android.app.AlertDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.location.Criteria;
import android.location.Location;
import android.location.LocationManager;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.CameraUpdate;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.model.LatLng;


import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Logger;

import AndroidFactories.MenuFactory;
import Objects.FullTour;

public class YourTourActivity extends AppCompatActivity implements OnMapReadyCallback {

    private final String START_TOUR_PASS = "temporarypassword";
    Toolbar menuToolbar;
    FullTour currenttour;
    Button pauseresumebutton;
    boolean paused;
    boolean arrived;
    boolean firstpress;
    boolean tourfinished;
    AlertDialog shownPopup;
    private GoogleMap currentmap;
    LocationManager locationmanager;
    int currentlocation;
    int previouslocation;
    int nextlocation;
    int currentlocationindex;
    TextView nextstop;
    TextView previousstop;
    TextView currentstop;
    TextView timeview;
    Timer tourtime;
    int seconds;
    int minutes;
    TourTimerTask timertask;


    class TourTimerTask extends TimerTask
    {

        @Override
        public void run() {
            runOnUiThread(tour_timer_incriment);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_tour);
        Bundle tourinfo = this.getIntent().getExtras();
        if(tourinfo != null) {
            currenttour = (FullTour)tourinfo.getSerializable(START_TOUR_PASS);
            TextView tourname = this.findViewById(R.id.currentTourName);
            tourname.setText(currenttour.getName());
            nextstop = findViewById(R.id.nextStop);
            currentstop = findViewById(R.id.currentStop);
            previousstop = findViewById(R.id.lastStop);
            timeview = findViewById(R.id.tourtimer);
            pauseresumebutton = findViewById(R.id.resumeButton);

            nextlocation = currenttour.getPlaces().get(0);
            nextstop.setText(Integer.toString(nextlocation));
            arrived = true;
            firstpress = true;
            tourfinished = false;
            paused = false;


            seconds = 0;
            minutes = 0;
            tourtime = new Timer();
            startTimer();

            getGoogleMapReady();
        }
        initializeMenu();
    }


    @Override
    protected void onSaveInstanceState(Bundle outState) {

        super.onSaveInstanceState(outState);
        outState.putSerializable(START_TOUR_PASS, currenttour);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

        super.onRestoreInstanceState(savedInstanceState);
        Toast.makeText(this, "RESTORED", Toast.LENGTH_SHORT).show();
        if(savedInstanceState != null)
        {
            Toast.makeText(this, "RESTORED FOR REAL", Toast.LENGTH_SHORT).show();
            currenttour = (FullTour)savedInstanceState.getSerializable(START_TOUR_PASS);
            TextView tourname = this.findViewById(R.id.currentTourName);
            tourname.setText(currenttour.getName());
        }
    }

    public void onStopClicked(View view)
    {
        showStopTourDialogue(true);
    }

    void showStopTourDialogue(boolean cancelable)
    {
        final AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);
        alertbuilder.setCancelable(true);
        LayoutInflater layoutinflater = LayoutInflater.from(this);
        final View popupview = layoutinflater.inflate(R.layout.stoptour_popup, null);
        alertbuilder.setCancelable(cancelable);
        Button no = popupview.findViewById(R.id.no);
        if(!cancelable)
        {
            float d = (float)(0.25);
            no.setAlpha(d);
        }
        no.setClickable(cancelable);
        alertbuilder.setView(popupview);
        alertbuilder.create();
        shownPopup = alertbuilder.show();
    }

    public void onPauseButtonClicked(View view)
    {
        if(!paused)
        {
            paused = true;
            timertask.cancel();
            pauseresumebutton.setText("RESUME");
        }
        else
        {
            paused = false;
            startTimer();
            pauseresumebutton.setText("PAUSE");
        }
    }

    public void onNextButtonClicked(View view)
    {
        if(currenttour != null) {
            if (firstpress) {
                arrived = true;
                firstpress = false;
                currentlocation = currenttour.getPlaces().get(0);
                currentstop.setText(Integer.toString(currentlocation));
                nextlocation = currenttour.getPlaces().get(1);
                nextstop.setText(Integer.toString(nextlocation));
                currentlocationindex = 0;
                //the person is at the first location
            } else if (!arrived) {
                arrived = true;
                currentlocationindex = currentlocationindex + 1;
                currentlocation = currenttour.getPlaces().get(currentlocationindex);
                currentstop.setText(Integer.toString(currentlocation));
                if(!((currentlocationindex + 1) >= currenttour.getPlaces().size())) {
                    nextlocation = currenttour.getPlaces().get(currentlocationindex + 1);
                    nextstop.setText(Integer.toString(nextlocation));
                }
                else
                {
                    tourfinished = true;
                    nextstop.setText("");
                }

                //the person is at a location

            } else if(arrived){
                arrived = false;
                if(tourfinished)
                {
                    showStopTourDialogue(false);
                }
                else {
                    previouslocation = currentlocation;
                    previousstop.setText(Integer.toString(previouslocation));
                    nextlocation = currenttour.getPlaces().get(currentlocationindex + 1);
                    nextstop.setText(Integer.toString(nextlocation));

                    currentstop.setText("");
                }

                //the person is leaving a location
                //previous is current, next is current + 1

            }
        }
    }

    public void onStopYesClicked(View view)
    {
        shownPopup.dismiss();
        finish();
        MenuFactory.startLocalToursActivity(this);
    }

    public void onStopNoClicked(View view)
    {
        if(shownPopup != null)
        {
            shownPopup.dismiss();
            shownPopup = null;
        }
    }

    public void onStopReviewClicked(View view)
    {

    }

    public void startTimer()
    {
        timertask = new TourTimerTask();
        tourtime.schedule(timertask, 1000, 1000);
    }

    private void initializeMenu() {
        menuToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(menuToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onLocalToursClicked(MenuItem item) {
        MenuFactory.startLocalToursActivity(this);

       // finish();
        return(true);
    }

    public boolean onProfileClicked(MenuItem item) {
        MenuFactory.startProfileActivity(this);

        //finish();
        return(true);
    }

    public boolean onYourTourClicked(MenuItem item)
    {
        return false;
    }

    public boolean onCreateClicked(MenuItem item)
    {
        MenuFactory.startCreateTourActivity(this);
        //finish();
        return(true);
    }

    public void getGoogleMapReady()
    {
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);
    }

    @Override
    public void onMapReady(GoogleMap googleMap) {
        currentmap = googleMap;
        currentmap.getUiSettings().setMyLocationButtonEnabled(false);

        centerMap();
    }

    public void centerMap()
    {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED)
        {

            currentmap.setMyLocationEnabled(true);
            Criteria criteria = new Criteria();
            locationmanager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
            String provider = locationmanager.getBestProvider(criteria, false);
            Location location = locationmanager.getLastKnownLocation(provider);
            double lat = location.getLatitude();
            double lng = location.getLongitude();
            LatLng coordinate = new LatLng(lat, lng);
            CameraUpdate positioncamera = CameraUpdateFactory.newLatLng(coordinate);

            currentmap.moveCamera(positioncamera);

            CameraUpdate zoom = CameraUpdateFactory.zoomTo(15);

            currentmap.moveCamera(zoom);
        }
    }

    private Runnable tour_timer_incriment = new Runnable() {
        public void run() {
            seconds = seconds + 1;
            if(seconds >= 60)
            {
                minutes = minutes + 1;
                seconds = 0;
            }
            timeview.setText("Min: " + Integer.toString(minutes) + " Sec: " + Integer.toString(seconds));

        }
    };
}
