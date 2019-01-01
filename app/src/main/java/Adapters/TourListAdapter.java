package Adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import Objects.BaseTour;
import Objects.FullTour;
import jackal.visitr.visitr.R;

public class TourListAdapter extends RecyclerView.Adapter<TourListAdapter.MyViewHolder> {

    private BaseTour[] tourlist;

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

                //code for tour info pop-up goes here

            }
        });

    }

    public BaseTour[] getTourlist() {
        return tourlist;
    }

    public void setTourlist(BaseTour[] tourlist) {
        this.tourlist = tourlist;
    }
}
