package com.example.fede.entendeme;

import android.widget.BaseAdapter;
import java.util.ArrayList;
import java.util.HashMap;
import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import static com.example.fede.entendeme.Constants.*;

public class ListViewConversionsAdapter extends BaseAdapter
{
    public ArrayList<HashMap<String, String>> list;

    Activity activity;
    TextView txtName;
    TextView txtDate;

    public ListViewConversionsAdapter(Activity activity, ArrayList<HashMap<String, String>> list){
        super();
        this.activity=activity;
        this.list=list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        // TODO Auto-generated method stub
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return 0;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // TODO Auto-generated method stub



        LayoutInflater inflater=activity.getLayoutInflater();

        if(convertView == null){

            convertView=inflater.inflate(R.layout.list_view_conversions_columns, null);

            txtName=(TextView) convertView.findViewById(R.id.name);
            txtDate=(TextView) convertView.findViewById(R.id.date);
        }

        HashMap<String, String> map=list.get(position);
        txtName.setText(map.get(FIRST_COLUMN));
        txtDate.setText(map.get(SECOND_COLUMN));

        return convertView;
    }
}
