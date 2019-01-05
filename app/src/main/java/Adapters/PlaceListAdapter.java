package Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import Objects.FullTour;
import Objects.Place;
import jackal.visitr.visitr.LocalToursActivity;
import jackal.visitr.visitr.R;

public class PlaceListAdapter extends RecyclerView.Adapter<PlaceListAdapter.MyViewHolder> {


    private Place[] placelist;
    Context activitycontext;

    public Place[]  getPlacelist() {
        return placelist;
    }

    public void setPlacelist(Place[] placelist) {
        this.placelist = placelist;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView placename;

        public MyViewHolder(TextView v) {
            super(v);
            placename = v;
        }
    }

    public PlaceListAdapter(Place[] placelist, Context activitycontext) {
        this.placelist = placelist;
        this.activitycontext = activitycontext;
    }

    @Override
    public int getItemCount() {
        return placelist.length;
    }



    @Override
    public PlaceListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        TextView v = (TextView) LayoutInflater.from(parent.getContext())
                .inflate(R.layout.tour_list_layout, parent, false);
        PlaceListAdapter.MyViewHolder vh = new PlaceListAdapter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(PlaceListAdapter.MyViewHolder holder, final int position) {
        final int tempposition = position;
        if (placelist[position] != null) {
            holder.placename.setText(placelist[position].getName() + "\n" +
                                                                placelist[position].getGoogle_places_id());
            /*TODO Add/Delete Pop-up?
            holder.placename.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    LocalToursActivity tempcallactivity = (LocalToursActivity) activitycontext;
                    tempcallactivity.makePopUp(view, placelist[position], tempcallactivity);
                }
            });
            */
        }

    }
}

