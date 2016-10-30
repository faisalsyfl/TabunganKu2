package sqrtstudio.com.tabunganku;

import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

public class Budgeting extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_budgeting);
    }

    public void budgetok(View v){
        SharedPreferences sp = getSharedPreferences("com.sqrtstudio.tabunganku",MODE_PRIVATE);
        SharedPreferences.Editor ed = sp.edit();
        ed.putString("budget",((EditText)findViewById(R.id.budgeting)).getText().toString());
        ed.commit();
        Intent i = new Intent(this,MainActivity.class);
        startActivity(i);
    }
}
