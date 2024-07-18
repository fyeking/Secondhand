package com.wf.secondhand.data.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.wf.secondhand.R;
import com.wf.secondhand.data.pojo.Product;

import java.util.List;

public class ProductAdapter extends BaseAdapter {
    private List<Product>  list;
    private Context context;

    public ProductAdapter(List list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return list.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        View view1= LayoutInflater.from(context).inflate(R.layout.layout_product,null);
        TextView title=view1.findViewById(R.id.product_title);
        TextView author=view1.findViewById(R.id.product_author);
        TextView price=view1.findViewById(R.id.product_price);
        ImageView image = view1.findViewById(R.id.product_img);
        title.setText(list.get(i).getTitle());
        author.setText(list.get(i).getAuthor());
        price.setText(list.get(i).getPrice());
//        image.setImageResource(R.drawable.switch_game);
        return view1;
    }
}
