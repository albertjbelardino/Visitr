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

public class PlaceInfoListAdapter  extends RecyclerView.Adapter<PlaceInfoListAdapter.MyViewHolder> {

    private Place[] placelist;
    Context activitycontext;

    public Place[] getPlacelist() {
        return placelist;
    }

    public void setPlacelist(Place[] placelist) {
        this.placelist = placelist;
    }


    public static class MyViewHolder extends RecyclerView.ViewHolder {
        public TextView placename;
        public TextView placedescription;
        public View placeview;
        public MyViewHolder(View v) {

            super(v);
            placeview = v;
            placename = placeview.findViewById(R.id.placeName);
            placedescription = placeview.findViewById(R.id.placeDescription);
        }
    }

    public PlaceInfoListAdapter(Place[] placelist, Context activitycontext)
    {
        this.placelist = placelist;
        this.activitycontext = activitycontext;
    }

    @Override
    public int getItemCount() {
        return placelist.length;
    }




    @Override
    public PlaceInfoListAdapter.MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.place_info_layout, parent, false);
        PlaceInfoListAdapter.MyViewHolder vh = new PlaceInfoListAdapter.MyViewHolder(v);
        return vh;

    }

    @Override
    public void onBindViewHolder(PlaceInfoListAdapter.MyViewHolder holder, final int position) {
        final int tempposition = position;
        if(placelist[position] != null) {
            holder.placename.setText(placelist[position].getName());
            holder.placedescription.setText(placelist[position].getDescription());
        }

    }


}
