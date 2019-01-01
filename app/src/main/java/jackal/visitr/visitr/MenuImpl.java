package jackal.visitr.visitr;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;


public class MenuImpl extends AppCompatActivity {

    public Context context;

    public MenuImpl(Context context) {
        this.context = context;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_menu, menu);
        return true;
    }

    public boolean onLocalToursClicked(MenuItem item)
    {
        Intent i = new Intent(context, LocalToursActivity.class);
        finish();
        startActivity(i);
        return true;
    }

    public boolean onProfileClicked(MenuItem item)
    {
        Intent i = new Intent(context, ProfileActivity.class);
        finish();
        startActivity(i);
        return(true);
    }

    public boolean onYourTourClicked(MenuItem item)
    {
        Intent i = new Intent(context, YourTourActivity.class);
        finish();
        startActivity(i);
        return(true);
    }

    public boolean onCreateClicked(MenuItem item)
    {
        return false;
    }
}
