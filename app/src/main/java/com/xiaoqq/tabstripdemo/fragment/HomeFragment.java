package com.xiaoqq.tabstripdemo.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.ListFragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.xiaoqq.tabstripdemo.R;
import com.xiaoqq.tabstripdemo.StringUtils;
import com.xiaoqq.tabstripdemo.model.ChannelItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Headers;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


/**
 * User:Shine
 * Date:2015-10-20
 * Description:
 */
public class HomeFragment extends BaseFragment {
    private final String url = "";
    private final OkHttpClient client = new OkHttpClient();
    private static List<ChannelItem> items = new ArrayList<>();
    public static int page_size;
    private HomePagerAdapter homePagerAdapter;
    private ViewPager home_viewPager;

    private MyPagerAdapter mAdapter;
    private ViewPager mPager;
    static final int NUM_ITEMS = 10;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_pager, container, false);
        home_viewPager = (ViewPager) view.findViewById(R.id.home_viewPager);
        //onRefresh();

        mAdapter = new MyPagerAdapter(getChildFragmentManager());
        mPager = (ViewPager) view.findViewById(R.id.pager);
        mPager.setAdapter(mAdapter);

        Button btn = (Button) view.findViewById(R.id.goto_first);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(0);
            }
        });
        btn = (Button) view.findViewById(R.id.goto_last);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mPager.setCurrentItem(NUM_ITEMS - 1);
            }
        });
        return view;
    }


    private void onRefresh() {
        Request request = new Request.Builder()
                .url(url)
                .build();

        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) throw new IOException("Unexpected code：" + response);
                Headers responseHeaders = response.headers();
                for (int i = 0; i < responseHeaders.size(); i ++) {
                    Log.i(responseHeaders.name(i) + ":", responseHeaders.value(i));
                }
            }
        });

    }


    public static class HomePagerAdapter extends FragmentPagerAdapter {

        public HomePagerAdapter(FragmentManager fragmentManager) {
            super(fragmentManager);
        }

        @Override
        public Fragment getItem(int position) {
            Log.e("页数:", "第"+position+"个");
            return HomePagerFragment.newInstance(items.get(position).getId(), items.get(position).getName());
        }

        @Override
        public int getCount() {
            return page_size;
        }
    }

    public static class MyPagerAdapter extends FragmentPagerAdapter {

        public MyPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
//            return ArrayListFragment.newInstance(position, items.get(position).getId(), items.get(position).getName());
            return ArrayListFragment.newInstance(position);
        }

        @Override
        public int getCount() {
            return NUM_ITEMS;
        }
    }

    public static class ArrayListFragment extends ListFragment {
        int mNum, id;
        String title;
        static ArrayListFragment newInstance(int num) {
            ArrayListFragment f = new ArrayListFragment();
            Bundle args = new Bundle();
            args.putInt("num", num);
            f.setArguments(args);
            return f;
        }
        static ArrayListFragment newInstance(int num, int id, String title) {
            ArrayListFragment f = new ArrayListFragment();
            Bundle args = new Bundle();
            args.putInt("num", num);
            args.putInt("id", id);
            args.putString("title", title);
            f.setArguments(args);
            return f;
        }

        @Override
        public void onCreate(@Nullable Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            mNum = getArguments() != null ? getArguments().getInt("num") : 1;
            id = getArguments() != null ? getArguments().getInt("id") : 1;
            title = getArguments() != null ? getArguments().getString("title") : "";
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            View view = inflater.inflate(R.layout.fragment_pager_list, container, false);
            //TextView tv = (TextView) view.findViewById(R.id.show_name);
            TextView tv = (TextView) view.findViewById(R.id.text);
            tv.setText(id + "-->" + title);
            Toast.makeText(getActivity(), "fragment #" + mNum, Toast.LENGTH_SHORT).show();
            return view;
        }

        @Override
        public void onActivityCreated(@Nullable Bundle savedInstanceState) {
            super.onActivityCreated(savedInstanceState);
            setListAdapter(new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1));
        }

        @Override
        public void onListItemClick(ListView l, View v, int position, long id) {
            Log.i("FragmentList", "Item clicked: " + id);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        mAdapter.notifyDataSetChanged();
    }


}
