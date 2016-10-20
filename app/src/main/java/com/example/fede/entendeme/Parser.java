package com.example.fede.entendeme;

/**
 * Created by fede on 20/10/2016.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.support.design.widget.Snackbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import static com.example.fede.entendeme.Constants.FIRST_COLUMN;
import static com.example.fede.entendeme.Constants.SECOND_COLUMN;

/**
 * Created by Hp on 3/24/2016.
 */
public class Parser extends AsyncTask<Void,Integer,Integer>{
    Context c;
    ListView lv;
    String data;
    TextView txtNoConversions;
    List<Integer> listViewIds;
    int id;
    //ArrayList<String> players=new ArrayList<>();
    final ArrayList<HashMap<String, String>> players = new ArrayList<HashMap<String,String>>();
    ProgressDialog pd;
    public Parser(Context c, String data, ListView lv, TextView txtNoConversions, int id, List<Integer> listViewIds) {
        this.c = c;
        this.data = data;
        this.lv = lv;
        this.txtNoConversions = txtNoConversions;
        this.id = id;
        this.listViewIds = listViewIds;
    }
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(c);
        pd.setTitle("Procesando");
        pd.setMessage("Buscando datos...Por favor espere");
        pd.show();
    }
    @Override
    protected Integer doInBackground(Void... params) {
        return this.parse();
    }
    @Override
    protected void onPostExecute(Integer integer) {
        super.onPostExecute(integer);
        if(integer == 1)
        {
            txtNoConversions.setVisibility(View.INVISIBLE);
            //ADAPTER
            //ArrayAdapter<String> adapter=new ArrayAdapter<String>(c,android.R.layout.simple_list_item_1,players);
            ListViewConversionsAdapter adapter = new ListViewConversionsAdapter(c, players);

            //ADAPT TO LISTVIEW
            lv.setAdapter(adapter);
            if(adapter.isEmpty()){
                txtNoConversions.setVisibility(View.VISIBLE);
            }

            //LISTENET
            lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    //Snackbar.make(view,players.get(position),Snackbar.LENGTH_SHORT).show();;
                }
            });
        }else
        {
            //Toast.makeText(c,"Unable to Parse",Toast.LENGTH_SHORT).show();
            txtNoConversions.setVisibility(View.VISIBLE);
        }
        pd.dismiss();
    }
    //PARSE RECEIVED DATA
    private int parse()
    {
        try
        {
            //ADD THAT DATA TO JSON ARRAY FIRST
            JSONArray ja=new JSONArray(data);
            //CREATE JO OBJ TO HOLD A SINGLE ITEM
            JSONObject jo=null;
            players.clear();
            //LOOP THRU ARRAY
            for(int i=0;i<ja.length();i++)
            {
                jo=ja.getJSONObject(i);
                //RETRIOEVE NAME
                String idConversionString = jo.getString("id");
                int idConversion = Integer.parseInt(idConversionString);
                String name =jo.getString("conversion");
                String date = jo.getString("lastModificationDate");
                String userIdConversionString = jo.getString("userId");
                int userIdConversion = Integer.parseInt(userIdConversionString);
                if(id == userIdConversion) {
                    HashMap<String, String> temp = new HashMap<String, String>();
                    temp.put(FIRST_COLUMN, name);
                    temp.put(SECOND_COLUMN, date);
                    //ADD IT TO OUR ARRAYLIST
                    players.add(temp);
                    listViewIds.add(idConversion);
                }
            }
            return 1;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return 0;
    }

}
