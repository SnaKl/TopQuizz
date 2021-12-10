package com.neves.topquiz.controller;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.view.LayoutInflater;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.neves.topquiz.model.Theme;

import java.util.List;

public class CustomAdapter extends BaseAdapter  {

    private LayoutInflater flater;
    //private List<Theme> list;
    private List<Theme> list;
    private int listItemLayoutResource;
    private int textViewItemNameId;


    public CustomAdapter(Activity context, int listItemLayoutResource,
                         int textViewItemNameId,
                         List<Theme> list) {
        this.listItemLayoutResource = listItemLayoutResource;

        this.textViewItemNameId = textViewItemNameId;
        this.list = list;
        this.flater = context.getLayoutInflater();
    }

    @Override
    public int getCount() {
        if(this.list == null)  {
            return 0;
        }
        return this.list.size();
    }

    /*@Override
    public Object getItem(int position) {
        return this.list.get(position);
    }*/

    @Override
    public Theme getItem(int position) {
        return this.list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    /*@Override
    public long getItemId(int position) {
        Theme theme = (Theme) this.getItem(position);
        //return language.getId();
        // return position; (Return position if you need).
    }*/

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Theme t = getItem(position);

        View rowView = this.flater.inflate(this.listItemLayoutResource, null,true);

        TextView textViewItemName = (TextView) rowView.findViewById(this.textViewItemNameId);
        textViewItemName.setText(t.getTitle());

        return rowView;
    }


}
