package com.example.opengles.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.LogUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.opengles.R;
import com.example.opengles.adapter.SimpleHomeAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainAct extends AppCompatActivity {
    //String url = "https://ss0.bdstatic.com/70cFuHSh_Q1YnxGkpoWK1HF6hhy/it/u=532808234,3202881386&fm=27&gp=0.jpg";
    List<String> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_main);

        initData();
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.gl_rv);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        SimpleHomeAdapter simpleHomeAdapter = new SimpleHomeAdapter(R.layout.item_simple_main_adapter, dataList);

        simpleHomeAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        simpleHomeAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.e("点击" + position);
            }
        });

        simpleHomeAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                LogUtils.e(position);
            }
        });

        recyclerView.setAdapter(simpleHomeAdapter);
    }

    private void initData() {
        dataList = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            dataList.add(String.valueOf((char) i));
        }
    }
}
