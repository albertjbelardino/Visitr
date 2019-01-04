package Adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import Objects.Place;
import jackal.visitr.visitr.R;

public class LocationSuggestionAdapter extends ArrayAdapter {

    private Place[] places;
    private Context activitycontext;
    private int resourceId;
    private static final int DROPDOWN_ITEM_LAYOUT_ID = R.layout.autocomplete_suggestions_item;

    public LocationSuggestionAdapter(Context context, int resource, Place[] places) {
        super(context, resource, places);
        this.activitycontext = context;
        this.resourceId = resource;
        this.places = places;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View view, @NonNull ViewGroup parent) {
        if (view == null) {
            view = LayoutInflater.from(parent.getContext())
                    .inflate(DROPDOWN_ITEM_LAYOUT_ID, parent, false);
        }

        TextView strName = (TextView) view.findViewById(R.id.suggestionsItemTextView);
        strName.setText(places[position].getCity_name());
        return view;
    }

    @Nullable
    @Override
    public Place getItem(int position) {
        return places[position];
    }


}
