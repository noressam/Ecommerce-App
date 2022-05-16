package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class ProductDetailsActivity extends AppCompatActivity {

    int x=1;
    Cursor cursor;
    //Cart cart=new Cart();
    ShoppingCart c;
    static int totalq=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        String ProductID=getIntent().getStringExtra("keyy");
        E_CommerceDB e_commerceDB=new E_CommerceDB(getApplicationContext());
        TextView textView=(TextView) findViewById(R.id.textView1);
        Button button=(Button) findViewById(R.id.button1);
        Button plusButton=(Button) findViewById(R.id.plusButton);
        Button minusButton=(Button) findViewById(R.id.minusButton);

        Button cartButton=(Button) findViewById(R.id.cartButton);
        TextView textView2=(TextView) findViewById(R.id.textView2);
        int id=Integer.parseInt(ProductID);
        cursor=e_commerceDB.getProduct(id);
        totalq=Integer.parseInt(cursor.getString(3));
        String Details=cursor.getString(1)+"\n"+"Price : "+cursor.getString(2)+"\n"+"Quantity : "+
                cursor.getString(3)+"\n"+"Description : "+cursor.getString(4);
        textView.setText(Details);
        Intent cartIntent=new Intent(ProductDetailsActivity.this,CartActivity.class);
        cartIntent.putExtra("Product",ProductID);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                button.setVisibility(v.INVISIBLE);
                plusButton.setVisibility(v.VISIBLE);
                textView2.setVisibility(v.VISIBLE);
                minusButton.setVisibility(v.VISIBLE);

                Toast.makeText(getApplicationContext(),cursor.getString(1)+" is Added to Shopping Cart",Toast.LENGTH_LONG).show();
                c=new ShoppingCart(cursor.getString(1),Integer.parseInt(cursor.getString(2)),x);
                Cart.list.add(c);
            }
        });

        plusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x=Integer.parseInt( textView2.getText().toString().trim())+1;
               textView2.setText(String.valueOf(x));
                Toast.makeText(getApplicationContext(),cursor.getString(1)+" is Added to Shopping Cart",Toast.LENGTH_LONG).show();
                Cart.list.remove(c);
                c=new ShoppingCart(cursor.getString(1),Integer.parseInt(cursor.getString(2)),x);
                Cart.list.add(c);
            }
        });

        minusButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                x=Integer.parseInt( textView2.getText().toString().trim())-1;
                textView2.setText(String.valueOf(x));
                Toast.makeText(getApplicationContext(),"Item Quantity has been updated",Toast.LENGTH_LONG).show();
              //  Toast.makeText(getApplicationContext(),String.valueOf(x),Toast.LENGTH_LONG).show();
                Cart.list.remove(c);
                c=new ShoppingCart(cursor.getString(1),Integer.parseInt(cursor.getString(2)),x);
                Cart.list.add(c);

            }
        });

       // Toast.makeText(getApplicationContext(),String.valueOf(x),Toast.LENGTH_LONG).show();

        cartButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            //    Intent intent=new Intent(ProductDetailsActivity.this,CustomListView.class);
               cartIntent.putExtra("Quantity",String.valueOf(x));
                cartIntent.putExtra("Price",cursor.getString(2));

                  startActivity(cartIntent);
            }
        });
    }
}