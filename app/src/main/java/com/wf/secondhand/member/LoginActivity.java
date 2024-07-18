package com.wf.secondhand.member;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.AssetManager;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.wf.secondhand.MainActivity;
import com.wf.secondhand.data.dao.UserDao;
import com.wf.secondhand.data.pojo.User;
import com.wf.secondhand.databinding.ActivityLoginBinding;

public class LoginActivity extends AppCompatActivity{
    private ActivityLoginBinding binding;
    private TextView tv_register;
    private EditText username,password;
    private CheckBox remember;
    private Button btn;
    private User user =null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //绑定相应的xml视图
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        //设置字体样式
        binding.loginTitle.setTypeface(setFont());
        barHide();
        //注册点击事件
        registerClick();

        //连接相应的控件
        username=binding.loginUsername;
        password=binding.loginPassword;
        remember=binding.loginRemember;
        btn = binding.btnLogin;

        //当注册成功时 登录界面用户名栏直接显示注册的userName
        // 从注册界面获取的userName值
        String temp =getIntent().getStringExtra("userName");
        if (temp != null) {
            username.setText(temp);
        }else {
            SharedPreferences preferences = getSharedPreferences("data",MODE_PRIVATE);
            remember.setChecked(preferences.getBoolean("state",false));
            username.setText(preferences.getString("userName",""));
            password.setText(preferences.getString("password",""));
        }


        //登录的点击事件
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String usernameString=username.getText().toString();
                String passwordString=password.getText().toString();

                //判断是否为空
                if (usernameString.equals("")||passwordString.equals("")) {
                    Toast.makeText(LoginActivity.this,"用户名或密码不能为空!",Toast.LENGTH_SHORT).show();
                    return;
                }

                UserDao dao =new UserDao(LoginActivity.this);
                //判断用户名或密码是否正确
                user=dao.getByName(usernameString);
                if (user==null||!usernameString.equals(user.getUsername())||!passwordString.equals(user.getPassword())){
                    Toast.makeText(LoginActivity.this,"用户名或密码不正确!",Toast.LENGTH_SHORT).show();
                    return;
                }

                //判断是否记住密码
                if (remember.isChecked()){
                    rememberPwd(user.getUsername(),user.getPassword());
                }else {
                    noRememberPwd();
                }

                //跳转到主界面
                Toast.makeText(LoginActivity.this,"登录成功!",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                //添加唯一标识
                uniqueUser(user.getUsername());

                startActivity(intent);
            }
        });

    }

    //注册点击事件
    public void registerClick(){
        tv_register=binding.loginRegister;
        tv_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
            }
        });
    }

    //储存成功登录的用户的唯一标识
    public void uniqueUser(String userName){
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString("unique",userName);
        editor.commit();
    }

    //记住密码功能
    public void rememberPwd(String userName,String password){
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.putString("userName",userName);
        editor.putString("password",password);
        editor.putBoolean("state",true);
        editor.commit();
    }

    //取消记住
    public void noRememberPwd(){
        SharedPreferences.Editor editor = getSharedPreferences("data",MODE_PRIVATE).edit();
        editor.remove("userName");
        editor.remove("password");
        editor.remove("state");
        editor.commit();
    }

    //设置字体
    public Typeface setFont(){
        AssetManager asset=getAssets();
        Typeface tf = Typeface.createFromAsset(asset,"fonts/Alimama_ShuHeiTi_Bold.ttf");
        return tf;
    }

    public void barHide(){
        //隐藏导航栏
        ActionBar actionBar =getSupportActionBar();
        actionBar.hide();
    }
}