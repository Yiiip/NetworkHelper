package com.lyp.networkhelper.activity;

import android.app.Activity;
import android.content.Context;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Toast;

import com.lyp.networkhelper.receiver.NetworkReceiver;


/**
 * Created by lyp on 2016/4/26.
 */
public abstract class BaseActivity extends AppCompatActivity {

    protected String TAG = getClass().getSimpleName();

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        this.setTranslucentStatusBar(this, false);

        if (getLayoutId() != 0) {
            setContentView(getLayoutId());
        }


        initViews();
        initData();
        initEvents();

        registerNetworkReceiver();
    }


    /**
     * 设置activity布局
     *
     * @return 返回activity的layout资源文件id，没有布局返回0
     */
    protected abstract int getLayoutId();


    /**
     * 初始化数据
     */
    protected abstract void initData();

    /**
     * 初始化view，并设置属性
     */
    protected abstract void initViews();

    /**
     * 设置view的事件监听
     */
    protected abstract void initEvents();


    /**
     * 为toolbar设置返回按钮
     * @param toolbar
     */
    protected void setToolbarWithBack(Toolbar toolbar, int colorRes) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        if (colorRes != 0) {
            toolbar.setBackgroundColor(getResources().getColor(colorRes));
        }
    }

    /**
     * 通过泛型来简化findViewById
     */
    protected final <E extends View> E findView(int id) {
        try {
            return (E) findViewById(id);
        } catch (ClassCastException ex) {
            Log.e(TAG, "Could not cast View to concrete class.", ex);
            throw ex;
        }
    }


    /**
     * 注册网络监听
     */
    private void registerNetworkReceiver()
    {
        NetworkReceiver receiver = NetworkReceiver.getNetWorkReceiver();
        IntentFilter filter = new IntentFilter();
        filter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
        filter.addAction("android.gzcpc.conn.CONNECTIVITY_CHANGE");
        registerReceiver(receiver, filter);
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        unregisterReceiver(NetworkReceiver.getNetWorkReceiver());
    }

    /**
     * Android 4.4 以上的版本实现系统状态栏透明
     * @param activity
     */
    public static void setTranslucentStatusBar(Activity activity, Boolean b) {
        if (b) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                activity.getWindow().setFlags(
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,
                        WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            }
        }
    }


    public void toast(String text) {
        Toast.makeText(mContext, text, Toast.LENGTH_SHORT).show();
    }

    public void toast(int resId) {
        toast(mContext.getResources().getText(resId)+"");
    }
}
