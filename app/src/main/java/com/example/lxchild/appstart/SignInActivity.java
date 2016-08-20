package com.example.lxchild.appstart;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.lxchild.healthassistant.MainActivity;
import com.example.lxchild.healthassistant.R;

/**
 * Created by LXChild on 7/24/15.
 */
public class SignInActivity extends ActionBarActivity {


    private static final String PREFS_USER = "userAccount";
    private static final String KEY_ACCOUNT = "account";
    private static final String DEFAULT_TEXT = "";
    private static final String KEY_PASSWORD = "password";

    private EditText et_account;
    private EditText et_password;
    private Button btn_signin;
    private Button btn_register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signin);

        initView();
        initData();
    }


    private void initView() {

        et_account = (EditText) findViewById(R.id.et_accout);
        et_password = (EditText) findViewById(R.id.et_password);

        btn_signin = (Button) findViewById(R.id.btn_signin);
        btn_signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkNull()) {
                    saveUserAccount(et_account.getText().toString(), et_password.getText().toString());
                    signIn();
                }
            }
        });

        btn_register = (Button) findViewById(R.id.btn_register);
        btn_register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String[] a = {"aaa", "aaa"};
                
            }
        });
    }

    private void initData() {

        String account = getSharedPreferences(PREFS_USER, Context.MODE_PRIVATE)
                .getString(KEY_ACCOUNT, DEFAULT_TEXT);
        String password = getSharedPreferences(PREFS_USER, Context.MODE_PRIVATE)
                .getString(KEY_PASSWORD, DEFAULT_TEXT);

        et_account.setText(account);
        et_password.setText(password);

        if (checkNull()) {
            signIn();
        }
    }

    private void signIn() {
            if (checkSignIn()) {
                Intent intent = new Intent();
                intent.setClass(SignInActivity.this, MainActivity.class);
                startActivity(intent);
            } else {
                Toast.makeText(SignInActivity.this, "Please check account/password is correct!", Toast.LENGTH_SHORT).show();
            }
    }

    private boolean checkNull() {
        if (et_account.getText() != null && !et_account.getText().toString().trim().equals("")) {
            if (et_password.getText() != null && !et_password.getText().toString().trim().equals("")) {
                return true;
            } else {
                et_password.setError("Password can't be null");
            }
        } else {
            et_account.setError("Account can't be null");
        }
        return false;
    }

    private boolean checkSignIn() {

        return true;
    }

    private void saveUserAccount(String account, String password) {
        SharedPreferences settings = getSharedPreferences(PREFS_USER, Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString(KEY_ACCOUNT, account);
        editor.putString(KEY_PASSWORD, password);
        editor.apply();
    }
}
