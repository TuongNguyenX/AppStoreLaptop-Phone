package com.example.appstorelaptop_phone.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.ViewFlipper;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.appstorelaptop_phone.Adapter.loaispadapter;
import com.example.appstorelaptop_phone.Adapter.sanphamadapter;
import com.example.appstorelaptop_phone.Common.Server;
import com.example.appstorelaptop_phone.Common.checkconnection;
import com.example.appstorelaptop_phone.Model.Giohang;
import com.example.appstorelaptop_phone.Model.Loaisp;
import com.example.appstorelaptop_phone.Model.Sanpham;
import com.example.appstorelaptop_phone.R;
import com.squareup.picasso.Picasso;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ViewFlipper viewFlipper;
    RecyclerView recyclerView;
    ArrayList<Loaisp> mangloaisp;
    com.example.appstorelaptop_phone.Adapter.loaispadapter loaispadapter;
    int id =0;
    String tenloaisp="";
    String hinhanhloaisp="";

    ArrayList<Sanpham> mangsanpham;
    com.example.appstorelaptop_phone.Adapter.sanphamadapter sanphamadapter;
    ListView listViewmanhinhchinh;
    DrawerLayout drawerLayout;

    public static ArrayList<Giohang> manggiohang = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        if(checkconnection.haveNetworkConnection(getApplicationContext())){
            Anhxa();
            getdulieuloaisp();
            GetDuLieuSPMoi();
            CatchOnItemListView();

        }else {
            checkconnection.showtoast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
            finish();

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

    private void CatchOnItemListView() {
        listViewmanhinhchinh.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                switch (position){
                    case 0:
                        if (checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,MainActivity.class);
                        } else{
                            checkconnection.showtoast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 1:
                        if (checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,DienThoaiActivity.class);
                            intent.putExtra("idloaisp",mangloaisp.get(position).getId());
                            startActivity(intent);
                        } else{
                            checkconnection.showtoast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 2:
                        if (checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LaptopActivity.class);
                            intent.putExtra("idloaisp",mangloaisp.get(position).getId());
                            startActivity(intent);
                        } else{
                            checkconnection.showtoast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 3:
                        if (checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,LienHeActivity.class);
//                            intent.putExtra("idloaisp",mangloaisp.get(position).getId());
                            startActivity(intent);
                        } else{
                            checkconnection.showtoast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                    case 4:
                        if (checkconnection.haveNetworkConnection(getApplicationContext())){
                            Intent intent = new Intent(MainActivity.this,ThongTinActivity.class);
//                            intent.putExtra("idloaisp",mangloaisp.get(position).getId());
                            startActivity(intent);
                        } else{
                            checkconnection.showtoast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
                        }
                        drawerLayout.closeDrawer(GravityCompat.START);
                        break;
                }
            }
        });
    }

    private void GetDuLieuSPMoi() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlspmoi, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    int ID =0;
                    String tensp = "";
                    Integer giasp =0;
                    String hinhanhsp = "";
                    String motasp = "";
                    int IDSP =0;
                    for (int i =0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            ID = jsonObject.getInt("id");
                            tensp = jsonObject.getString("tensp");
                            giasp = jsonObject.getInt("giasp");
                            hinhanhsp = jsonObject.getString("hinhanhsp");
                            motasp = jsonObject.getString("motasp");
                            IDSP = jsonObject.getInt("idsanpham");
                            mangsanpham.add(new Sanpham(ID,tensp,giasp,hinhanhsp,motasp,IDSP));
                            sanphamadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Log.d("khoa", "onResponse: "+e.toString());
                        }
                    }
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkconnection.showtoast(getApplicationContext(),error.toString());
                Log.d("khoa", "onResponse: "+error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void getdulieuloaisp() {
        RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(Server.urlloaisp, new Response.Listener<JSONArray>() {
            @Override
            public void onResponse(JSONArray response) {
                if (response != null){
                    for (int i=0;i<response.length();i++){
                        try {
                            JSONObject jsonObject = response.getJSONObject(i);
                            id = jsonObject.getInt("id");
                            tenloaisp = jsonObject.getString("tenloaisp");
                            hinhanhloaisp = jsonObject.getString("hinhanhloaisp");
                            mangloaisp.add(new Loaisp(id,tenloaisp,hinhanhloaisp));
                            loaispadapter.notifyDataSetChanged();
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                    mangloaisp.add(new Loaisp(0,"Liên Hệ","http://icons.iconarchive.com/icons/dtafalonso/android-lollipop/64/Phone-icon.png"));
                    mangloaisp.add(new Loaisp(0,"Thông Tin","http://icons.iconarchive.com/icons/icons-land/vista-people/64/Person-Male-Light-icon.png"));
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                checkconnection.showtoast(getApplicationContext(),error.toString());
            }
        });
        requestQueue.add(jsonArrayRequest);
    }

    private void Anhxa() {
        mangloaisp = new ArrayList<>();
        mangloaisp.add(0,new Loaisp(0,"Home","http://icons.iconarchive.com/icons/custom-icon-design/mono-general-3/64/home-icon.png"));

        listViewmanhinhchinh = findViewById(R.id.listviewmanhinhchinh);

        recyclerView = findViewById(R.id.recyclerview);
        viewFlipper =findViewById(R.id.viewflipper);
        viewFlipper.startFlipping();
        loaispadapter = new loaispadapter(mangloaisp,getApplicationContext());
        listViewmanhinhchinh.setAdapter(loaispadapter);
        drawerLayout = findViewById(R.id.drawer_layout);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar); // dùng khi bỏ thanh menu
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationIcon(android.R.drawable.ic_menu_sort_by_size);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                drawerLayout.openDrawer(GravityCompat.START);
            }
        });

        actionviewflipper();
        mangsanpham = new ArrayList<>();
        sanphamadapter = new sanphamadapter(getApplicationContext(),mangsanpham);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new GridLayoutManager(getApplicationContext(),2));
        recyclerView.setAdapter(sanphamadapter);

        if (manggiohang != null){
            Log.d("rp1", "main err anhxa");
        } else {
            manggiohang = new ArrayList<>();
        }

    }

    private void actionviewflipper() {
        ArrayList<String> mangquangcao = new ArrayList<>();
        mangquangcao.add("https://cellshope.com/wp-content/uploads/2019/05/iphone-banner-min.png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/09/banner/thu-cu-note10-800-300-800x300-(1).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/09/banner/oppo-A9-800-300-800x300-(1).png");
        mangquangcao.add("https://cdn.tgdd.vn/2019/09/banner/huawei-3i-white-800-300-800x300-(1).png");
        for (int i =0;i<mangquangcao.size();i++){
            ImageView imageView = new ImageView(getApplicationContext());
            Picasso.get().load(mangquangcao.get(i)).into(imageView);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            viewFlipper.addView(imageView);
        }
        viewFlipper.setFlipInterval(2000);
        viewFlipper.setAutoStart(true);
        Animation animation_in = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_in_right);
        Animation animation_out = AnimationUtils.loadAnimation(getApplicationContext(),R.anim.slide_out_right);
        viewFlipper.setInAnimation(animation_in);
        viewFlipper.setInAnimation(animation_out);
    }
}