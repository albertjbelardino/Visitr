package jackal.visitr.visitr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import AndroidFactories.MenuFactory;
import Objects.FullTour;

public class YourTourActivity extends AppCompatActivity {

    private final String START_TOUR_PASS = "temporarypassword";
    Toolbar menuToolbar;
    FullTour currenttour;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_tour);
        Bundle tourinfo = this.getIntent().getExtras();
        if(tourinfo != null)
        {
            currenttour = (FullTour)tourinfo.getSerializable(START_TOUR_PASS);
            Toast.makeText(this, "TOUR NAME : " + currenttour.getName(), Toast.LENGTH_SHORT).show();

        }


        initializeMenu();
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
        finish();
        return(true);
    }

    public boolean onProfileClicked(MenuItem item) {
        MenuFactory.startProfileActivity(this);
        finish();
        return(true);
    }

    public boolean onYourTourClicked(MenuItem item)
    {
        return false;
    }

    public boolean onCreateClicked(MenuItem item)
    {
        MenuFactory.startCreateTourActivity(this);
        finish();
        return(true);
    }
}
