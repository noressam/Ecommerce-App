package com.example.e_commerce;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

public class CustomListView2 extends ArrayAdapter<String> {
    private String [] activities;
    private int[] imageid;
    private Activity context;

    public CustomListView2(Activity context, String [] activities,int[] imageid) {
        super(context,R.layout.listviewlayout,activities);
        this.context=context;
        this.activities=activities;
        this.imageid=imageid;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r=convertView;
        CustomListView2.ViewHolder viewHolder=null;
        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.categorieslayout,null,true);
            viewHolder=new CustomListView2.ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder=(CustomListView2.ViewHolder) r.getTag();
        }
        viewHolder.ig.setImageResource(imageid[position]);
        viewHolder.tv.setText(activities[position]);


        return r;


    }
    class ViewHolder{
        ImageView ig;
        TextView tv;

        ViewHolder(View v)
        {
            ig=(ImageView) v.findViewById(R.id.image);
            tv=(TextView) v.findViewById(R.id.text);

        }
    }
}
