package com.demo.matrix;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ScrollView;
import android.widget.TextView;

import com.demo.matrix.card.CardFactory;
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
    private TextView tv;
    private View view;
    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Matrix.init(new CardFactory());
        tv = (TextView) findViewById(R.id.tv);
        scroll = (ScrollView) findViewById(R.id.scroll);

        dialog = new ProgressDialog(this, android.app.AlertDialog.THEME_HOLO_LIGHT);
        dialog.setCancelable(false);
        dialog.setIndeterminate(true);

        refresh();

        tv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                refresh();
            }
        });
    }

    private void refresh() {

        dialog.show();
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
                dialog.dismiss();
                scroll.removeAllViews();
                scroll.addView(view);
            }

            @Override
            public void onFailure(retrofit2.Call<Node> call, Throwable t) {
                node = DataUtil.getData(loadJsonAssets(), Node.class);
                view = Matrix.getEngine().produce(MainActivity.this, node);
                dialog.dismiss();
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
}
