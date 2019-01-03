package jackal.visitr.visitr;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import AndroidFactories.MenuFactory;
import AndroidFactories.PopupFactory;
import Objects.FullTour;

public class YourTourActivity extends AppCompatActivity {

    private final String START_TOUR_PASS = "temporarypassword";
    Toolbar menuToolbar;
    FullTour currenttour;
    boolean paused;
    AlertDialog shownPopup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_tour);
        Bundle tourinfo = this.getIntent().getExtras();
        if(tourinfo != null)
        {
            currenttour = (FullTour)tourinfo.getSerializable(START_TOUR_PASS);
            //Toast.makeText(this, "TOUR NAME : " + currenttour.getName(), Toast.LENGTH_SHORT).show();
            TextView tourname = this.findViewById(R.id.currentTourName);
            tourname.setText(currenttour.getName());

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
        final AlertDialog.Builder alertbuilder = new AlertDialog.Builder(this);
        alertbuilder.setCancelable(true);
        LayoutInflater layoutinflater = LayoutInflater.from(this);
        final View popupview = layoutinflater.inflate(R.layout.stoptour_popup, null);
        alertbuilder.setView(popupview);
        alertbuilder.create();
        shownPopup = alertbuilder.show();


    }

    public void onPauseButtonClicked(View view)
    {
        if(paused)
        {

        }
        else
        {

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
}
