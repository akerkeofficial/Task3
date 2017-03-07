package com.example.macbookpro.task3_android2;
import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class MainActivity extends Activity {




    BroadcastReceiver br;
    EditText et;
    ListView lv;
    ArrayList<Map<String, String>> data;
    SimpleAdapter aa;
    public final static String BROADCAST_ACTION = "ru.startandroid.develop.p0961servicebackbroadcast";
    @Override
    public void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et=(EditText)findViewById(R.id.et);
        lv=(ListView)findViewById(R.id.lv);
        data=new ArrayList<>();
        aa = new SimpleAdapter(this, data, R.layout.item,
                new String[]{"First", "Second"}, new int[]{R.id.me, R.id.nameTV});
        lv.setAdapter(aa);
        br = new BroadcastReceiver() {
            public void onReceive(Context context, Intent intent) {
                String t =intent.getStringExtra("request");
                if(t.equals("stop")){unregisterReceiver(br);t="bye";}
                if(t!=null){
                    Map<String, String> map = new HashMap();
                    map.put("First",et.getText().toString());
                    map.put("Second",t);
                    data.add(map);
                    aa.notifyDataSetChanged();}
            }
        };

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(br);IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
        registerReceiver(br, intFilt);
    }



    public void onClickStart(View v) {
        if(et.getText().toString().equals("hello")){
            IntentFilter intFilt = new IntentFilter(BROADCAST_ACTION);
            registerReceiver(br, intFilt);
        }
        else if(et.getText().equals("bye"))unregisterReceiver(br);
        Intent intent;
        intent = new Intent(this, MyService.class).putExtra("text", et.getText().toString());
        startService(intent);
    }

},nm