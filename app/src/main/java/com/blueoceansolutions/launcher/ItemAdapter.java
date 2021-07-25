package com.blueoceansolutions.launcher;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;

public class ItemAdapter extends ArrayAdapter<Item> {

    private Context context;


    public ItemAdapter(Context context, int resourceId, List<Item> items){
        super(context, resourceId, items);
        this.context = context;
    }

    private class ViewHolder{
        TextView tv_name, tv_pack;
        ImageView iv_icon;
    }

    @NonNull
    public View getView(int position, View convertView, @NonNull ViewGroup parent){
        ViewHolder holder;
        Item rowItem = getItem(position);

        LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);

        if(convertView == null){
            convertView = mInflater.inflate(R.layout.item, null);
            holder = new ViewHolder();
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_pack = (TextView) convertView.findViewById(R.id.tv_pack);
            holder.iv_icon = (ImageView) convertView.findViewById(R.id.tv_icon);
            convertView.setTag(holder);
        } else
            holder = (ViewHolder) convertView.getTag();

        if(rowItem != null){
            holder.tv_name.setText(rowItem.getNome());
            holder.tv_pack.setText(rowItem.getPack());
            holder.iv_icon.setImageDrawable(rowItem.getIcon());
        }

        return convertView;
    }

}
