package com.demo.matrix;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ScrollView;

import com.demo.matrix.card.CardFactory;
import com.demo.matrix.card.ListViewCard;
import com.demo.matrix.cardmodel.Node;
import com.demo.matrix.net.RetrofitService;
import com.demo.matrix.util.DataUtil;
import com.seasonfif.matrix.engine.Matrix;

import java.io.IOException;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = "MainActivity";
    private Node node;
    private ScrollView scroll;
    private SwipeRefreshLayout refreshLayout;
    private View view;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Matrix.init(new CardFactory());
        refreshLayout = (SwipeRefreshLayout) findViewById(R.id.refresh);
        refreshLayout.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light, android.R.color.holo_orange_light, android.R.color.holo_red_light);
        scroll = (ScrollView) findViewById(R.id.scroll);

        dialog = new ProgressDialog(this, android.app.AlertDialog.THEME_HOLO_LIGHT);
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);

        refresh();

        refreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        refresh();
                    }
                }, 3000);

            }
        });

    }

    private void refresh() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://seasonfif.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        RetrofitService service = retrofit.create(RetrofitService.class);
        Call<Node> nodeCall = service.getNodes();
        nodeCall.enqueue(new retrofit2.Callback<Node>() {
            @Override
            public void onResponse(retrofit2.Call<Node> call, retrofit2.Response<Node> response) {
                view = Matrix.getEngine().produce(MainActivity.this, response.body());
                scrollConflict(findListView((ViewGroup) view));
                refreshLayout.setRefreshing(false);
                scroll.removeAllViews();
                scroll.addView(view);
            }

            @Override
            public void onFailure(retrofit2.Call<Node> call, Throwable t) {
                node = DataUtil.getData(loadJsonAssets(), Node.class);
                view = Matrix.getEngine().produce(MainActivity.this, node);
                scrollConflict(findListView((ViewGroup) view));
                refreshLayout.setRefreshing(false);
                scroll.removeAllViews();
                scroll.addView(view);
            }
        });
    }

    private String loadJsonAssets(){
        String jsonStr = null;
        try {
            jsonStr = DataUtil.getStrFromAssets(getAssets().open("nodes.json"));
            if (node == null){
                Log.e(TAG, "");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return jsonStr;
    }

    private void scrollConflict(ListViewCard listView){
        if (listView == null) return;
        listView.setOnSwipeLayout(new ListViewCard.OnSwipeLayout() {
            @Override
            public void swipeLayoutEnable() {
                refreshLayout.setEnabled(true);
            }

            @Override
            public void swipeLayoutDisable() {
                refreshLayout.setEnabled(false);
            }
        });
    }

    private ListViewCard findListView(ViewGroup group) {
        if (group != null) {
            for (int i = 0, j = group.getChildCount(); i < j; i++) {
                View child = group.getChildAt(i);
                if (child instanceof ListViewCard) {
                    return (ListViewCard) child;
                } else if (child instanceof ViewGroup) {
                    ListViewCard result = findListView((ViewGroup) child);
                    if (result != null)
                        return result;
                }
            }
        }
        return null;
    }
}
