package com.example.e_commerce;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import androidx.annotation.Nullable;

public class E_CommerceDB extends SQLiteOpenHelper {

    private static String dbname="ecommerceDB";
    SQLiteDatabase EcommerceDB;
    public E_CommerceDB(@Nullable Context context) {
        super(context, dbname,null,10);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table Customers(CID integer primary key autoincrement, name text not null,username text not null,password text not null,gender text not null,birthdate text not null,job text not null);");
        db.execSQL("create table Orders(OID integer primary key autoincrement ,orddate text not null,adress text not null,C_ID integer,FOREIGN KEY (C_ID)REFERENCES Customers(CID))");
        db.execSQL("create table Categories(CatID integer primary key autoincrement,cat_name text not null)");
        db.execSQL("create table Products (PID  integer primary key autoincrement,pro_name text not null,price integer not null,quantity integer not null,description text not null,Cat_ID integer,FOREIGN KEY (Cat_ID)REFERENCES Categories(CatID))");
        db.execSQL("create table OrderDetails(OrdID ,prodID  integer primary key autoincrement ,quantity integer not null,FOREIGN KEY (OrdID)REFERENCES Orders (OID),FOREIGN KEY (prodID)REFERENCES Products (PID))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Customers");
        db.execSQL("drop table if exists Orders");
        db.execSQL("drop table if exists Categories");
        db.execSQL("drop table if exists Products");
        db.execSQL("drop table if exists OrderDetails");
        onCreate(db);
    }

    public void AddCustomer(Customer customer){
        ContentValues row=new ContentValues();
        row.put("name",customer.name);
        row.put("username",customer.username);
        row.put("password",customer.password);
        row.put("gender",customer.gender);
        row.put("birthdate",customer.birthdate);
        row.put("job",customer.job);
        EcommerceDB=getWritableDatabase();
        EcommerceDB.insert("Customers",null,row);
        EcommerceDB.close();
    }

