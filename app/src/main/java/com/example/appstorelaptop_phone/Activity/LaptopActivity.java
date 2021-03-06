package com.example.appstorelaptop_phone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appstorelaptop_phone.Adapter.laptopadapter;
import com.example.appstorelaptop_phone.Common.Server;
import com.example.appstorelaptop_phone.Common.checkconnection;
import com.example.appstorelaptop_phone.Model.Sanpham;
import com.example.appstorelaptop_phone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class LaptopActivity extends AppCompatActivity {
    Toolbar toolbarlaptop;
    ListView lvlaptop;
    laptopadapter laptopadapter;
    ArrayList<Sanpham> manglaptop;
    int idlaptop = 0;
    int page =1;
    View footerview;
    boolean isLoading =false; // không cho kéo xuống nhiều lần
    mHandler mHandler;
    boolean limitdata = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_laptop);
        AnhXa();
        if (checkconnection.haveNetworkConnection(getApplicationContext())){
            GetIDLoaiSP();
            ActionToolBar();
            GetData(page);
            LoadMoreData();
        }
        else {
            checkconnection.showtoast(getApplicationContext(),"bạn hãy kiểm tra lại internet");
        }
    }
    private void LoadMoreData() {
        lvlaptop.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChiTietSPActivity.class);
                intent.putExtra("thongtinsp",manglaptop.get(position));
                startActivity(intent);
            }
        });

        lvlaptop.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if (firstVisibleItem + visibleItemCount == totalItemCount && totalItemCount != 0 && isLoading == false && limitdata == false){
                    isLoading = true;
                    ThreadData threadData = new ThreadData();
                    threadData.start();
                }
            }
        });
    }
    private void GetData(int Page) {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        String duongdan = Server.urldienthoai+String.valueOf(Page);
        StringRequest stringRequest = new StringRequest(Request.Method.POST, duongdan, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                int id =0;
                String tenlaptop = "";
                int gialaptop = 0;
                String hinhanhlaptop = "";
                String motalaptop = "";
                int idsplaptop = 0;
                if (response != null && response.length() != 3){
                    lvlaptop.removeFooterView(footerview);
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i =0;i<jsonArray.length();i++){
                            JSONObject jsonObject = jsonArray.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenlaptop = jsonObject.getString("tensp");
                            gialaptop = jsonObject.getInt("giasp");
                            hinhanhlaptop = jsonObject.getString("hinhanhsp");
                            motalaptop = jsonObject.getString("motasp");
                            idsplaptop = jsonObject.getInt("idsanpham");
                            manglaptop.add(new Sanpham(id,tenlaptop,gialaptop,hinhanhlaptop,motalaptop,idsplaptop));
                            laptopadapter.notifyDataSetChanged();
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Log.d("khoa", "onErrorResponse: "+e);
                    }
                }
                else {
                    limitdata = true;
                    lvlaptop.removeFooterView(footerview);
                    Toast.makeText(getApplicationContext(), "đã hết dữ liệu", Toast.LENGTH_SHORT).show();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("khoa", "onErrorResponse: "+error);
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                HashMap<String,String> param = new HashMap<>();
                param.put("idsanpham",String.valueOf(idlaptop));
                return param;
            }
        };
        requestQueue.add(stringRequest);
    }
    private void ActionToolBar() {
        setSupportActionBar(toolbarlaptop);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbarlaptop.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void GetIDLoaiSP() {
        idlaptop = getIntent().getIntExtra("idloaisp",-1);
    }
    private void AnhXa() {
        toolbarlaptop = findViewById(R.id.toolbarlaptop);
        lvlaptop =findViewById(R.id.listviewlaptop);
        manglaptop = new ArrayList<>();
        laptopadapter = new laptopadapter(manglaptop, getApplicationContext());
        lvlaptop.setAdapter(laptopadapter);
        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        footerview = inflater.inflate(R.layout.progressbar,null);
        mHandler = new mHandler();
    }
    public class ThreadData extends Thread{
        @Override
        public void run() {
            mHandler.sendEmptyMessage(0);
            try {
                Thread.sleep(3000);

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            Message message = mHandler.obtainMessage(1);
            mHandler.sendMessage(message);
            super.run();
        }
    }
    public  class mHandler extends Handler {
        @Override
        public void handleMessage(@NonNull Message msg) {
            switch (msg.what){
                case 0:
                    lvlaptop.addFooterView(footerview);
                    break;
                case 1:
                    GetData(++page);
                    isLoading =false;
                    break;
            }
            super.handleMessage(msg);
        }
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menucart,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()){
            case R.id.menugiohang:
                Intent intent = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intent);
        }
        return super.onOptionsItemSelected(item);
    }
}