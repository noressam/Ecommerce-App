package com.example.e_commerce;

import android.app.Activity;
import android.content.Context;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;

public class CustomListView extends ArrayAdapter<String> {

    private ArrayList<String> products=new ArrayList<String>();
    //private int[] imageid;
    private Activity context;
    private ArrayList<Integer>quantity=new ArrayList<Integer>();
    private ArrayList<Integer>price=new ArrayList<Integer>();
    ViewHolder viewHolder=null;
    static boolean removed=false;
    int total=0;
    int j=0;
    ShoppingCart c;

    public CustomListView(Activity context, ArrayList<String>products, ArrayList<Integer>quantity,ArrayList<Integer>price) {
        super(context,R.layout.listviewlayout,products);
        this.context=context;
        this.products=products;
        this.price=price;
        this.quantity=quantity;
    }


    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        View r=convertView;

        if(r==null){
            LayoutInflater layoutInflater=context.getLayoutInflater();
            r=layoutInflater.inflate(R.layout.listviewlayout,null,true);
            viewHolder=new ViewHolder(r);
            r.setTag(viewHolder);
        }
        else {
            viewHolder=(ViewHolder) r.getTag();
        }
        //viewHolder.ig.setImageResource(imageid[position]);
        viewHolder.tv.setText(products.get(position));
        viewHolder.b.setText("Remove");

        viewHolder.et.setText(String.valueOf(quantity.get(position)));
        viewHolder.tp.setText(price.get(position)+"$");
        viewHolder.b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                j=Cart.list.indexOf(viewHolder.tv.getText().toString())+1;
                Cart.list.remove(j);
                viewHolder.tv.setVisibility(View.INVISIBLE);
                viewHolder.b.setVisibility(View.INVISIBLE);
                viewHolder.et.setVisibility(View.INVISIBLE);
                viewHolder.tp.setVisibility(View.INVISIBLE);
                for (int i = 0; i < Cart.list.size(); i++) {
                    total += price.get(i) * quantity.get(i);
                }
                CartActivity.t2.setText(String.valueOf(total));
                //removed=true;
                //CartActivity.isRemoved();

            }
        });
        viewHolder.et.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {


                j=Cart.list.indexOf(viewHolder.tv.getText().toString())+1;
                String newquan=s.toString().trim();
                c=new ShoppingCart(viewHolder.tv.getText().toString(),price.get(j),Integer.parseInt(newquan));
                Cart.list.set(j,c);
                quantity.set(j,Integer.parseInt(newquan));
                total=0;
                for (int i = 0; i < Cart.list.size(); i++) {
                    total += price.get(i) * quantity.get(i);
                }
                CartActivity.t2.setText(String.valueOf(total));

            }
        });
        return r;

    }
    class ViewHolder{
       // ImageView ig;
        TextView tv;
        Button b;
        EditText et;
        TextView tp;
        ViewHolder(View v)
        {
         //   ig=(ImageView) v.findViewById(R.id.image);
            tv=(TextView) v.findViewById(R.id.text);
            b=(Button) v.findViewById(R.id.button);
            et=(EditText) v.findViewById(R.id.editText1);
            tp=(TextView) v.findViewById(R.id.tv);
        }
    }
}
