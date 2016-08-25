package com.cws.mvc;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.cws.mvc.bean.User;
import com.cws.mvc.net.UserLoginNet;

/**
 * MVC
 * Created by cws on 2016/8/25.
 */
/*
流程：
1，界面展示
2，用户的输入
3，按钮点击
4，判断用户输入
5，显示滚动条
6，一系列耗时工作
7，隐藏
8，提示用户

存在问题：
Activity中存在两部分内容： 业务相关 + 界面相关
MVC中，V中内容相对较少，C中内容很多

解决方案：
1，如果将Activity中的业务部分拆分 -- MVP
2，如果将Activity中的界面部分拆分 -- MVVM
 */
public class MainActivity extends AppCompatActivity {

    EditText et_username;
    EditText et_password;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        pd = new ProgressDialog(this);
    }

    /**
     * 按钮点击
     * @param v
     */
    public void login(View v){
        final String username = et_username.getText().toString();
        final String password = et_password.getText().toString();

        final User user = new User();
        user.username = username;
        user.password = password;

        if (checkUserInfo(user)) {
            // 登陆
            pd.show();
            new Thread() {
                @Override
                public void run() {
                    UserLoginNet net = new UserLoginNet();
                    if (net.sendUserInfo(user)) {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 登陆成功
                                pd.dismiss();
                                showToast("登陆成功");
                            }
                        });
                    } else {
                        runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                // 登陆失败
                                pd.dismiss();
                                showToast("用户名或密码不正确");
                            }
                        });
                    }
                }
            }.start();
        } else {
            showToast("请输入完整信息");
        }

    }

    /**
     * 判断用户输入
     * @return
     * @param user
     */
    private boolean checkUserInfo(User user) {
        if (TextUtils.isEmpty(user.username) || TextUtils.isEmpty(user.password)) {
            return false;
        }
        return true;
    }

    /**
     * 显示toast
     * @param msg
     */
    private void showToast(String msg) {
        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

}
