package com.example.gr33nsn4ck.classic1970bike;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;


public class CustomAdapter_Gridview_1img extends BaseAdapter {

    private Context icontext;
    private LayoutInflater inflater;
    private int[] int_image_list;
    private String[] str_image_list;
    private int img_count;


    public CustomAdapter_Gridview_1img(Context context, int[] int_image_list) {
        this.icontext = context;
        this.inflater = LayoutInflater.from(this.icontext);
        this.int_image_list = int_image_list;
        this.img_count = this.int_image_list.length;
    }


    @Override
    public int getCount() {
        return img_count;
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
        if(convertView == null){
            convertView = inflater.inflate(R.layout.custom_gridview_1img, null);
            holder = new ViewHolder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.imageview1_custom_gridview_1img);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        if(str_image_list != null){
            Bitmap imgBitmap = BitmapFactory.decodeFile(str_image_list[position]);
            holder.imageView.setImageBitmap(imgBitmap);
        }
        if(int_image_list != null){
            holder.imageView.setImageResource(int_image_list[position]);
        }

        return convertView;
    }

    private class ViewHolder {
        ImageView imageView;
    }


}
