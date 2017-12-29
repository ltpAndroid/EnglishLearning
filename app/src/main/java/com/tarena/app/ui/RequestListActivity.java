package com.tarena.app.ui;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ImageView;
import android.widget.ListView;

import com.tarena.app.R;
import com.tarena.app.adapter.RequestAdapter;
import com.tarena.app.entity.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RequestListActivity extends AppCompatActivity {

    @BindView(R.id.iv_back)
    ImageView ivBack;
    @BindView(R.id.lv_requests)
    ListView lvRequests;

    private RequestAdapter adapter;
    private List<String> requests = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_request_list);
        ButterKnife.bind(this);

        initData();
        loadRequests();
    }

    private void initData() {
        Intent intent = getIntent();
        requests = (List<String>) intent.getExtras().get("request");
    }

    private void loadRequests() {
        adapter = new RequestAdapter(this, requests);
        lvRequests.setAdapter(adapter);
    }

    @OnClick(R.id.iv_back)
    public void onViewClicked() {
        finish();
    }
}
