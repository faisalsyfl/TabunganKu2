package sqrtstudio.com.tabunganku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        SharedPreferences sp = getSharedPreferences("com.sqrtstudio.tabunganku",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        if(!sp.getString("owner","EMPTY").equals("EMPTY")){
            Intent i = new Intent(this,MainActivity.class);
//            ed.clear();
//            ed.commit();
            startActivity(i);
        }
        setContentView(R.layout.activity_login);
        super.onCreate(savedInstanceState);
    }

    public void startJourney(View v){
        SharedPreferences sp = getSharedPreferences("com.sqrtstudio.tabunganku",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("owner",((EditText)findViewById(R.id.firstEt)).getText().toString());
        ed.commit();
        Intent i = new Intent(this,Budgeting.class);
        startActivity(i);
    }
}
