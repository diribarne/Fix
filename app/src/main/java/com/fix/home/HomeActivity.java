package com.fix.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.fix.BuildConfig;
import com.fix.FixAbstractActivity;
import com.fix.R;
import com.fix.detail.DetailActivity;
import com.fix.model.FixUser;
import com.fix.network.FixAPI;

import java.util.List;

public class HomeActivity extends FixAbstractActivity<HomeView, HomePresenter> implements HomeView {
    Snackbar errorSnack;
    RecyclerView contactList;
    SwipeRefreshLayout swipeRefreshLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        contactList = (RecyclerView) findViewById(R.id.contactList);
        swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.swipeRefresh);
        contactList.setLayoutManager(new LinearLayoutManager(getBaseContext()));
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                getPresenter().getContact();
            }
        });
        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        errorSnack = Snackbar.make(contactList, getString(R.string.connection_error), Snackbar.LENGTH_INDEFINITE).setAction(getString(R.string.retry), new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getPresenter().getContact();
            }
        });
        getPresenter().getContact();
    }

    @NonNull
    @Override
    public HomePresenter createPresenter() {
        final FixAPI fixAPI = createProxy(BuildConfig.FIX_API_BASE_URL, FixAPI.class);
        return new HomePresenter(fixAPI);
    }


    @Override
    public void showContactList(List<FixUser> userList) {
        contactList.setAdapter(new ContactAdapter(userList));
    }

    @Override
    public void showError() {
        errorSnack.show();
    }

    @Override
    public void showProgress(Boolean isShow) {
        swipeRefreshLayout.setRefreshing(isShow);
    }


    class ContactAdapter extends RecyclerView.Adapter<ContactAdapter.ViewHolder> {
        private List<FixUser> data;

        public ContactAdapter(List<FixUser> userList) {
            this.data = userList;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {

            public TextView name;
            public ImageView thumb;
            public View container;

            public ViewHolder(View v) {
                super(v);
                container = v;
                name = (TextView) v.findViewById(R.id.title);
                thumb = (ImageView) v.findViewById(R.id.thumb);
                container.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent i = new Intent(HomeActivity.this, DetailActivity.class);
                        i.putExtra("user", (FixUser) view.getTag());
                        startActivity(i);
                    }
                });
            }
        }

        @Override
        public ContactAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                            int viewType) {
            View root = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.contact_list_row, parent, false);
            return new ViewHolder(root);
        }

        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {
            FixUser fixUser = data.get(position);
            holder.name.setText(fixUser.getName());
            Glide.with(getBaseContext())
                    .load(fixUser.getThumb())
                    .centerCrop().error(R.drawable.ic_empty_avatar_48)
                    .into(holder.thumb);
            holder.container.setTag(fixUser);

        }

        @Override
        public int getItemCount() {
            return data.size();
        }
    }

}
