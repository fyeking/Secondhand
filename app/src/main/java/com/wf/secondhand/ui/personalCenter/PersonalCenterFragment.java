package com.wf.secondhand.ui.personalCenter;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.wf.secondhand.BlurbActivity;
import com.wf.secondhand.MainActivity;
import com.wf.secondhand.R;
import com.wf.secondhand.data.adapter.ProductAdapter;
import com.wf.secondhand.data.dao.ProductDao;
import com.wf.secondhand.data.pojo.Product;
import com.wf.secondhand.databinding.FragmentPersonalCenterBinding;
import com.wf.secondhand.member.LoginActivity;

import java.util.List;

public class PersonalCenterFragment extends Fragment {

private FragmentPersonalCenterBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentPersonalCenterBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        //判断是否为登录状态 执行相应的操作
        getUnique();

        //字体
        binding.centerCutLeft.setTypeface(setFont());
        binding.centerCutRight.setTypeface(setFont());
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onResume() {
        super.onResume();
        getUnique();
    }


    @Override
    public void onStart() {
        super.onStart();
        getUnique();
    }


    public void getUnique(){
        SharedPreferences preferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
        String name =preferences.getString("unique","");
        //判断是否为登录状态
        if (!name.equals("")) {
            //登录状态
            binding.centerName.setText(name);
            binding.centerName.setTextColor(this.getResources().getColor(R.color.black));
            binding.centerName.setTypeface(setFont());

            //加载个人列表
            personalProduct(name);

            //退出按钮
            TextView textView =binding.centerLogOut;
            textView.setVisibility(View.VISIBLE);
            textView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //清除唯一标识
                    SharedPreferences.Editor editor = preferences.edit();
                    editor.remove("unique");
                    editor.commit();
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);
                }
            });
        }else {
            //未登录状态
            binding.centerName.setText("未登录");
            binding.centerName.setTextColor(this.getResources().getColor(R.color.login_click_style));
            binding.centerName.setTypeface(setFont());

            binding.centerLogOut.setVisibility(View.GONE);

            //取消个人列表
            personalProduct(name);

            //个人中心的未登录的点击事件 跳转到登录界面
            TextView login=binding.centerName;
            login.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getActivity(), LoginActivity.class);
                    startActivity(intent);
                }
            });
        }
    }

    //加载个人商品列表

    public void personalProduct(String author){
        ProductDao dao =new ProductDao(getActivity());
        //根据username查商品
        List<Product> list =dao.getProByAuthor(author);
        itemClickPro(list);

        //点击"我买的"的点击事件
        binding.centerCutRight.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.centerCutRight.setBackgroundResource(R.drawable.center_btn_ture);
                binding.centerCutLeft.setBackgroundResource(R.drawable.center_btn_false);
                list.clear();
                List<Product> list =dao.getTran(author);
                itemClickPro(list);
            }
        });
        //点击"我卖的"的点击事件
        binding.centerCutLeft.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                binding.centerCutLeft.setBackgroundResource(R.drawable.center_btn_ture);
                binding.centerCutRight.setBackgroundResource(R.drawable.center_btn_false);
                list.clear();
                List<Product> list =dao.getProByAuthor(author);
                itemClickPro(list);
            }
        });




    }



    //子项点击事件
    public void itemClickPro(List<Product> list){
        ProductAdapter adapter = new ProductAdapter(list,getActivity());
        binding.centerPersonalProduct.setAdapter(adapter);
        binding.centerPersonalProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), BlurbActivity.class);
                intent.putExtra("product",list.get(i));
                startActivity(intent);
            }
        });
    }

    //字体
    public Typeface setFont(){
        AssetManager asset= getActivity().getAssets();
        Typeface tf = Typeface.createFromAsset(asset,"fonts/Alimama_ShuHeiTi_Bold.ttf");
        return tf;
    }
}