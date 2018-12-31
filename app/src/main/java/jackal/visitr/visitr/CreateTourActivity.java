package jackal.visitr.visitr;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class CreateTourActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_tour);

        Toolbar menuToolbar = (Toolbar) findViewById(R.id.menu_toolbar);
        setSupportActionBar(menuToolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onLocalToursClicked(MenuItem item)
    {
        Intent i = new Intent(this, LocalToursActivity.class);
        startActivity(i);
        return true;
    }

    public boolean onProfileClicked(MenuItem item)
    {
        Intent i = new Intent(this, ProfileActivity.class);
        startActivity(i);
        return(true);
    }

    public boolean onYourTourClicked(MenuItem item)
    {
        Intent i = new Intent(this, YourTourActivity.class);
        startActivity(i);
        return(true);
    }

    public boolean onCreateClicked(MenuItem item)
    {
        return false;
    }
}
