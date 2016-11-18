package com.lyp.networkhelper.activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Toast;

import com.lyp.networkhelper.R;
import com.lyp.networkhelper.adapter.ItemAdapter;
import com.lyp.networkhelper.bean.ItemBean;
import com.lyp.networkhelper.view.DefaultLoadingLayout;
import com.lyp.networkhelper.view.NetworkHelperLayout;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends BaseActivity {

    private RecyclerView rcView;
    private List<ItemBean> mData = new ArrayList<>();
    private ItemAdapter mAdapter;

    private DefaultLoadingLayout mLayout;

    private Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            int count = (Integer) msg.obj;
            if (count > 0) {
                mLayout.onDone();
            } else {
                mLayout.onEmpty();
            }
        }
    };

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initData() {
        mLayout.onLoading();
        new Thread(new Runnable() {
            @Override
            public void run() {
                downloadData();

            }
        }).start();

        mAdapter = new ItemAdapter(this, mData);
        rcView.setAdapter(mAdapter);


    }

    private void downloadData() {
        try {
            Thread.sleep(2000);
            for (int i = 0; i < (int)(Math.random()*10); i++) {
                mData.add(new ItemBean("模拟数据"+i, "数据内容"+i, "时间"+i));
            }
            Message msg = new Message();
            msg.obj = mData.size();
            mHandler.sendMessage(msg);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void initViews() {
        rcView = findView(R.id.rcView);
        rcView.setLayoutManager(new LinearLayoutManager(this));

        mLayout = (DefaultLoadingLayout)NetworkHelperLayout.createDefaultLayout(MainActivity.this, rcView);
    }

    @Override
    protected void initEvents() {
        mAdapter.setOnItemClickListener(new ItemAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Toast.makeText(MainActivity.this, ""+position, Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onItemLongClick(View view, int position) {

            }
        });
    }
}
