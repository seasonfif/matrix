package com.demo.matrix.card;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

import com.demo.matrix.adapter.TextAdapter;
import com.demo.matrix.cardmodel.ItemCardBean;
import com.seasonfif.matrix.annotation.CardModel;
import com.seasonfif.matrix.annotation.NestMode;
import com.seasonfif.matrix.card.ICard;

import java.util.List;

/**
 * Created by seasonfif on 2017/5/20.
 */

@CardModel(ItemCardBean.class)
public class ListViewCard<T> extends ListView implements ICard<List<T>> {

    public static final int TYPE_LISTVIEW_CARD = 0x0003;

    private TextAdapter adapter;

    private OnSwipeLayout onSwipeLayout;

    public interface OnSwipeLayout{
        void swipeLayoutEnable();
        void swipeLayoutDisable();
    }

    public ListViewCard(Context context) {
        this(context, null);
    }

    public ListViewCard(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ListViewCard(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init() {
        setDivider(new ColorDrawable(Color.parseColor("#bdbdbd")));
        setDividerHeight(9);
        adapter = new TextAdapter(getContext());
        setAdapter(adapter);

        /**
         *  解决与ScrollView的冲突
         */
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                getParent().requestDisallowInterceptTouchEvent(true);
                return false;
            }
        });

        /**
         *  解决与SwipeRefreshLayout的冲突
         */
        setOnScrollListener(new OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {

            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                View firstView = view.getChildAt(firstVisibleItem);

                // 当firstVisibleItem是第0位。如果firstView==null说明列表为空，需要刷新;或者top==0说明已经到达列表顶部, 也需要刷新
                if (firstVisibleItem == 0 && (firstView == null || firstView.getTop() == 0)) {
                    if (onSwipeLayout != null){
                        onSwipeLayout.swipeLayoutEnable();
                    }
                } else {
                    if (onSwipeLayout != null){
                        onSwipeLayout.swipeLayoutDisable();
                    }
                }
            }
        });
    }

    /**
     * 重写该方法、达到使ListView适应ScrollView的效果
     *
     * 将listview全部展开显示在scrollview上了
     *//*
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        int expandSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2,
                MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, expandSpec);
    }*/



    @Override
    public int getNestMode() {
        return NestMode.NONE;
    }

    @Override
    public void update(List<T> data) {
        adapter.setDatas(data);
    }

    @Override
    public void addCard(int index, ICard card) {

    }

    public void setOnSwipeLayout(OnSwipeLayout onSwipeLayout) {
        this.onSwipeLayout = onSwipeLayout;
    }
}
