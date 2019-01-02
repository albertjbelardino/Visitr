package Adapters;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import Objects.BaseTour;
import Objects.FullTour;
import jackal.visitr.visitr.FullInfoTour;
import jackal.visitr.visitr.R;

import static jackal.visitr.visitr.FullInfoTour.type.WALKING;

public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.MyViewHolder> {


    private BaseTour[] tourlist;

    public BaseTour[] getTourlist() {
        return tourlist;
    }

    public void setTourlist(BaseTour[] tourlist) {
        this.tourlist = tourlist;
    }
    

    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tourname;
        public MyViewHolder(TextView v) {

            super(v);
            tourname = v;
        }
    }

    public TourListAdapter(BaseTour[] tourlist)
    {
        this.tourlist = tourlist;
    }

    @Override
    public int getItemCount() {
        return tourlist.length;
    }




    @Override
    public TourListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tour_list_layout, parent, false);
        MyViewHolder vh = new MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        final int tempposition = position;
        holder.tourname.setText(tourlist[position].name + "\n" + tourlist[position].city);
        holder.tourname.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {

                Toast.makeText(view.getContext(), "ID : " + tourlist[tempposition].ID, Toast.LENGTH_SHORT).show();

                final AlertDialog.Builder ab = new AlertDialog.Builder(view.getContext());
                ab.setCancelable(true);
                LayoutInflater li = LayoutInflater.from(view.getContext());
                final View popupview = li.inflate(R.layout.tourpopup, null);

                TextView tourname = (TextView)popupview.findViewById(R.id.tourName);
                TextView genre = (TextView)popupview.findViewById(R.id.genre);
                TextView totaltime = (TextView)popupview.findViewById(R.id.totalTime);
                TextView tourtype = (TextView)popupview.findViewById(R.id.tourType);
                TextView numstop = (TextView)popupview.findViewById(R.id.numStops);
                TextView keywords = (TextView)popupview.findViewById(R.id.keywords);
                TextView tourdescription = (TextView)popupview.findViewById(R.id.tourdesciption);
                Button exitbutton = (Button)popupview.findViewById(R.id.button);

                //get tour info from server here
                String[] t = new String[3];
                t[0] = "test";
                t[1] = "why";
                t[2] = "work";
                FullInfoTour test = new FullInfoTour(1, "testtour", "testville", "testing", 10, 3, t, "This will be a desciption of the tour, it will probably be pretty long.", WALKING);

                tourname.setText(test.name);
                genre.setText("Genre: " + test.genre);
                totaltime.setText("Time Estimate: " + Integer.toString(test.totaltime) + " minutes");
                tourtype.setText("Type: " + test.thisType.toString());
                numstop.setText("# Stops: : " + Integer.toString(test.numstops));
                String keywordtotal = "";
                for(String s : test.keywords)
                {
                    keywordtotal = keywordtotal + s + ", ";
                }
                keywords.setText(keywordtotal);
                tourdescription.setText(test.tourdescription);

                ab.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        dialogInterface.dismiss();
                    }
                });
                ab.setView(popupview);
                ab.create();
                ab.show();





            }
        });

    }


}
