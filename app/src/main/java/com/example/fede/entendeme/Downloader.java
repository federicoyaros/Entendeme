package com.example.fede.entendeme;

/**
 * Created by fede on 20/10/2016.
 */
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;
import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Hp on 3/24/2016.
 */
public class Downloader extends AsyncTask<Void,Integer,String> {
    Context c;
    String address;
    ListView lv;
    ProgressDialog pd;
    TextView txtNoConversions;
    List<Integer> listViewIds;
    List<String> listViewTitles;
    List<String> listViewDates;
    int id;
    public Downloader(Context c, String address, ListView lv, TextView txtNoConversions, int id, List<Integer> listViewIds, List<String> listViewTitles, List<String> listViewDates) {
        this.c = c;
        this.address = address;
        this.lv = lv;
        this.txtNoConversions = txtNoConversions;
        this.id = id;
        this.listViewIds = listViewIds;
        this.listViewTitles = listViewTitles;
        this.listViewDates = listViewDates;
    }
    //B4 JOB STARTS
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pd=new ProgressDialog(c);
        pd.setTitle("Procesando");
        pd.setMessage("Buscando datos...Por favor espere");
        pd.show();
    }
    @Override
    protected String doInBackground(Void... params) {
        String data=downloadData();
        return data;
    }
    @Override
    protected void onPostExecute(String s) {
        super.onPostExecute(s);
        pd.dismiss();;
        if(s != null)
        {
            Parser p=new Parser(c,s,lv, txtNoConversions, id, listViewIds, listViewTitles, listViewDates);
            p.execute();
        }else
        {
            Toast.makeText(c,"Unable to download data",Toast.LENGTH_SHORT).show();
        }
    }
    private String downloadData()
    {
        //connect and get a stream
        InputStream is=null;
        String line =null;
        try {
            URL url=new URL(address);
            HttpURLConnection con= (HttpURLConnection) url.openConnection();
            is=new BufferedInputStream(con.getInputStream());
            BufferedReader br=new BufferedReader(new InputStreamReader(is));
            StringBuffer sb=new StringBuffer();
            if(br != null) {
                while ((line=br.readLine()) != null) {
                    sb.append(line+"n");
                }
            }else {
                return null;
            }
            return sb.toString();
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            if(is != null)
            {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
