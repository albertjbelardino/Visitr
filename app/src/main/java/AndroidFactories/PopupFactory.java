package AndroidFactories;

import android.view.View;
import android.widget.TextView;

import Objects.FullTour;
import jackal.visitr.visitr.R;

public class PopupFactory {


    public static void fillTourPopup(View popupview, FullTour currenttour) {
        TextView tourname = (TextView)popupview.findViewById(R.id.tourName);
        TextView genre = (TextView)popupview.findViewById(R.id.genre);
        TextView totaltime = (TextView)popupview.findViewById(R.id.totalTime);
        TextView tourcity = (TextView)popupview.findViewById(R.id.tourCity);
        TextView rating = (TextView)popupview.findViewById(R.id.rating);
        TextView keywords = (TextView)popupview.findViewById(R.id.keywords);
        TextView tourdescription = (TextView)popupview.findViewById(R.id.tourdesciption);

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
    }
}
