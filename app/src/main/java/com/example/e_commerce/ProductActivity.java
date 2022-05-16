package com.example.e_commerce;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import java.util.ArrayList;

public class ProductActivity extends AppCompatActivity {

    String[]products=new String[4];
    ListView listView;
    private Button voice;
    private Button Barcode;
    E_CommerceDB e_commerceDB;
    SearchView searchView;
    int choose;

    CustomListView2 customListView2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);


        String categoryName=getIntent().getStringExtra("Key");
        //Toast.makeText(getApplicationContext(),categoryName,Toast.LENGTH_LONG).show();
        listView=(ListView) findViewById(R.id.list_item);
        e_commerceDB=new E_CommerceDB(getApplicationContext());
        voice=(Button) findViewById(R.id.voice);
        Barcode=(Button)findViewById(R.id.barcode);
        searchView=(SearchView) findViewById(R.id.sv);
        voice.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                OpenVoiceDialog();
                choose=1;
            }
        });

        Barcode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent=new Intent(ProductActivity.this,ScanningActivity.class);
                startActivity(intent);

            }
        });

        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                ProductActivity.this.customListView2.getFilter().filter(query);
                Toast.makeText(getApplicationContext(),query,Toast.LENGTH_LONG).show();
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                ProductActivity.this.customListView2.getFilter().filter(newText);

                Toast.makeText(getApplicationContext(),newText,Toast.LENGTH_LONG).show();
                return false;
            }
        });

        /*
        Product p1=new Product ("Rice",40,10,2,"1 Kilo of El Maleka Rice");
        e_commerceDB.addProduct(p1);
        Product p2=new Product ("Pepsi",15,20,2,"2 Liters of Pepsi");
        e_commerceDB.addProduct(p2);
        Product p3=new Product ("Cheese",35,10,2,"1 kilo of Lavachkeri Cheese");
        e_commerceDB.addProduct(p3);

        Product p4=new Product ("Pants",150,10,1,"LC-wiki pants with blue color");
        e_commerceDB.addProduct(p4);
        Product p5=new Product ("T-shirt",200,15,1,"T-shirt from Dejavo is trendy");
        e_commerceDB.addProduct(p5);
        Product p6=new Product ("Jacket",450,10,1,"Water proof jacket ");
        e_commerceDB.addProduct(p6);

        Product p7=new Product ("Bike",1500,5,3,"Ride Bike with speeds");
        e_commerceDB.addProduct(p7);
        Product p8=new Product ("Ball",200,10,3,"Ball with good material");
        e_commerceDB.addProduct(p8);
        Product p9=new Product ("Dumbbells",50,15,3,"Dumbbells for exercise in Gym");
        e_commerceDB.addProduct(p9);

        Product p10=new Product ("Chair",850,10,4,"Wood Chair with wheels");
        e_commerceDB.addProduct(p10);
        Product p11=new Product ("Freezer",6000,20,4,"Sharp Freezer which help to freeze food");
        e_commerceDB.addProduct(p11);
        Product p12=new Product ("Couch",350,10,4,"Comfortable Couch from Ika");
        e_commerceDB.addProduct(p12);

        Product p13=new Product ("Foundation",350,10,5,"Fit Me Foundation with best coverage ");
        e_commerceDB.addProduct(p13);
        Product p14=new Product ("Powder",6000,20,5,"NYX Professional Makeup Mineral Matte Finishing Loose Powder ");
        e_commerceDB.addProduct(p14);
        Product p15=new Product ("Blusher",350,10,5,"Amazing Blusher Colours with Mn ");
        e_commerceDB.addProduct(p15);
        Product p16=new Product ("Lipstick",350,10,5,"NYX PROFESSIONAL MAKEUP Matte Lipstick");
        e_commerceDB.addProduct(p16);

        Product p17=new Product ("Egg",40,7,2,"1 plate of egg");
        e_commerceDB.addProduct(p17);
        Product p18=new Product ("Socks",90,10,1,"Set of Woman Cotton Sock ");
        e_commerceDB.addProduct(p18);
        Product p19=new Product ("Rope",350,5,3,"Jump ropes for beginners");
        e_commerceDB.addProduct(p19);
        Product p20=new Product ("TV",5500,15,4,"LG TV with 55 inch HD LED");
        e_commerceDB.addProduct(p20);



         */




        int c=Integer.parseInt( categoryName);
        Cursor cursor1 = e_commerceDB.getCategoryName(c);
        String name=cursor1.getString(0);

        Cursor cursor= e_commerceDB.fetchProduct(c);


        ArrayList<String> arrayList=new ArrayList<String>();
        for(int i=0;i<cursor.getCount();i++){
            String con=cursor.getString(1)+"\n"+"Price  "+cursor.getString(2);
            products[i]=con;
           // arrayList.add(con);
            cursor.moveToNext();
        }

        if(name.equals("Clothes")){
          int[]  imgid={R.drawable.pants, R.drawable.tshirt, R.drawable.jacket,R.drawable.sock};
           customListView2=new CustomListView2(this,products,imgid);}
        else if(name.equals("Food")){
            int[]  imgid={R.drawable.rice, R.drawable.pepsi, R.drawable.cheese,R.drawable.egg};
            customListView2=new CustomListView2(this,products,imgid);
        }
        else if(name.equals("Sports")){
            int[]  imgid={R.drawable.bike, R.drawable.ball, R.drawable.dumbbells,R.drawable.rope};
            customListView2=new CustomListView2(this,products,imgid);
        }
        else if(name.equals("Makeup")){
            int[]  imgid={R.drawable.foundation, R.drawable.powder, R.drawable.blusher,R.drawable.lipstick};
            customListView2=new CustomListView2(this,products,imgid);
        }
        else{
            int[]  imgid={R.drawable.chair, R.drawable.freezer, R.drawable.couch,R.drawable.tv};
            customListView2=new CustomListView2(this,products,imgid);
        }
      //  ArrayAdapter<String> arrayAdapter=new ArrayAdapter<String>(getApplicationContext(), android.R.layout.simple_list_item_1,arrayList);
        listView.setAdapter(customListView2);

        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

               String  selectedname=(String)( listView.getItemAtPosition(position));
              String []items= selectedname.split("\n");
               Cursor cursor1=e_commerceDB.getProductID(items[0].trim());
              //  Toast.makeText(getApplicationContext(),items[0].trim(),Toast.LENGTH_LONG).show();
                Intent intent=new Intent(ProductActivity.this,ProductDetailsActivity.class);
                intent.putExtra("keyy",cursor1.getString(0));
                startActivity(intent);
            }
        });

    }




/*


 */
    private void OpenVoiceDialog() {
        Intent intent =new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        startActivityForResult(intent,200);

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == 200 && resultCode ==RESULT_OK){
            ArrayList<String>arrayList=data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            String voice=arrayList.get(0);
            if (e_commerceDB.Exist(voice)) {
                Cursor cursor=e_commerceDB.getProductID(voice);
                Intent intent=new Intent(ProductActivity.this,ProductDetailsActivity.class);
                intent.putExtra("keyy",cursor.getString(0));
                Toast.makeText(this,voice,Toast.LENGTH_LONG).show();
                startActivity(intent);
            }
            else
            {

                Toast.makeText(this,"This product does’t Exist",Toast.LENGTH_LONG).show();
            }

        }
        else{
            Toast.makeText(this,"Something went wrong",Toast.LENGTH_LONG).show();
        }
    }




}
