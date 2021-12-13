package com.neves.topquiz.controller;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import com.neves.topquiz.model.Theme;

import java.util.ArrayList;
import java.util.List;

public class CustomAdapter extends BaseAdapter implements Filterable {

    private final LayoutInflater flater;
    private List<Theme> list;
    private final List<Theme> temp;
    private final List<Theme> suggestions;
    private final int listItemLayoutResource;
    private final int textViewItemNameId;


    public CustomAdapter(Activity context, int listItemLayoutResource,
                         int textViewItemNameId,
                         List<Theme> list) {

        this.listItemLayoutResource = listItemLayoutResource;
        this.textViewItemNameId = textViewItemNameId;
        this.list = list;
        this.temp = new ArrayList<>(list);
        this.suggestions = new ArrayList<>();
        this.flater = context.getLayoutInflater();
    }

    @Override
    public Filter getFilter() {
        return nameFilter;
    }

    Filter nameFilter = new Filter() {
        public String convertResultToString(Object resultValue) {
            String str = ((Theme) (resultValue)).getTitle();
            return str;
        }

        @Override
        protected FilterResults performFiltering(CharSequence constraint) {
            if (constraint != null) {
                suggestions.clear();
                for (Theme theme : temp) {
                    if (theme.getTitle().toLowerCase()
                            .startsWith(constraint.toString().toLowerCase())) {
                        suggestions.add(theme);
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = suggestions;
                filterResults.count = suggestions.size();
                return filterResults;
            } else {
                return new FilterResults();
            }
        }

        @Override
        protected void publishResults(CharSequence constraint, FilterResults results) {
            List<Theme> filteredList = (List<Theme>) results.values;
            if (results != null && results.count > 0) {
                list.clear();
                for (Theme t : filteredList) {
                    list.add(t);
                    notifyDataSetChanged();
                }

            }
        }
    };

    @Override
    public int getCount() {
        if (this.list == null) {
            return 0;
        }
        return this.list.size();
    }

    public void updateList(List<Theme> newlist) {
        list = newlist;
        this.notifyDataSetChanged();
    }

    @Override
    public Theme getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Theme t = getItem(position);

        View rowView = this.flater.inflate(this.listItemLayoutResource, null, true);

        TextView textViewItemName = rowView.findViewById(this.textViewItemNameId);
        textViewItemName.setText(t.getTitle());

        return rowView;
    }

}
