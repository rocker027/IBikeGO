package com.coors.ibikego.bikemode;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.coors.ibikego.R;

public class PokemonActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pokemon);
        WebView mWebView = (WebView) findViewById(R.id.webview);
        mWebView.loadUrl("https://tw.appx.hk/map");
        mWebView.getSettings().setJavaScriptEnabled(true);

        WebViewClient mWebViewClient = new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                view.loadUrl(url);
                return true;
            }
        };
    }
}
