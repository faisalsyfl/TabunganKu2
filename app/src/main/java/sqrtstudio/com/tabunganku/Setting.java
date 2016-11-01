package sqrtstudio.com.tabunganku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class Setting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        SharedPreferences sp = getSharedPreferences("com.sqrtstudio.tabunganku",MODE_PRIVATE);

        EditText yn = (EditText)findViewById(R.id.yn);
        yn.setText(sp.getString("owner","#UNDEFINED"));
        EditText cb = (EditText)findViewById(R.id.cb);
        cb.setText(sp.getString("budget","#UNDEFINED"));
//        Button btn = (Button)findViewById(R.id.btnSet);
    }

    public void ButtonSet(View v){
        SharedPreferences sp = getSharedPreferences("com.sqrtstudio.tabunganku",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        EditText yn = (EditText)findViewById(R.id.yn);
        ed.putString("owner",yn.getText().toString());
        EditText cb = (EditText)findViewById(R.id.cb);
        ed.putString("budget",cb.getText().toString());
        ed.commit();

        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
