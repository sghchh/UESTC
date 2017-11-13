package com.example.as.uestc.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

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
    private TextView signin,back;
    private LoginPresenterImpl presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        presenter=new LoginPresenterImpl();
        presenter.attach(this,new LoginModelImpl());
        intiView();
    }

    private void intiView()
    {
        username=(EditText)findViewById(R.id.login_username);
        pass=(EditText)findViewById(R.id.login_passwords);
        back=(TextView)findViewById(R.id.login_back);
        signin=(TextView)findViewById(R.id.login_signin);
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                PostUser user=new PostUser(username.getText().toString(),pass.getText().toString());
                //presenter.loginV(user,back);
                presenter.login(user);
                //presenter.login(user);
                //Intent intent=new Intent(LoginActivity.this,AnswerActivity.class);
                //intent.putExtra("token",login.getToken());
                //startActivity(intent);
                //finish();
            }
        });
    }
    @Override
    public void saveLogin(Login login) {
        this.login=login;
    }

    public void onFinish()
    {
        Intent intent=new Intent(LoginActivity.this,AnswerActivity.class);
        intent.putExtra("token",login.getToken());
        startActivity(intent);
        finish();
    }
}
