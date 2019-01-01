package Adapters;

import android.content.Context;
import android.database.DataSetObserver;
import android.graphics.Typeface;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import jackal.visitr.visitr.R;

public class GenreDropdownAdapter extends ArrayAdapter<String> {

    private Context context;
    private String[] genre_list;
    private static final String[] FINAL_genre_list = new String[]{"Food", "Sports", "Art"};

    public GenreDropdownAdapter(@NonNull Context context, String[] list) {
        super(context, 0, list);
        this.context = context;
        this.genre_list = list;
    }

    public GenreDropdownAdapter(@NonNull Context context) {
        super(context, 0, FINAL_genre_list);
    }

    public GenreDropdownAdapter(@NonNull Context context, int resource) {
        super(context, resource, FINAL_genre_list);
        setDropDownViewResource(resource);
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View v = super.getDropDownView(position, convertView, parent);

        return v;
    }

}
