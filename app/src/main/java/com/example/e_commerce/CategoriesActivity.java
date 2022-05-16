package com.example.e_commerce;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class CategoriesActivity extends AppCompatActivity {

    String[]categories=new String[5];
    int []imgid={R.drawable.clothes,R.drawable.food,R.drawable.sport,R.drawable.home,R.drawable.makeup};
    ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);
        listView=(ListView) findViewById(R.id.list_item);
        E_CommerceDB e_commerceDB=new E_CommerceDB(getApplicationContext());

        /*

        e_commerceDB.AddCategory("Clothes");
        e_commerceDB.AddCategory("Food");
        e_commerceDB.AddCategory("Sports");
        e_commerceDB.AddCategory("Home");
        e_commerceDB.AddCategory("Makeup");



         */


        Cursor cursor=e_commerceDB.fetchAllCategories();
        ArrayList<String>arrayList=new ArrayList<String>();
        for(int i=0;i<cursor.getCount();i++){
            categories[i]=cursor.getString(0);
         //   arrayList.add(cursor.getString(0));
            cursor.moveToNext();
        }

        CustomListView2 customListView2=new CustomListView2(this,categories,imgid);
       // ArrayAdapter<String>arrayAdapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(customListView2);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String nameselected=(String) (listView.getItemAtPosition(position));
                Cursor cursor1 = e_commerceDB.getCategoryID(nameselected);
                //Toast.makeText(getApplicationContext(),cursor1.getString(0),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(CategoriesActivity.this,ProductActivity.class);
                intent.putExtra("Key",cursor1.getString(0));
                startActivity(intent);
            }
        });
    }
}