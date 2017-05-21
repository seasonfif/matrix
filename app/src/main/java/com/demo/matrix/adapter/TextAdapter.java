package com.demo.matrix.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.demo.matrix.card.ItemCard;
import com.demo.matrix.cardmodel.ItemCardBean;

import java.util.List;

/**
 * Created by seasonfif on 2017/5/20.
 */
public class TextAdapter<T> extends BaseAdapter {

    private Context context;
    private List<T>  mDatas;

    public TextAdapter(Context context) {
        this.context = context;
    }

    public void setDatas(List<T> datas) {
        this.mDatas = datas;
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (mDatas != null){
            return mDatas.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (mDatas != null){
            return mDatas.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ItemCard view;
        if (convertView == null){
            view = new ItemCard(context);
        }else{
            view = (ItemCard) convertView;
        }

        view.update((ItemCardBean) mDatas.get(position));

        return view;
    }
}
