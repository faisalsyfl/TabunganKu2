package sqrtstudio.com.tabunganku;

import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faisal Syaiful Anwar on 10/30/2016.
 */

public class DbTabungan{

    public static class Tabungan{
        public int id;
        public String category;
        public int spent;
        public String desc;

        public void setId(int id){
            this.id = id;
        }
        public int getId(){
            return this.id;
        }
        public void setCategory(String cat){
            this.category = cat;
        }
        public String getCategory(){
            return this.category;
        }
        public void setSpent(int spent){
            this.spent = spent;
        }
        public int getSpent(){
            return this.spent;
        }
        public void setDesc(String desc){
            this.desc = desc;
        }
        public String getDesc(){
            return this.desc;
        }
    }

    private SQLiteDatabase db;
    private final OpenHelper dbHelper;

    public DbTabungan (Context c){
        dbHelper = new OpenHelper(c);
    }

    public void open(){
        db = dbHelper.getWritableDatabase();
    }

    public void close(){
        db.close();
    }

    public long insertNew(String cat,int spent, String desc){
        ContentValues newValue = new ContentValues();
        newValue.put("CATEGORY",cat);
        newValue.put("SPENT",spent);
        newValue.put("DESC",desc);
        return db.insert("TABUNGAN",null,newValue);
    }

    // Getting All Shops
    public ArrayList<Tabungan> selectAll() {
        ArrayList<Tabungan> tabList = new ArrayList<Tabungan>();
        String selectQuery = "SELECT * FROM TABUNGAN";
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);
        if (cursor.moveToFirst()) {
            do {

                Tabungan data = new Tabungan();
                data.setId(Integer.parseInt(cursor.getString(0)));
                data.setCategory(cursor.getString(1));
                data.setSpent(Integer.parseInt(cursor.getString(2)));
                data.setDesc(cursor.getString(3));
                tabList.add(data);
            } while (cursor.moveToNext());
        }
        return tabList;
    }
    public Tabungan getTabungan(String id){
        Cursor cur = null;
        Tabungan T = new Tabungan();
        String[] cols = new String[]{"ID", "CATEGORY", "SPENT", "DESC"};

        String[] param = {id};
        cur = db.query("TABUNGAN",cols,"ID=?",param,null,null,null);

        if(cur.getCount()>0){
            cur.moveToFirst();
            T.id = Integer.parseInt(cur.getString(0));
            T.category = cur.getString(1);
            T.spent = Integer.parseInt(cur.getString(2));
            T.desc = cur.getString(3);
        }

        return T;
    }

    public int getID(String nama){
        Cursor cur = null;
        Tabungan T = new Tabungan();
        String[] cols = new String[]{"ID", "CATEGORY", "SPENT", "DESC"};

        String[] param = {};
        cur = db.query("TABUNGAN",cols,"ID=?",param,null,null,null);

        if(cur.getCount()>0){
            cur.moveToFirst();
            T.id = Integer.parseInt(cur.getString(0));

        }

        return T.id;
    }
    public void removeAll()
    {

        db.delete("Tabungan", null, null);
//        db.delete(DatabaseHelper.TAB_USERS_GROUP, null, null);
    }

    public int updateShop(Tabungan tab) {
        ContentValues values = new ContentValues();
        values.put("CATEGORY", tab.getCategory());
        values.put("SPENT", tab.getSpent());
        values.put("DESC", tab.getDesc());
        return db.update("TABUNGAN", values, "ID" + " = ?",
                new String[]{String.valueOf(tab.getId())});
    }
    public void deleteTabungan(int id) {
//        SQLiteDatabase db = this.getWritableDatabase();
        db.delete("TABUNGAN", "ID" + " = ?",
                new String[] { String.valueOf(id) });
        db.close();
    }
}
