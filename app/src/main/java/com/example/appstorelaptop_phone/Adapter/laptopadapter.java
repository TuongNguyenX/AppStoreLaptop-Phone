package com.example.appstorelaptop_phone.Adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.appstorelaptop_phone.Model.Sanpham;
import com.example.appstorelaptop_phone.R;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.text.DecimalFormat;
import java.util.ArrayList;

public class laptopadapter extends BaseAdapter implements Serializable {
    ArrayList<Sanpham> arrayListlaptop;
    Context context;

    public laptopadapter(ArrayList<Sanpham> arrayListlaptop, Context context) {
        this.arrayListlaptop = arrayListlaptop;
        this.context = context;
    }

    @Override
    public int getCount() {
        return arrayListlaptop.size();
    }

    @Override
    public Object getItem(int position) {
        return arrayListlaptop.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    public class ViewHolder{
        public TextView txttenlaptop,txtgialaptop,txtmotalaptop;
        public ImageView imglaptop;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        laptopadapter.ViewHolder viewHolder = null;
        if (convertView == null){
            viewHolder = new laptopadapter.ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.dong_laptop,null);
            viewHolder.txttenlaptop = convertView.findViewById(R.id.textviewlaptop);
            viewHolder.txtgialaptop = convertView.findViewById(R.id.textviewgialaptop);
            viewHolder.txtmotalaptop = convertView.findViewById(R.id.textviewmotalaptop);
            viewHolder.imglaptop = convertView.findViewById(R.id.imglaptop);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (laptopadapter.ViewHolder) convertView.getTag();
        }
        Sanpham sanpham = (Sanpham) getItem(position);
        viewHolder.txttenlaptop.setText(sanpham.getTensp());
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        viewHolder.txtgialaptop.setText("Giá: "+ decimalFormat.format(sanpham.getGiasp()));
        viewHolder.txtmotalaptop.setMaxLines(2);
        viewHolder.txtmotalaptop.setEllipsize(TextUtils.TruncateAt.END);
        viewHolder.txtmotalaptop.setText(sanpham.getMotasp());
        Picasso.get().load(sanpham.getHinhanhsp())
                .placeholder(R.drawable.ic_launcher_background)
                .error(R.drawable.ic_launcher_foreground)
                .into(viewHolder.imglaptop);
        return convertView;
    }
}

