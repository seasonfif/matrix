package com.demo.matrix.card;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.matrix.R;
import com.demo.matrix.cardmodel.ItemCardBean;
import com.seasonfif.matrix.annotation.CardModel;
import com.seasonfif.matrix.annotation.NestMode;
import com.seasonfif.matrix.card.ICard;

/**
 * 创建时间：2017年05月17日13:55 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
@CardModel(ItemCardBean.class)
public class ItemCard extends LinearLayout implements ICard<ItemCardBean> {

  TextView tv;
  LinearLayout root;

  public ItemCard(Context context) {
    this(context, null);
  }

  public ItemCard(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ItemCard(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    inflate(getContext(), R.layout.item_card, this);
    tv = (TextView) findViewById(R.id.tv);
    root = (LinearLayout) findViewById(R.id.root);
  }

  @Override public int getNestMode() {
    return NestMode.MANUAL;
  }

  @Override public void update(ItemCardBean data) {
    if (data != null){
      tv.setText(data.text);
    }
  }

  @Override public void addCard(int index, ICard card) {
    LayoutParams lp = new LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    root.addView((View)card, lp);
  }
}
