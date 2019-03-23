package com.example.opengles.act;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.blankj.utilcode.util.ToastUtils;
import com.chad.library.adapter.base.BaseQuickAdapter;
import com.example.opengles.R;
import com.example.opengles.adapter.HomeSimpleAdapter;

import java.util.ArrayList;
import java.util.List;

public class HomeSimpleAct extends AppCompatActivity {
    private List<String> dataList;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.act_home_simple);

        initData();
        initView();
    }

    private void initView() {
        RecyclerView recyclerView = findViewById(R.id.rv_home_simple);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        HomeSimpleAdapter homeSimpleAdapter = new HomeSimpleAdapter(R.layout.item_home_adapter_simple, dataList);

        homeSimpleAdapter.openLoadAnimation(BaseQuickAdapter.SCALEIN);
        homeSimpleAdapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShort("Item Click:" + position);
            }
        });

        homeSimpleAdapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                ToastUtils.showShort("Child Item Click:" + position);
            }
        });

        recyclerView.setAdapter(homeSimpleAdapter);
    }

    private void initData() {
        dataList = new ArrayList<>();
        for (int i = 'A'; i <= 'Z'; i++) {
            dataList.add(String.valueOf((char) i));
        }
    }
}
