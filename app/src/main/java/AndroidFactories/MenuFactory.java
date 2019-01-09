package AndroidFactories;

import android.content.Context;
import android.content.Intent;

import jackal.visitr.visitr.AddPlaceActivity;
import jackal.visitr.visitr.CreateTourActivity;
import jackal.visitr.visitr.LocalToursActivity;
import jackal.visitr.visitr.LoginActivity;
import jackal.visitr.visitr.ProfileActivity;
import jackal.visitr.visitr.YourTourActivity;

public class MenuFactory {

    public static void startLocalToursActivity(Context context) {
        Intent i = new Intent(context, LocalToursActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(i);
    }

    public static void startCreateTourActivity(Context context) {
        Intent i = new Intent(context, CreateTourActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(i);
    }

    public static void startProfileActivity(Context context) {
        Intent i = new Intent(context, ProfileActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(i);
    }

    public static void startYourTourActivity(Context context) {
        Intent i = new Intent(context, YourTourActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(i);
    }

    public static void startAddPlaceActivity(Context context) {
        Intent i = new Intent(context, AddPlaceActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(i);
    }

    public static void startLoginActivity(Context context) {
        Intent i = new Intent(context, LoginActivity.class);
        i.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        context.startActivity(i);
    }

}
