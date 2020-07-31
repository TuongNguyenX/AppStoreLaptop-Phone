package com.example.appstorelaptop_phone.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appstorelaptop_phone.Activity.GioHangActivity;
import com.example.appstorelaptop_phone.Activity.MainActivity;
import com.example.appstorelaptop_phone.Model.Giohang;
import com.example.appstorelaptop_phone.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class giohangadapter extends BaseAdapter {
    Context context;
    ArrayList<Giohang> arrayListgiohang;

    public giohangadapter(Context context, ArrayList<Giohang> arrayListgiohang) {
        this.context = context;
        this.arrayListgiohang = arrayListgiohang;
    }

    @Override
    public int getCount() {
        return arrayListgiohang.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListgiohang.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class ViewHolder{
        public TextView txttengh,txtgiagh;
        public ImageView imggh;
        public Button btnplus,btnvalues,btnminus;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder = null;
        if (viewHolder== null){
            viewHolder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_giohang,null);
            viewHolder.txttengh = convertView.findViewById(R.id.textviewtengiohang);
            viewHolder.txtgiagh = convertView.findViewById(R.id.textviewgiagiohang);
            viewHolder.imggh = convertView.findViewById(R.id.imageviewgiohang);
            viewHolder.btnminus = convertView.findViewById(R.id.buttonminus);
            viewHolder.btnplus = convertView.findViewById(R.id.buttonplus);
            viewHolder.btnvalues = convertView.findViewById(R.id.buttonvalues);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Giohang giohang = (Giohang) getItem(position);
        viewHolder.txttengh.setText(giohang.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgiagh.setText(decimalFormat.format(giohang.getGiasp())+" đ");
        Picasso.get().load(giohang.getHinhsp())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(viewHolder.imggh);
        viewHolder.btnvalues.setText(giohang.getSoluongsp()+ "");
        int sl = Integer.parseInt(viewHolder.btnvalues.getText().toString());
        if (sl>=10){
            viewHolder.btnplus.setVisibility(View.INVISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        } else if (sl<=1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.INVISIBLE);
        } else if (sl>1){
            viewHolder.btnplus.setVisibility(View.VISIBLE);
            viewHolder.btnminus.setVisibility(View.VISIBLE);
        }
        final ViewHolder finalViewHolder = viewHolder;
        viewHolder.btnplus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi = Integer.parseInt(finalViewHolder.btnvalues.getText().toString())+1;
                int slht = MainActivity.manggiohang.get(position).getSoluongsp();
                long  giaht = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(slmoi);
                long giamoinhat = giaht * slmoi / slht;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagh.setText(decimalFormat.format(giamoinhat)+" đ");
                GioHangActivity.EventUltil();
                if (slmoi >9){
                    finalViewHolder.btnplus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoi));
                } else {
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoi));
                }
            }
        });
        viewHolder.btnminus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int slmoi = Integer.parseInt(finalViewHolder.btnvalues.getText().toString())-1;
                int slht = MainActivity.manggiohang.get(position).getSoluongsp();
                long  giaht = MainActivity.manggiohang.get(position).getGiasp();
                MainActivity.manggiohang.get(position).setSoluongsp(slmoi);
                long giamoinhat = giaht * slmoi / slht;
                MainActivity.manggiohang.get(position).setGiasp(giamoinhat);
                DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
                finalViewHolder.txtgiagh.setText(decimalFormat.format(giamoinhat)+" đ");
                GioHangActivity.EventUltil();
                if (slmoi <2){
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.INVISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoi));
                } else {
                    finalViewHolder.btnplus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnminus.setVisibility(View.VISIBLE);
                    finalViewHolder.btnvalues.setText(String.valueOf(slmoi));
                }
            }
        });
        return convertView;
    }
}

