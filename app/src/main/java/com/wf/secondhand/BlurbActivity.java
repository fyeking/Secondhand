package com.wf.secondhand;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.wf.secondhand.data.dao.ProductDao;
import com.wf.secondhand.data.pojo.Product;
import com.wf.secondhand.databinding.ActivityBlurbBinding;

public class BlurbActivity extends AppCompatActivity {
    private ActivityBlurbBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityBlurbBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        barHide();
        Product product =(Product) getIntent().getSerializableExtra("product");
        binding.blurbTitle.setText(product.getTitle());
        binding.blurbAuthor.setText(product.getAuthor());
        binding.blurbContent.setText(product.getContent());
        binding.blurbPrice.setText(product.getPrice());
        SharedPreferences preferences =getSharedPreferences("data", Context.MODE_PRIVATE);
        String name =preferences.getString("unique","");
        if (name.equals(product.getAuthor())){
            binding.blurbWant.setVisibility(View.GONE);
        }

        binding.blurbWant.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (name.equals("")) {
                    Toast.makeText(BlurbActivity.this,"未登录",Toast.LENGTH_SHORT).show();
                    return;
                }

                AlertDialog.Builder dialog = new AlertDialog.Builder(BlurbActivity.this);
                dialog.setTitle("购买商品");
                dialog.setMessage("确定要购买吗？");
                dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        ProductDao dao =new ProductDao(BlurbActivity.this);
                        dao.transaction(product.getId(),name);
                        Toast.makeText(BlurbActivity.this,"购买成功",Toast.LENGTH_SHORT).show();
                    }
                });

                dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {

                    }
                });
                dialog.show();
            }
        });
    }



    //隐藏导航栏
    public void barHide(){
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
    }
}