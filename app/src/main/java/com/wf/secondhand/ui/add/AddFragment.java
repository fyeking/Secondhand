package com.wf.secondhand.ui.add;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.wf.secondhand.data.dao.ProductDao;
import com.wf.secondhand.data.pojo.Product;
import com.wf.secondhand.databinding.FragmentAddBinding;

public class AddFragment extends Fragment {

private FragmentAddBinding binding;


    public View onCreateView(@NonNull LayoutInflater inflater,
            ViewGroup container, Bundle savedInstanceState) {

    binding = FragmentAddBinding.inflate(inflater, container, false);
    View root = binding.getRoot();

        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
       binding.addBtn.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View view) {
               SharedPreferences preferences = getContext().getSharedPreferences("data", Context.MODE_PRIVATE);
               String author = preferences.getString("unique","");
               //处于登录状态
               if (!author.equals("")){
                   String title =binding.addTitle.getText().toString();
                   String content=binding.addContent.getText().toString();
                   String price=binding.addPrice.getText().toString();
                   //判断标题是否为空
                   if (title.equals("")){
                       Toast.makeText(getContext(),"标题不能为空!",Toast.LENGTH_SHORT).show();
                       return;
                   }
                   //判内容是否为空
                   if (content.equals("")){
                       Toast.makeText(getContext(),"内容不能为空!",Toast.LENGTH_SHORT).show();
                       return;
                   }
                   //判断价格是否为空
                   if (price.equals("")){
                       Toast.makeText(getContext(),"价格不能为空!",Toast.LENGTH_SHORT).show();
                       return;
                   }
                   AlertDialog.Builder dialog = new AlertDialog.Builder(getActivity());
                   dialog.setTitle("添加商品");
                   dialog.setMessage("确定要添加吗？");
                   dialog.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {
                           Product pro =new Product(title,content,author,price);
                           ProductDao dao =new ProductDao(getActivity());
                           dao.addPro(pro);
                           Toast.makeText(getContext(),"添加成功",Toast.LENGTH_SHORT).show();
                       }
                   });

                   dialog.setNegativeButton("取消", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialogInterface, int i) {

                       }
                   });
                   dialog.show();
               }else {
                   //未登录状态
                   Toast.makeText(getContext(),"用户未登录",Toast.LENGTH_SHORT).show();
               }

           }
       });

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}