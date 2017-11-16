package com.example.as.uestc.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.as.uestc.Answer.view.AnswerActivity;
import com.example.as.uestc.R;
import com.example.as.uestc.login.bean.Login;
import com.example.as.uestc.login.bean.PostUser;
import com.example.as.uestc.login.model.LoginModelImpl;
import com.example.as.uestc.login.presenter.LoginPresenterImpl;
import com.example.as.uestc.login.view.LoginView;

public class LoginActivity extends AppCompatActivity implements LoginView {

    private Login login;
    private EditText username,pass;
    private Button signin;
    private LoginPresenterImpl presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setFinishOnTouchOutside(false);
        setContentView(R.layout.activity_login);
        presenter=new LoginPresenterImpl();
        presenter.attach(this,new LoginModelImpl());
        intiView();
    }

    private void intiView()
    {
        username=(EditText)findViewById(R.id.login_username);
        pass=(EditText)findViewById(R.id.login_passwords);
        signin=(Button) findViewById(R.id.login_signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PostUser user=new PostUser(username.getText().toString(),pass.getText().toString());
                //presenter.loginV(user,back);
                presenter.login(user);
            }
        });
    }
    @Override
    public void finishLogin(Login login) {
        onFinish(login);
    }

    public void onFinish(Login login)
    {
        Intent intent=new Intent(LoginActivity.this,AnswerActivity.class);
        intent.putExtra("token",login.getToken());
        startActivity(intent);
        finish();
    }

    /**
     * 登录状态码返回不是0的时候显示toast提示
     * @param login
     */
    public void showToast(Login login)
    {
        Toast.makeText(this,login.getErrcode()+":"+login.getErrmsg(),Toast.LENGTH_SHORT).show();
    }
}
