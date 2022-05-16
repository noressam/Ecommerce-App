package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

public class CartActivity extends AppCompatActivity {

    ArrayList <String>products=new ArrayList<String>();
    ArrayList<Integer>Quantity=new ArrayList<Integer>();
    ArrayList<Integer> Price=new ArrayList<Integer>();
    ListView listView;
    EditText editText;
    CustomListView customListView;
    Cursor cursor;
    static TextView t2;
    Button checkout;
    static int total=0;
    static int totalq=0;
   // ArrayList<ShoppingCart>shoppingCarts=new ArrayList<ShoppingCart>();
   // ShoppingCart s;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        editText = (EditText) findViewById(R.id.editText1);
         t2 = (TextView) findViewById(R.id.totaltext);
        checkout=(Button)findViewById(R.id.checkout);
        listView = (ListView) findViewById(R.id.list_item1);
        E_CommerceDB e_commerceDB = new E_CommerceDB(getApplicationContext());
        String id = getIntent().getStringExtra("Product");
        String quantity = getIntent().getStringExtra("Quantity");
        String price = getIntent().getStringExtra("Price");

        cursor = e_commerceDB.getProduct(Integer.parseInt(id));
        // Toast.makeText(getApplicationContext(),cursor.getString(1),Toast.LENGTH_LONG).show();
/*
       for(int i=0;i<cursor.getCount();i++) {
           products = new String[]{cursor.getString(1)};
           cursor.moveToNext();
       }

 */

        //  s=new ShoppingCart(products[0],Integer.parseInt(quantity),Integer.parseInt(price));
        // shoppingCarts.add(s);

        Toast.makeText(getApplicationContext(), String.valueOf(Cart.list.size()), Toast.LENGTH_LONG).show();
        total=0;
        for (int i = 0; i < Cart.list.size(); i++) {
            //products[i]=shoppingCarts.get(i).name;
            products.add(Cart.list.get(i).name);
            Price.add(Cart.list.get(i).price);
            Quantity.add(Cart.list.get(i).quantity);
            total += Price.get(i) * Quantity.get(i);
            totalq+=Cart.list.get(i).quantity;

        }


        customListView = new CustomListView(this, products, Quantity, Price);

        //   Toast.makeText(getApplicationContext(),products[0],Toast.LENGTH_LONG).show();


        //    customListView.setQuantity(quantity);
        //   customListView.setPrice(price);
        listView.setAdapter(customListView);


        if (Cart.list.size() != 0) {
           // isRemoved();
            t2.setText(String.valueOf(total));
        }
        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(CartActivity.this,SubmissionActivity.class);
                startActivity(intent);
            }
        });
    }
    public void isRemoved(){

        if(CustomListView.removed==true){
            for (int i = 0; i < Cart.list.size(); i++) {
                total += Price.get(i) * Quantity.get(i);
            }
        }
    }

}