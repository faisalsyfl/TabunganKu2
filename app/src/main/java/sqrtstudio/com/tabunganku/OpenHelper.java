package sqrtstudio.com.tabunganku;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Faisal Syaiful Anwar on 10/30/2016.
 */

public class OpenHelper extends SQLiteOpenHelper {


    public static final int DATABASE_VERSION = 1;
    public static final String DATABASE_NAME = "dbTabungan.db";
    public static final String TABLE = "TABUNGAN";
    public static final String KEY_ID = "ID";
    public static final String KEY_CAT = "CATEGORY";
    public static final String KEY_SPENT = "SPENT";
    public static final String KEY_DESC = "DESC";
    public static final String KEY_TGL = "TGL";


    public OpenHelper (Context context) {
        super(context, DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_CONTACTS_TABLE = "CREATE TABLE " + TABLE + "("
        + KEY_ID + " INTEGER PRIMARY KEY," + KEY_CAT + " TEXT,"
        + KEY_SPENT + " TEXT," + KEY_DESC + " TEXT," + KEY_TGL + " TEXT" + ")";
        db.execSQL(CREATE_CONTACTS_TABLE);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Drop older table if existed
        db.execSQL("DROP TABLE IF EXISTS" + TABLE);
        // Creating tables again
        onCreate(db);
    }
}
