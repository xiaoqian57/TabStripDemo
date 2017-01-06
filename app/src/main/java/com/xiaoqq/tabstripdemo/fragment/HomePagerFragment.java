package com.xiaoqq.tabstripdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.xiaoqq.tabstripdemo.R;

/**
 * Created by qqian on 1/3/2017.
 */

public class HomePagerFragment extends Fragment {

    // Store instance variables
    private String title;
    private int id;

    public static HomePagerFragment newInstance(int id, String title) {
        HomePagerFragment fragment = new HomePagerFragment();
        Bundle args = new Bundle();
        args.putInt("id", id);
        args.putString("title", title);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        id = getArguments().getInt("id");
        title = getArguments().getString("title");
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home_view_pager, container, false);
        TextView tv = (TextView) view.findViewById(R.id.show_name);
        tv.setText(id +" -- "+ title);
        return view;
    }
}
