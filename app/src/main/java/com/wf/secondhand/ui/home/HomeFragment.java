package com.wf.secondhand.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wf.secondhand.BlurbActivity;
import com.wf.secondhand.data.adapter.ProductAdapter;
import com.wf.secondhand.data.dao.ProductDao;
import com.wf.secondhand.data.pojo.Product;
import com.wf.secondhand.databinding.FragmentHomeBinding;

import java.util.List;

public class HomeFragment extends Fragment {

private FragmentHomeBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {
    binding = FragmentHomeBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        getListAll("");


        //模糊查找的点击事件
        binding.homeBtnFind.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String title=binding.homeInputProduct.getText().toString();
                getListAll(title);
            }
        });


    }


    //查列表全部及子项的点击事件
    public void getListAll(String title){
        ProductDao productDao =new ProductDao(getActivity());

        //数据源
        List<Product> list=productDao.getProAll(title);

        //适配器
        ProductAdapter adapter = new ProductAdapter(list,getActivity());

        binding.homeProduct.setAdapter(adapter);

        binding.homeProduct.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getActivity(), BlurbActivity.class);
                intent.putExtra("product",list.get(i));
                startActivity(intent);
            }
        });


    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}