package Adapters;

import android.app.AlertDialog;
import android.content.Context;
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
import jackal.visitr.visitr.LocalToursActivity;
import jackal.visitr.visitr.R;


public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.MyViewHolder> {


    private FullTour[] tourlist;
    Context activitycontext;

    public FullTour[] getTourlist() {
        return tourlist;
    }

    public void setTourlist(FullTour[] tourlist) {
        this.tourlist = tourlist;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView tourname;
        public MyViewHolder(TextView v) {

            super(v);
            tourname = v;
        }
    }

    public TourListAdapter(FullTour[] tourlist, Context activitycontext)
    {
        this.tourlist = tourlist;
        this.activitycontext = activitycontext;
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
    public void onBindViewHolder(MyViewHolder holder, final int position) {
        final int tempposition = position;
        holder.tourname.setText(tourlist[position].getName() + "\n" + tourlist[position].getCity());
        holder.tourname.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View view) {
                LocalToursActivity tempcallactivity = (LocalToursActivity)activitycontext;
                tempcallactivity.makePopUp(position, view, tourlist, tempcallactivity);
            }
        });

    }




}