    public String getPassword(String username){
        EcommerceDB=getReadableDatabase();
        String []row={username};
        Cursor cursor=EcommerceDB.rawQuery("Select password from Customers where username like ? ",row);
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            EcommerceDB.close();
            return cursor.getString(0);
        }
        EcommerceDB.close();
        return  "";


    }
    public int getCustomerID(String username){
        EcommerceDB=getReadableDatabase();
        String []row={username};
        Cursor cursor=EcommerceDB.rawQuery("Select CID from Customers where username like ? ",row);
        if(cursor.getCount()!=0){
            cursor.moveToFirst();
            EcommerceDB.close();
            return Integer.parseInt(cursor.getString(0));
        }
        EcommerceDB.close();
        return  0;}
    public void AddCategory(String CatName){
        ContentValues row=new ContentValues();
        row.put("cat_name",CatName);
        EcommerceDB=getWritableDatabase();
        EcommerceDB.insert("Categories",null,row);
        EcommerceDB.close();
    }

   public boolean CheckUserName(String name){

       String []row={name};
       EcommerceDB=getReadableDatabase();
       Cursor cursor=EcommerceDB.rawQuery("Select username from Customers where username like ? ",row);
       cursor.moveToFirst();
       EcommerceDB.close();
       if(cursor.getCount()>0)
           return false;
       else
           return true;


   }


   public boolean Login(String username,String password){
        String []row={username};
       EcommerceDB=getReadableDatabase();
       Cursor cursor=EcommerceDB.rawQuery("Select username from Customers where username like ? ",row);
       cursor.moveToFirst();
        String [] row1={password};
       Cursor cursor1=EcommerceDB.rawQuery("Select password from Customers where password like ? ",row1);
       cursor1.moveToFirst();
       EcommerceDB.close();
       if(cursor.getCount()==0||cursor1.getCount()==0)
           return false;
       else {
           String s1 = cursor.getString(0);
           String s2 = cursor1.getString(0);
           if (username.equals(s1) && password.equals(s2))
               return true;
           else
               return false;
       }

   }
   public Cursor fetchAllCategories(){

       EcommerceDB=getReadableDatabase();
       String []col={"cat_name"};
       Cursor cursor=EcommerceDB.query("Categories",col,null,null,null,null,null);
       if(cursor !=null)
           cursor.moveToFirst();
       EcommerceDB.close();
       return cursor;
   }
   public Cursor fetchProduct(int CategoryID){
       String []row={String.valueOf(CategoryID)};
       EcommerceDB=getReadableDatabase();
       Cursor cursor=EcommerceDB.rawQuery("Select * from Products where Cat_ID like ? ",row);
       cursor.moveToFirst();
       EcommerceDB.close();
       return cursor;
   }
   public Cursor getCategoryID(String Name){
       String[]col={Name};
       EcommerceDB=getReadableDatabase();
       Cursor cursor=EcommerceDB.rawQuery("Select CatID from Categories where cat_name like ? ",col);
       cursor.moveToFirst();
       EcommerceDB.close();
       return cursor;
   }
    public Cursor getCategoryName(int id){
        String[]col={String.valueOf(id)};
        EcommerceDB=getReadableDatabase();
        Cursor cursor=EcommerceDB.rawQuery("Select cat_name from Categories where CatID like ? ",col);
        cursor.moveToFirst();
        EcommerceDB.close();
        return cursor;
    }
   public Cursor searchForProducts(String productName){
        String[]col={productName};
        EcommerceDB=getReadableDatabase();
        Cursor cursor=EcommerceDB.rawQuery("Select * from Products where pro_name like ? ",col);
        cursor.moveToFirst();
        EcommerceDB.close();
        return cursor;
   }
    public Cursor searchForProduct(String productID){
        String[]col={productID};
        EcommerceDB=getReadableDatabase();
        Cursor cursor=EcommerceDB.rawQuery("Select * from Products where PID like ? ",col);
        cursor.moveToFirst();
        EcommerceDB.close();
        return cursor;
    }
    public boolean Exist(String ProductName){
        String[]col={ProductName};
        EcommerceDB=getReadableDatabase();
        Cursor cursor=EcommerceDB.rawQuery("Select pro_name from Products where pro_name like ? ",col);
        cursor.moveToFirst();
        EcommerceDB.close();
        if(cursor.getCount()!=0)
            return true;
        else
            return false;

    }
    public boolean Exists(String ProductID){
        String[]col={ProductID};
        EcommerceDB=getReadableDatabase();
        Cursor cursor=EcommerceDB.rawQuery("Select PID from Products where PID like ? ",col);
        cursor.moveToFirst();
        EcommerceDB.close();
        if(cursor.getCount()!=0)
            return true;
        else
            return false;

    }

   public int getPrice(String productName){
       String[]col={productName};
       EcommerceDB=getReadableDatabase();
       Cursor cursor=EcommerceDB.rawQuery("Select price from Products where pro_name like ? ",col);
       cursor.moveToFirst();
       EcommerceDB.close();
       return Integer.parseInt(cursor.getString(0));
   }


   public void addProduct(Product product){

        ContentValues row=new ContentValues();
        row.put("pro_name",product.Name);
        row.put("price",product.Price);
        row.put("quantity",product.Quantity);
        row.put("Cat_ID",product.CategoryID);

        row.put("description",product.Description);
       EcommerceDB=getWritableDatabase();
       EcommerceDB.insert("Products",null,row);
       EcommerceDB.close();

   }
    public Cursor getProductID(String Name){
        String[]col={Name};
        EcommerceDB=getReadableDatabase();
        Cursor cursor=EcommerceDB.rawQuery("Select PID from Products where pro_name like ? ",col);
        cursor.moveToFirst();
        EcommerceDB.close();
        return cursor;
    }
    public Cursor getProduct(int id){
        String[]col={String.valueOf(id)};
        EcommerceDB=getReadableDatabase();
        Cursor cursor=EcommerceDB.rawQuery("Select * from Products where PID like ? ",col);
        cursor.moveToFirst();
        EcommerceDB.close();
        return cursor;
    }
    public Cursor getOrderID(int Customer_id){
        String[]col={String.valueOf(Customer_id)};
        EcommerceDB=getReadableDatabase();
        Cursor cursor=EcommerceDB.rawQuery("Select OID from Orders where C_ID like ? ",col);
        cursor.moveToFirst();
        EcommerceDB.close();
        return cursor;
    }
   public void AddOrder(Order order){
        ContentValues row=new ContentValues();
        row.put("orddate",String.valueOf(order.OrderDate));
        row.put("adress",order.Address);
        row.put("C_ID",order.Customer_id);
        EcommerceDB=getWritableDatabase();
        EcommerceDB.insert("Orders",null,row);
        EcommerceDB.close();


   }
    public void FinishOrder(Order order,int quan){
        ContentValues row2=new ContentValues();
        Cursor cursor=getOrderID(order.Customer_id);
        row2.put("OrdID",cursor.getString(0));
        Cursor cursor1=getProductID(order.product_name);
        row2.put("prodID",cursor1.getString(0));
        row2.put("quantity",quan);
        EcommerceDB=getWritableDatabase();
        EcommerceDB.insert("OrderDetails",null,row2);
        EcommerceDB.close();
    }

   public void UpdateQuantity(int quan,String name){

       String[]col={String.valueOf(quan),name};
       EcommerceDB=getWritableDatabase();
       EcommerceDB.execSQL("Update Products Set quantity=@quan Where pro_name like ?",col);
       EcommerceDB.close();
   }


}
