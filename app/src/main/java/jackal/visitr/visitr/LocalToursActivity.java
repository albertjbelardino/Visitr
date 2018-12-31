package jackal.visitr.visitr;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.amazonaws.mobile.client.AWSMobileClient;
import com.amazonaws.mobile.client.AWSStartupHandler;
import com.amazonaws.mobile.client.AWSStartupResult;

public class LocalToursActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_local_tours);

        AWSMobileClient.getInstance().initialize(this, new AWSStartupHandler() {
            @Override
            public void onComplete(AWSStartupResult awsStartupResult) {
                Log.d("YourMainActivity", "AWSMobileClient is instantiated and you are connected to AWS!");
            }
        }).execute();

        Toolbar menuToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(menuToolbar);




        //key = AIzaSyCI80Oyo1ovK5M2gk-1WNUQdTvK6Plt3sw

        //test again

    }

    public boolean onLocalToursClicked(MenuItem item)
    {
        return false;
    }

    public boolean onProfileClicked(MenuItem item)
    {
        Intent i = new Intent(this, ProfileActivity.class);
        finish();
        startActivity(i);
        return(true);
    }

    public boolean onYourTourClicked(MenuItem item)
    {
        Intent i = new Intent(this, YourTourActivity.class);
        finish();
        startActivity(i);
        return(true);
    }

    public boolean onCreateClicked(MenuItem item)
    {
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
}
