package com.wf.secondhand.member;


import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.wf.secondhand.data.dao.UserDao;
import com.wf.secondhand.data.pojo.User;
import com.wf.secondhand.databinding.ActivityRegisterBinding;

public class RegisterActivity extends AppCompatActivity {
    private ActivityRegisterBinding binding;
    private EditText username,password,confirmPwd,email,phone;
    private Button btn;
    private User user=null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定相应的xml视图
        binding= ActivityRegisterBinding.inflate(getLayoutInflater());
        //设置上下文
        setContentView(binding.getRoot());

        //连接相应的控件
        username=binding.registerUsername;
        password=binding.registerPassword;
        confirmPwd=binding.registerConfirmPwd;
        email=binding.registerEmail;
        phone=binding.registerPhone;
        btn=binding.btnRegister;

        //注册按钮点击事件
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //获取相应的控件输入的值
                user =new User(username.getText().toString(),password.getText().toString(),email.getText().toString(),phone.getText().toString());
                String confirm=confirmPwd.getText().toString();
                //判断username 和password是否为空
                if (user.getUsername().equals("")||user.getPassword().equals("")) {
                    Toast.makeText(RegisterActivity.this,"用户名或密码不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断email是否为空
                if (user.getEmail() .equals("")) {
                    Toast.makeText(RegisterActivity.this,"电子邮箱不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断phone是否为空
                if (user.getPhone().equals("")) {
                    Toast.makeText(RegisterActivity.this,"手机号码不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }
                //判断密码是否一致
                if (!user.getPassword().equals(confirm)){
                    Toast.makeText(RegisterActivity.this,"密码不一致!",Toast.LENGTH_SHORT).show();
                    return;
                }
                UserDao dao =new UserDao(RegisterActivity.this);
                //判断用户是否存在
                if (dao.existsName(user.getUsername())){
                    Toast.makeText(RegisterActivity.this,"用户名存在!",Toast.LENGTH_SHORT).show();
                    return;
                }

                //将注册数据插入user表
                dao.insert(user);

                //跳转到登录界面
                Toast.makeText(RegisterActivity.this,"注册成功!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                intent.putExtra("userName",user.getUsername());
                startActivity(intent);
            }
        });


        barHide();
    }

    public void barHide(){
        //隐藏导航栏
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
    }
}