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
        super(context,R.layout.custom_row, tab);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        LayoutInflater mineInflater = LayoutInflater.from(getContext());
        View customView = mineInflater.inflate(R.layout.custom_row,parent,false);

        DbTabungan.Tabungan singleName = getItem(position);
//        Log.d("test",singleName.category.toString());

        TextView desc = (TextView) customView.findViewById(R.id.textDesc);
        TextView price = (TextView) customView.findViewById(R.id.textPrice);
        ImageView rowImage = (ImageView) customView.findViewById(R.id.imageView);

        desc.setText(singleName.getDesc().toString());
        price.setText(formatRP(String.valueOf(singleName.getSpent())));
        if(singleName.getCategory().toString().equals("Makan")){
            rowImage.setImageResource(R.drawable.makan);
        }else if(singleName.getCategory().toString().equals("Belanja")){
            rowImage.setImageResource(R.drawable.belaja);
        }else if(singleName.getCategory().toString().equals("Tabung")){
            rowImage.setImageResource(R.drawable.nabung);
        }else{
            rowImage.setImageResource(R.drawable.other);
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

