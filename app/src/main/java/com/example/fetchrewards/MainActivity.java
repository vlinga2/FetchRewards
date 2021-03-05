package com.example.fetchrewards;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class MainActivity extends AppCompatActivity {
    private ArrayList<ListItem> listItems;
    private RecyclerView customRecyclerView;
    private CustomAdapter customAdapter;
    private NetworkingTask networkingTask;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        networkingTask = new NetworkingTask();
        networkingTask.execute("https://fetch-hiring.s3.amazonaws.com/hiring.json");
    }

    @Override
    protected void onStart() {
        super.onStart();
        customRecyclerView = findViewById(R.id.recyclerView);
    }

    @Override
    protected void onStop() {
        if(!networkingTask.isCancelled())
            networkingTask.cancel(true);
        super.onStop();
    }

    void populateData(String s){
        String[] data = s.split("\n");
        listItems = new ArrayList<ListItem>();
        int count = -1;
        for(String line:data){
            count++;
            Log.d("TAG "+count, " "+data.length);
            if(count ==0 || count == data.length-1) {
                continue;
            }
            Log.d("TAG "+count, line);
            line = line.substring(1,line.length()-2);

            String[] cell = line.split(",");
            //Log.d("TAG", "cell length "+cell.length);
            String name = cell[2].substring(9);
            String listId = cell[1].substring(11);
            String id = cell[0].substring(6);
            if(name.length() ==2 || name.equals( "null")) continue;
            listItems.add(new ListItem(name, listId, id));
            Log.d("TAG "+count, " "+id+" "+listId+" "+name);
        }
        Collections.sort(listItems, new Comparator<ListItem>() {
            @Override
            public int compare(ListItem o1, ListItem o2) {
                if(o1.getListId().equals(o2.getListId()) ){
                    return o1.getName().compareTo(o2.getName());
                }
                return o1.getListId().compareTo(o2.getListId());

            }
        });
        customAdapter = new CustomAdapter(this,listItems);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        customRecyclerView.setLayoutManager(layoutManager);
        customRecyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));

        customRecyclerView.setAdapter(customAdapter);
    }

    class NetworkingTask extends AsyncTask<String, Void, String>{

        @Override
        protected String doInBackground(String... strings) {
            try {
                return HTTPGetCall(strings[0]);
            } catch (IOException e) {
                e.printStackTrace();
            }
            return "";
        }

        @Override
        protected void onPostExecute(String s) {
            populateData(s);
        }
    }

    protected String HTTPGetCall(String WebMethodURL) throws IOException, MalformedURLException
    {
        StringBuilder response = new StringBuilder();

        //Prepare the URL and the connection
        URL u = new URL(WebMethodURL);
        HttpURLConnection conn = (HttpURLConnection) u.openConnection();

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK)
        {
            //Get the Stream reader ready
            BufferedReader input = new BufferedReader(new InputStreamReader(conn.getInputStream()),8192);

            //Loop through the return data and copy it over to the response object to be processed
            String line = null;

            while ((line = input.readLine()) != null)
            {
                response.append(line+"\n");
            }

            input.close();
            conn.disconnect();
        }

        return response.toString();
    }
}