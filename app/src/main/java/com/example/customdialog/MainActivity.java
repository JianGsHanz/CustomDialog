package com.example.customdialog;

import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Toast;

import com.example.customdialog.widget.CustomDialog;
import com.example.customdialog.widget.IDialog;
import com.example.customdialog.widget.manager.CustomDialogsManager;
import com.example.customdialog.widget.manager.DialogWrapper;

public class MainActivity extends AppCompatActivity {

    private String mUrl = "http://wxtest.9fbank.com/wkstatic/privacy-policy.html";
    private CustomDialog.Builder mFirstBuilder;
    private CustomDialog.Builder mSecondBuilder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //第一个Dialoog
        mFirstBuilder = new CustomDialog.Builder(this)
                .setTitle("I`M CustomTitle")
                .setContent("yes , I`m Content...")
                .setCancelable(true)//设置是否屏蔽物理返回键 true不屏蔽  false屏蔽
                .setCancelableOutSide(true)//设置dialog外点击是否可以让dialog消失
                .setPositiveButton("确定", new IDialog.OnClickListener() {
                    @Override
                    public void onClick(IDialog dialog) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "确定", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new IDialog.OnClickListener() {
                    @Override
                    public void onClick(IDialog dialog) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "取消", Toast.LENGTH_SHORT).show();
                    }
                });

        //第二个Dialoog
        mSecondBuilder = new CustomDialog.Builder(this)
                .setTitle("I`m CustomTitle")
                .setDialogView(R.layout.web_dialog)//设置dialog布局
                .setScreenWidthP(0.85f) //设置屏幕宽度比例 0.0f-1.0f
                .setGravity(Gravity.CENTER)//设置Gravity
                .setWindowBackgroundP(0.2f)//设置背景透明度 0.0f-1.0f 1.0f完全不透明
                .setNegativeButton("不同意", new IDialog.OnClickListener() {
                    @Override
                    public void onClick(IDialog dialog) {
                        dialog.dismiss();
                        Toast.makeText(MainActivity.this, "不同意", Toast.LENGTH_SHORT).show();
                    }
                }).
                        setPositiveButton("同意", new IDialog.OnClickListener() {
                            @Override
                            public void onClick(IDialog dialog) {
                                dialog.dismiss();
                                Toast.makeText(MainActivity.this, "同意", Toast.LENGTH_SHORT).show();
                            }
                        })
                .setBuildChildListener(new IDialog.OnBuildListener() {
                    //设置子View
                    @Override
                    public void onBuildChildView(final IDialog dialog, View view, int layoutRes) {
                        WebView web = view.findViewById(R.id.web_view);
                        WebSettings webSettings = web.getSettings();
                        webSettings.setJavaScriptEnabled(true);
                        //设置可以支持缩放
                        webSettings.setSupportZoom(true);
                        //设置出现缩放工具
                        webSettings.setBuiltInZoomControls(true);
                        //扩大比例的缩放
                        webSettings.setDisplayZoomControls(false);
                        //设置Web页面字体稳定大小
                        webSettings.setTextZoom(100);
                        webSettings.setBlockNetworkImage(false);
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                            webSettings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
                        }
                        //设置加载进来的页面自适应手机屏幕
                        webSettings.setUseWideViewPort(true);
                        webSettings.setLoadWithOverviewMode(true);
                        //设置WebView缓存模式
                        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
                        web.loadUrl(mUrl);
                    }
                });
    }

    public void onClick(View view) {
        //将创建好的dialog放入队列依次展示
        CustomDialogsManager.getInstance().requestShow(new DialogWrapper(mFirstBuilder));
        CustomDialogsManager.getInstance().requestShow(new DialogWrapper(mSecondBuilder));

    }
}
