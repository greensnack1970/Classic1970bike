package com.example.gr33nsn4ck.classic1970bike;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import com.squareup.picasso.Picasso;
import java.util.ArrayList;
import java.util.HashMap;

class CustomAdapter_MortorcycleList extends BaseAdapter {

    ArrayList<String> id = new ArrayList<String>();
    ArrayList<String> topic = new ArrayList<String>();
    ArrayList<String> price = new ArrayList<String>();
    ArrayList<String> datetime = new ArrayList<String>();
    ArrayList<String> path_file = new ArrayList<String>();
    ArrayList<String> image_0 = new ArrayList<String>();

    Context context;
    LayoutInflater inflater;
    //ArrayList<HashMap<String, String>> feedList;

    public CustomAdapter_MortorcycleList(Context context,
                                         ArrayList<String> id,
                                         ArrayList<String> topic,
                                         ArrayList<String> price,
                                         ArrayList<String> datetime,
                                         ArrayList<String> path_file,
                                         ArrayList<String> image_0) {
        this.context = context;
        this.inflater = LayoutInflater.from(context);
        this.id = id;
        this.topic = topic;
        this.price = price;
        this.datetime = datetime;
        this.path_file = path_file;
        this.image_0 = image_0;
    }

    @Override
    public int getCount() {
        return id.size();
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


        ViewHolder holder = null;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_listview_timeline, null);

            holder = new ViewHolder();
            holder.rightTextViewTopic = (TextView) convertView.findViewById(R.id.rightTextViewTopic);
            holder.leftImageView = (ImageView) convertView.findViewById(R.id.leftImageView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.rightTextViewTopic.setText(topic.get(position)+"\n"+
                                            "ราคา :"+price.get(position)+"\n\n"+
                                            datetime.get(position));
        String img_url = "http://trymycmh.16mb.com/sell_image/" + path_file.get(position) + "/" + image_0.get(position);
        Picasso.with(this.context).load(img_url).into(holder.leftImageView);

        return convertView;
    }

    private class ViewHolder {
        TextView rightTextViewTopic;
        ImageView leftImageView;
    }

}
