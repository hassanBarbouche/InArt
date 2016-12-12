package com.esprit.android.inart;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.EditText;
import android.widget.Toast;

public class YoutubeActivity extends Activity implements View.OnTouchListener, Handler.Callback {

    private final Handler handler = new Handler(this);
    private static final int CLICK_ON_WEBVIEW = 1;
    private static final int CLICK_ON_URL = 2;
    Bundle link=new Bundle();
    String idyoutube="";
    private WebViewClient client;
    String webUrl="";
    String url="http://m.youtube.com/";
    private WebView myWebView;
    private EditText editText;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.youtube_web);
        myWebView = (WebView) findViewById(R.id.activity_main_webview);
       // WebSettings webSettings = myWebView.getSettings();
       // webSettings.setJavaScriptEnabled(true);
        //myWebView.loadUrl("https://www.youtube.com/");
        //setContentView(myWebView);
        //myWebView.setWebViewClient(new WebViewClient());
        //myWebView.setWebViewClient(new TutorialWebViewClient());
       //editText = (EditText) findViewById(R.id.editText);
       // String a =myWebView.getUrl();
       // editText.setText(a);
//        System.out.println(myWebView.getUrl());
//        editText.setOnEditorActionListener(new TextView.OnEditorActionListener() {
//            @Override
//            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
//                boolean handled = false;
//                if(actionId == EditorInfo.IME_ACTION_SEND) {
//                    myWebView.loadUrl(editText.getText().toString());
//                    System.out.println(myWebView.getUrl());
//                    handled = true;
//
//                }
//                return handled;
//
//            }
//        });
        myWebView.setOnTouchListener(this);
        client = new WebViewClient(){
            @Override public boolean shouldOverrideUrlLoading(WebView view, String url) {
                handler.sendEmptyMessage(CLICK_ON_URL);

                return false;
            }



            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                // TODO Auto-generated method stub

                webUrl = myWebView.getUrl();
                System.out.println("###nasjkxbsa99999999"+webUrl);



                super.onPageStarted(view, url, favicon);


            }
        };


        myWebView.setWebViewClient(client);
        myWebView.setVerticalScrollBarEnabled(false);
        myWebView.loadUrl(url);

        myWebView.getSettings().setJavaScriptEnabled(true);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);

        System.out.println(myWebView.getUrl());
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        System.out.println(myWebView.getUrl());
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if(myWebView.canGoBack()) {
            myWebView.goBack();
        }
        else super.onBackPressed();
    }

    @Override
    public boolean handleMessage(Message msg) {
        if (msg.what == CLICK_ON_URL){
            handler.removeMessages(CLICK_ON_WEBVIEW);
            System.out.println(webUrl);


            return true;
        }
        if (msg.what == CLICK_ON_WEBVIEW){
            handler.removeMessages(CLICK_ON_WEBVIEW);

            editText = (EditText) findViewById(R.id.editTexto);
            Toast.makeText(this, "WebView clicked", Toast.LENGTH_SHORT).show();
            Intent i1 = new Intent(this, addVideo.class);
            webUrl = myWebView.getUrl();
            if(webUrl.contains("/watch?list")) {
                Toast.makeText(this, "ceci est une liste veuillez cliquez sur une video", Toast.LENGTH_SHORT).show();
            }
            if(webUrl.contains("/watch?v=")){
                editText.setText(webUrl);
            i1.putExtra("arg", webUrl);
            setResult(RESULT_OK, i1);
            finish();

            }
            return true;
        }
        return false;
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        handler.sendEmptyMessageDelayed(CLICK_ON_WEBVIEW, 500);
        return false;
    }
}
