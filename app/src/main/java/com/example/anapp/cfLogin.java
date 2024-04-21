package com.example.anapp;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

public class cfLogin extends AppCompatActivity {

    EditText username, password;
    Button button;
    String name, pass;
    WebView webView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cf_login);

        username = findViewById(R.id.CFUsername);
        password = findViewById(R.id.CFPassword);
        button = findViewById(R.id.CFLogin);
        webView = findViewById(R.id.webview);

        WebSettings webSettings = webView.getSettings();
        webSettings.setJavaScriptEnabled(true);
        webView.clearCache(true);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                name = username.getText().toString();
                pass = password.getText().toString();
                loginBegin();
                register();
            }
        });

    }

    private void loginBegin() {

        System.out.println("Button Clicked");
        webView.loadUrl("https://codeforces.com/enter/");
        webView.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                String setName = "javascript:document.getElementById('handleOrEmail').value = '" + name + "';";
                String setPass = "javascript:document.getElementById('password').value = '" + pass + "';";
                String clickBut = "javascript:document.querySelector('.submit').click();";
                view.evaluateJavascript(setName, null);
                view.evaluateJavascript(setPass, null);
                view.evaluateJavascript(clickBut, null);

                try {
                    Thread.sleep(3000);
                } catch(InterruptedException e) {
                    e.printStackTrace();
                }
                
            }
        });
    }

    private void register() {
        System.out.println("registering");

    }
}