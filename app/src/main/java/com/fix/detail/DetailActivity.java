package com.fix.detail;


import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fix.BuildConfig;
import com.fix.FixAbstractActivity;
import com.fix.R;
import com.fix.home.HomeActivity;
import com.fix.home.HomePresenter;
import com.fix.model.Address;
import com.fix.model.DetailItem;
import com.fix.model.FixUser;
import com.fix.model.Phone;
import com.fix.network.FixAPI;

import java.util.ArrayList;
import java.util.List;

public class DetailActivity extends FixAbstractActivity<DetailView, DetailPresenter> implements DetailView {

    ImageView photo;
    RecyclerView list;
    private Toolbar toolbar;
    private Snackbar errorSnack;
    private FixUser user;
    private DetailAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.detail_activity);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        list = (RecyclerView) findViewById(R.id.detailList);

        list.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        photo = (ImageView) findViewById(R.id.photo);
        user = getIntent().getParcelableExtra("user");
        errorSnack = Snackbar.make(list, getString(R.string.connection_error), Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().getContact(user.getUser_id());
            }
        });
        fillData(user);
        getPresenter().getContact(user.getUser_id());
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

    }


    @Override
    public void fillData(FixUser fixUser) {
        Glide.with(getBaseContext()).load(fixUser.getPhoto()).crossFade().into(photo);
        toolbar.setTitle(fixUser.getName());
        List<DetailItem> detailItems = new ArrayList<>();
        for (Phone p : fixUser.getPhones()) {
            if (p.getNumber() != null)
                detailItems.add(new DetailItem(p.getName(), R.drawable.ic_phone_24dp));
        }
        if (fixUser.getBirth_date() != null) {
            detailItems.add(new DetailItem(fixUser.getBirth_date(), R.drawable.ic_cake_24dp));
        }
        adapter = new DetailAdapter(detailItems);
        list.setAdapter(adapter);
    }

    @Override
    public void fillAddresss(FixUser fixUser) {
        List<DetailItem> detailItems = new ArrayList<>();
        if (fixUser.getAddresses() != null && !fixUser.getAddresses().isEmpty())
            for (Address address : fixUser.getAddresses()) {
                if (!TextUtils.isEmpty(address.getWork())) {
                    detailItems.add(new DetailItem(address.getWork(), R.drawable.ic_work_24dp));
                }
                if (!TextUtils.isEmpty(address.getHome())) {
                    detailItems.add(new DetailItem(address.getHome(), R.drawable.ic_domain__24dp));
                }
            }
        adapter.addData(detailItems);
    }

    @Override
    public void showError() {
        errorSnack.show();
    }

    @NonNull
    @Override
    public DetailPresenter createPresenter() {
        final FixAPI fixAPI = createProxy(BuildConfig.FIX_API_BASE_URL, FixAPI.class);
        return new DetailPresenter(fixAPI);
    }

    class DetailAdapter extends RecyclerView.Adapter<DetailAdapter.ViewHolder> {
        private List<DetailItem> data;

        public DetailAdapter(List<DetailItem> userList) {
            this.data = userList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView value;
            public ImageView logo;

            public ViewHolder(View v) {
                super(v);
                value = (TextView) v.findViewById(R.id.value);
                logo = (ImageView) v.findViewById(R.id.logo);

            }
        }

        @Override
        public DetailAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
            View root = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.detail_list_row, parent, false);
            return new ViewHolder(root);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            DetailItem item = data.get(position);
            holder.value.setText(item.getName());
            holder.logo.setImageResource(item.getResource());

        }

        public void addData(List<DetailItem> items) {
            data.addAll(items);
            notifyItemInserted(data.size());

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }
}
