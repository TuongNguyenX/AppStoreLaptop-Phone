package com.example.appstorelaptop_phone.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.appstorelaptop_phone.Common.Server;
import com.example.appstorelaptop_phone.Common.checkconnection;
import com.example.appstorelaptop_phone.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ThongTinKHActivity extends AppCompatActivity {
    EditText edttenkh,edtsdt,edtemail;
    Button btnxacnhan,btntrove;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thong_tin_k_h);
        AnhXa();
        if (checkconnection.haveNetworkConnection(getApplicationContext())){
            EventButton();
        } else {
            checkconnection.showtoast(getApplicationContext(),"Bạn hãy kiểm tra lại kết nối");
        }
        btntrove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
    private void EventButton() {
        btnxacnhan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String tenkh = edttenkh.getText().toString().trim();
                final String sdt = edtsdt.getText().toString().trim();
                final String email = edtemail.getText().toString().trim();
                if (tenkh.length()>0 && sdt.length()>0 && email.length()>0){
                    final RequestQueue requestQueue = Volley.newRequestQueue(getApplicationContext());
                    StringRequest stringRequest = new StringRequest(Request.Method.POST, Server.urldonhang, new Response.Listener<String>() {
                        @Override
                        public void onResponse(final String madonhang) {
                            Log.d("madonhang", "mdh "+madonhang);

                            if (madonhang.length()>0){//Integer.parseInt(madonhang) >0
                                RequestQueue queue = Volley.newRequestQueue(getApplicationContext());
                                StringRequest request = new StringRequest(Request.Method.POST, Server.urlchitietdonhang, new Response.Listener<String>() {
                                    @Override
                                    public void onResponse(final String response) {
                                        Log.d("rp1", response);
                                        if (response.length()>0){//response.equals("1")
//                                            Log.d("rp1", "vào if 1");
                                            MainActivity.manggiohang.clear();
                                            checkconnection.showtoast(getApplicationContext(),"Bạn đã thêm dữ liệu giỏ hàng thành công");
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            checkconnection.showtoast(getApplicationContext(),"Mời bạn tiếp tục mua hàng");
                                        } else {
                                            checkconnection.showtoast(getApplicationContext(),"Dữ liệu giỏ hàng của bạn bị lỗi !");
                                            Log.d("rp1", "errr else");
                                        }
                                    }
                                }, new Response.ErrorListener() {
                                    @Override
                                    public void onErrorResponse(VolleyError error) {
                                        checkconnection.showtoast(getApplicationContext(),"Dữ liệu giỏ hàng của bạn bị lỗi ! 22");
                                        Log.d("madonhang", "errr onErrorResponse");
                                    }
                                }) {
                                    @Override
                                    protected Map<String, String> getParams() throws AuthFailureError {
                                        JSONArray jsonArray = new JSONArray();
                                        for (int i=0; i<MainActivity.manggiohang.size();i++){
                                            JSONObject jsonObject = new JSONObject();
                                            try {
                                                jsonObject.put("madonhang",madonhang);
                                                jsonObject.put("masanpham",MainActivity.manggiohang.get(i).getIdsp());
                                                jsonObject.put("tensanpham",MainActivity.manggiohang.get(i).getTensp());
                                                jsonObject.put("soluongsanpham",MainActivity.manggiohang.get(i).getSoluongsp());
                                                jsonObject.put("giasanpham",MainActivity.manggiohang.get(i).getGiasp());
                                            } catch (JSONException e) {
                                                e.printStackTrace();
                                            }
                                            jsonArray.put(jsonObject);
                                        }
                                        HashMap<String,String> hashMap = new HashMap<String, String>();
                                        hashMap.put("json",jsonArray.toString());
                                        return hashMap;
                                    }
                                };
                                queue.add(request);
                            }
                            else {
                                Log.d("rp1", "errr else madonhang");
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }){
                        //don hang
                        @Override
                        protected Map<String, String> getParams() throws AuthFailureError {
                            HashMap<String,String> hashMap = new HashMap<String, String>();
                            hashMap.put("tenkhachhang",tenkh);
                            hashMap.put("sodienthoai",sdt);
                            hashMap.put("email",email);
                            return hashMap;
                        }
                    };// don hang nay
                    requestQueue.add(stringRequest);
                } else {
                    checkconnection.showtoast(getApplicationContext(),"Hãy kiểm tra lại dữ liệu");

                }
            }
        });
    }

    private void AnhXa() {
        edttenkh = findViewById(R.id.edittexttenkhachhang);
        edtsdt = findViewById(R.id.edittextsodienthoaikhachhang);
        edtemail = findViewById(R.id.edittextemail);
        btntrove = findViewById(R.id.btntrove);
        btnxacnhan = findViewById(R.id.btnxacnhan);
    }
}