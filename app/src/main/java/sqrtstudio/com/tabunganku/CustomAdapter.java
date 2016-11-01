package sqrtstudio.com.tabunganku;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Faisal Syaiful Anwar on 10/28/2016.
 */


public class CustomAdapter extends ArrayAdapter<DbTabungan.Tabungan> {
    public CustomAdapter(Context context, ArrayList<DbTabungan.Tabungan> tab) {
        super(context,R.layout.custom_row2, tab);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mineInflater = LayoutInflater.from(getContext());
        View customView = mineInflater.inflate(R.layout.custom_row2,parent,false);

        DbTabungan.Tabungan singleName = getItem(position);

        TextView desc = (TextView) customView.findViewById(R.id.textDesc);
        TextView price = (TextView) customView.findViewById(R.id.textPrice);
        ImageView rowImage = (ImageView) customView.findViewById(R.id.imageView);
        TextView tgl = (TextView) customView.findViewById(R.id.textTgl);
        tgl.setText(singleName.getTgl().toString());
        desc.setText(singleName.getDesc().toString());
        price.setText(formatRP(String.valueOf(singleName.getSpent())));
        if(singleName.getCategory().toString().equals("Income")){
            rowImage.setImageResource(R.drawable.income);
        }else{
            rowImage.setImageResource(R.drawable.outcome);
        }

        return customView;
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

