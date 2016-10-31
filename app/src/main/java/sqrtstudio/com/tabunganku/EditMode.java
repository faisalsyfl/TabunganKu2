package sqrtstudio.com.tabunganku;

import android.app.DatePickerDialog;
import android.app.Dialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.icu.text.DateFormat;
import android.icu.text.SimpleDateFormat;
import android.opengl.EGLDisplay;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v4.app.DialogFragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import java.util.Calendar;
import java.util.Date;

public class EditMode extends AppCompatActivity {

    private DatePicker datePicker;
    private Calendar calendar;
    private TextView dateView;
    private int year, month, day;

    //    private int idedit = 99;

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_mode);


        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar2);
        setSupportActionBar(myToolbar);

        Spinner sp = (Spinner) findViewById(R.id.dropdown);
        EditText pc = (EditText) findViewById(R.id.spent);
        EditText dc = (EditText) findViewById(R.id.desc);
        EditText date = (EditText) findViewById(R.id.etdate);


        /*CREATING DATE*/
        SimpleDateFormat df = new SimpleDateFormat("MMM dd, YYYY");
        date.setText(df.format(new Date()));

        Button btn= (Button) findViewById(R.id.btn);


        if(getIntent().getExtras() != null){
            //JIKA EDIT
            Intent i = getIntent();
            sp.setSelection(getIndex(sp,i.getStringExtra(MainActivity.CAT_MESSAGE)));
            pc.setText("0", TextView.BufferType.EDITABLE);
            dc.setText(i.getStringExtra(MainActivity.DESC_MESSAGE));
            btn.setText("EDIT");
        }
    }

    public static class DatePickerFragment extends DialogFragment
            implements DatePickerDialog.OnDateSetListener {

        @Override
        public Dialog onCreateDialog(Bundle savedInstanceState) {
            // Use the current date as the default date in the picker
            final Calendar c = Calendar.getInstance();
            int year = c.get(Calendar.YEAR);
            int month = c.get(Calendar.MONTH);
            int day = c.get(Calendar.DAY_OF_MONTH);

            // Create a new instance of DatePickerDialog and return it
            return new DatePickerDialog(getActivity(), this, year, month, day);
        }

        @RequiresApi(api = Build.VERSION_CODES.N)
        public void onDateSet(DatePicker view, int year, int month, int day) {
            Calendar c = Calendar.getInstance();
            c.set(year,month,day);

            SimpleDateFormat sdf = new SimpleDateFormat("MMM dd, YYYY");
            String formattedDate = sdf.format(c.getTime());

            ((TextView) getActivity().findViewById(R.id.etdate)).setText(formattedDate);

        }
    }

    public void showDatePickerDialog(View v) {
        DialogFragment newFragment = new DatePickerFragment();
        newFragment.show(getSupportFragmentManager(), "datePicker");
    }
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_bar, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.hapus) {
            if(getIntent().getExtras() != null){
                Intent i = getIntent();
                DbTabungan db = new DbTabungan(getApplicationContext());
                db.open();
                db.deleteTabungan(i.getIntExtra(MainActivity.ID_MESSAGE,99));
                Intent i2 = new Intent(this,MainActivity.class);
                startActivity(i2);
            }
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    //class helper for spinner
    private int getIndex(Spinner sp,String val){
        int index = 0;

        for (int i=0;i<sp.getCount();i++){
            if (sp.getItemAtPosition(i).toString().equalsIgnoreCase(val)){
                index = i;
                break;
            }
        }
        return index;
    }

    public void klikButton(View v) {
        Boolean stats = true;
        Spinner dd = (Spinner)findViewById(R.id.dropdown);
        Intent intent2 = getIntent();
        if(getIntent().getExtras() != null){
            //operasi edit
            stats = false;
        }else{
            stats = true;
        }
        intent2.putExtra(MainActivity.CAT_MESSAGE,dd.getSelectedItem().toString());
        intent2.putExtra(MainActivity.SPENT_MESSAGE,((EditText)findViewById(R.id.spent)).getText().toString());
        intent2.putExtra(MainActivity.DESC_MESSAGE,((EditText)findViewById(R.id.desc)).getText().toString());

        DbTabungan db = new DbTabungan(getApplicationContext());
        db.open();

        EditText etharga= (EditText)findViewById(R.id.spent);
        int harga = Integer.parseInt(etharga.getText().toString());


        if(stats){
//            Log.d("cek",((EditText)findViewById(R.id.etdate)).getText().toString());
            db.insertNew(dd.getSelectedItem().toString(),harga,((EditText)findViewById(R.id.desc)).getText().toString(),((EditText)findViewById(R.id.etdate)).getText().toString());
            if(dd.getSelectedItem().toString().equals("Income")){
                SharedPreferences sp = getSharedPreferences("com.sqrtstudio.tabunganku",MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                String calc = String.valueOf(Integer.parseInt(sp.getString("budget","NULL")) + harga);
//                Log.d("Budget:",calc);
                ed.putString("budget",calc);
                ed.commit();
            }else{
                SharedPreferences sp = getSharedPreferences("com.sqrtstudio.tabunganku",MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                String calc = String.valueOf(Integer.parseInt(sp.getString("budget","NULL")) - harga);
//                Log.d("Budget:",calc);
                ed.putString("budget",calc);
                ed.commit();
            }
        }else{
            DbTabungan.Tabungan newV = new DbTabungan.Tabungan();
            newV.setId(intent2.getIntExtra(MainActivity.ID_MESSAGE,0));
            newV.setCategory(dd.getSelectedItem().toString());
            newV.setSpent(harga);
            newV.setDesc(((EditText)findViewById(R.id.desc)).getText().toString());
            newV.setTgl(((EditText)findViewById(R.id.etdate)).getText().toString());
            db.updateShop(newV);
            if(dd.getSelectedItem().toString().equals("Tabung")){
                SharedPreferences sp = getSharedPreferences("com.sqrtstudio.tabunganku",MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                String calc = String.valueOf(Integer.parseInt(sp.getString("budget","NULL")) + harga);
//                Log.d("Budget:",calc);
                ed.putString("budget",calc);
                ed.commit();
            }else{
                SharedPreferences sp = getSharedPreferences("com.sqrtstudio.tabunganku",MODE_PRIVATE);
                SharedPreferences.Editor ed = sp.edit();
                String calc = String.valueOf(Integer.parseInt(sp.getString("budget","NULL")) - harga);
//                Log.d("Budget:",calc);
                ed.putString("budget",calc);
                ed.commit();
            }
        }
        db.close();

        setResult(RESULT_OK,intent2);

        finish();
    }



}
