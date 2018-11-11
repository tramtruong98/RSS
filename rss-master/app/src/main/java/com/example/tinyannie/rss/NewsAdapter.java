package com.example.tinyannie.rss;

import android.content.Context;
import android.net.LinkAddress;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class NewsAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<dongNews> dongNewsList;

    public NewsAdapter(Context context, int layout, List<dongNews> dongNewsList) {
        this.context = context;
        this.layout = layout;
        this.dongNewsList = dongNewsList;
    }



    public int getCount() {
        return dongNewsList.size();

    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater inflater= (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        convertView =inflater.inflate(layout,null);
        TextView view_link= convertView.findViewById(R.id.view_link);
        TextView view_title=convertView.findViewById(R.id.view_title);
        ImageView view_image=convertView.findViewById(R.id.view_img);
        dongNews dongNews=dongNewsList.get(position);
        view_link.setText(dongNews.getLink());
        view_title.setText(dongNews.getTitle());
        view_image.setImageResource(dongNews.getImage());
        return convertView;
    }
}
