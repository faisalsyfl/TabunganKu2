package sqrtstudio.com.tabunganku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    static final int ACT2_REQUEST = 99;
    static final int ACT3_REQUEST = 88;
    static final String ID_MESSAGE = "id";
    static final String CAT_MESSAGE = "cat";
    static final String SPENT_MESSAGE = "spent";
    static final String DESC_MESSAGE = "desc";
    static final String TYPE = "tipe";
    List<DbTabungan.Tabungan> items = new ArrayList<DbTabungan.Tabungan>();
    ArrayAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        /*Set Budget*/


        DbTabungan db = new DbTabungan(getApplicationContext());
        db.open();
//        db.removeAll();

        ArrayList<DbTabungan.Tabungan> data = db.selectAll();
        final ListView tabList = (ListView) findViewById(R.id.list1);
        adapter = new CustomAdapter(this,data);
        tabList.setAdapter(adapter);

        tabList.setClickable(true);
        tabList.setOnItemClickListener( new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View arg1, int position, long arg3) {
                clickList((DbTabungan.Tabungan)tabList.getItemAtPosition(position));
                adapter.notifyDataSetChanged();
            }
        });

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


    }

    @Override
    protected void onStart() {
        super.onStart();
        SharedPreferences sp = getSharedPreferences("com.sqrtstudio.tabunganku",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        TextView tv = (TextView)findViewById(R.id.currentBudget);
//        Log.d("Budget:",sp.getString("budget","####"));
        tv.setText(formatRP(sp.getString("budget","#UNDEFINED")));
    }

    public void clickList(DbTabungan.Tabungan data){
        Log.d("data:",String.valueOf(data.spent));
        Intent intent3 = new Intent(this,EditMode.class);
        intent3.putExtra(ID_MESSAGE,data.getId());
        intent3.putExtra(CAT_MESSAGE,data.getCategory());
        intent3.putExtra(SPENT_MESSAGE,data.spent);
        intent3.putExtra(DESC_MESSAGE,data.getDesc());
//        Log.d("ID",data.getSpent()));
        startActivityForResult(intent3,ACT3_REQUEST);
    }
    public void klikButton(View v) {

        Intent intent2 = new Intent(this, EditMode.class);
        startActivityForResult(intent2,ACT2_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // cek request code
        if (requestCode == ACT2_REQUEST) {
            adapter.notifyDataSetChanged();
            Toast toast = Toast.makeText(getApplicationContext(),"Berhasil Tambah data",Toast.LENGTH_SHORT);
            toast.show();
            finish();
            startActivity(getIntent());
        }else if(requestCode == ACT3_REQUEST){
            adapter.notifyDataSetChanged();
            Toast toast = Toast.makeText(getApplicationContext(),"Berhasil Edit data",Toast.LENGTH_SHORT);
            toast.show();
            finish();
            startActivity(getIntent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }else if(id == R.id.Laporan){

        }

        return super.onOptionsItemSelected(item);
    }
    public void showReport(MenuItem menu){
        Intent i = new Intent(this,ChartView.class);

        startActivity(i);
    }
    public String formatRP(String angka){
        String angka_satu,angka_dua;
        NumberFormat rupiahFormat;
        String Rupiah = "Rp.";

        if(angka.equals("")){
            angka_satu = "0";
        }else{
            angka_satu = angka;
        }

        rupiahFormat = NumberFormat.getInstance(Locale.GERMANY);
        String rupiah = rupiahFormat.format(Double.parseDouble(angka_satu));

        String Result = Rupiah + " " + rupiah ;

        return Result;
    }
}
