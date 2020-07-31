package com.example.appstorelaptop_phone.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.appstorelaptop_phone.Activity.ChiTietSPActivity;
import com.example.appstorelaptop_phone.Common.checkconnection;
import com.example.appstorelaptop_phone.Model.Sanpham;
import com.example.appstorelaptop_phone.R;
import com.squareup.picasso.Picasso;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class sanphamadapter extends RecyclerView.Adapter<sanphamadapter.ItemHolder> {
    Context context;
    ArrayList<Sanpham> arrayListsp;

    public sanphamadapter(Context context, ArrayList<Sanpham> arrayListsp) {
        this.context = context;
        this.arrayListsp = arrayListsp;
    }

    @NonNull
    @Override
    public ItemHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.dong_sanphammoi,null);
        ItemHolder itemHolder = new ItemHolder(v);
        return itemHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ItemHolder holder, int position) {
        Sanpham sanpham = arrayListsp.get(position);
        holder.txttensp.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        holder.txtgiasp.setText("Giá: "+ decimalFormat.format(sanpham.getGiasp()));
        Picasso.get().load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(holder.imgsanpham);
    }

    @Override
    public int getItemCount() {

        return arrayListsp.size();
    }

    public class ItemHolder extends  RecyclerView.ViewHolder{
        public ImageView imgsanpham;
        public TextView txttensp,txtgiasp;


        public ItemHolder(@NonNull final View itemView) {
            super(itemView);
            imgsanpham = itemView.findViewById(R.id.imageviewsp);
            txttensp = itemView.findViewById(R.id.textviewtensp);
            txtgiasp = itemView.findViewById(R.id.textviewgiasp);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, ChiTietSPActivity.class);
                    intent.putExtra("thongtinsp",arrayListsp.get(getPosition()));
                    checkconnection.showtoast(context,arrayListsp.get(getPosition()).getTensp());
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);
                }
            });
        }
    }
}

