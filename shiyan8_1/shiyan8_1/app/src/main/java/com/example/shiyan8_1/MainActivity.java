package com.example.shiyan8_1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

/**
 * 实验8-1 SharedPreferences进行简单数据存储
 */
public class MainActivity extends AppCompatActivity {
    //账号，密码
    private EditText account, password;
    //记住密码和自动登录
    private CheckBox rempass, autologin;
    //登录
    private Button button;
    //用户名文本，密码文本
    private String userNameValue, passwordValue;
    //sharedPreferences，轻量型存储
    private SharedPreferences sharedPreferences;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        /**
         * 参数1：文件信息存放文件
         * 参数2：操作模式MODE_PRIVATE：私有模式，新内容覆盖原内容
         */
        sharedPreferences = getSharedPreferences("userInfo", MODE_PRIVATE);
        account = findViewById(R.id.edit_account);
        password = findViewById(R.id.edit_password);
        rempass = findViewById(R.id.checkbox_rememberpass);
        autologin = findViewById(R.id.checkbox_againlogin);
        button = findViewById(R.id.button_login);

        //判断记住密码多选框的状态
        if (sharedPreferences.getBoolean("ISCHECK", false)) {
            //设置默认是记录密码状态
            rempass.setChecked(true);
            account.setText(sharedPreferences.getString("USER_NAME", ""));
            password.setText(sharedPreferences.getString("PASSWORD", ""));
            //判断自动登陆多选框状态
            if (sharedPreferences.getBoolean("AUTO_ISCHECK", false)) {
                //设置默认是自动登录状态
                autologin.setChecked(true);
                //跳转界面
                Intent intent = new Intent(MainActivity.this, Login.class);
                MainActivity.this.startActivity(intent);

            }
        }

        // 登录监听事件  现在默认为用户名为：wyq 密码：123
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("步骤2", "1");
                userNameValue = account.getText().toString();
                passwordValue = password.getText().toString();

                if (userNameValue.equals("wyq") && passwordValue.equals("123")) {
                    Toast.makeText(MainActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                    //登录成功和记住密码框为选中状态才保存用户信息，即rempass.isChecked()为true时候进入if语句
                    if (rempass.isChecked()) {
                        //记住用户名、密码、
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("USER_NAME", userNameValue);
                        editor.putString("PASSWORD", passwordValue);
                        editor.apply();
                    }
                    //跳转界面
                    Intent intent = new Intent(MainActivity.this, Login.class);
                    MainActivity.this.startActivity(intent);
                } else {
                    Toast.makeText(MainActivity.this, "用户名或密码错误，请重新登录", Toast.LENGTH_LONG).show();
                }
            }
        });

        //监听记住密码多选框按钮事件
        rempass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rempass.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("ISCHECK", true);
                    editor.apply();

                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("ISCHECK", false);
                    editor.apply();
                }
            }
        });
        autologin.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (autologin.isChecked()) {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("AUTO_ISCHECK", true);
                    editor.apply();
                } else {
                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putBoolean("AUTO_ISCHECK", false);
                    editor.apply();
                }
            }
        });
    }
}
