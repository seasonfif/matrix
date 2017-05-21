package com.demo.matrix.card;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.demo.matrix.R;
import com.demo.matrix.cardmodel.ContainerCardBean;
import com.seasonfif.matrix.annotation.CardModel;
import com.seasonfif.matrix.annotation.NestMode;
import com.seasonfif.matrix.card.ICard;

/**
 * 创建时间：2017年05月17日18:27 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
@CardModel(ContainerCardBean.class)
public class ContainerCard extends LinearLayout implements ICard<ContainerCardBean> {

  public static final int TYPE_CONTAINER_CARD = 0x0000;
  TextView tv;

  public ContainerCard(Context context) {
    this(context, null);
  }

  public ContainerCard(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }

  public ContainerCard(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    setOrientation(VERTICAL);
    setBackgroundColor(Color.GRAY);
    tv = new TextView(getContext());
    tv.setTextColor(Color.WHITE);
    addView(tv, new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT));
  }

  @Override public int getNestMode() {
    return NestMode.AUTO;
  }

  @Override
  public void update(ContainerCardBean data) {
    if (data != null){
      tv.setText(data.title);
    }
  }

  @Override public void addCard(int index, ICard card) {
    LayoutParams lp;
    if (card instanceof ListViewCard){
      lp = new LayoutParams(LayoutParams.MATCH_PARENT, 800);
      lp.topMargin = getContext().getResources().getDimensionPixelSize(R.dimen.dimen_15);
    }else{
      lp = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
      lp.topMargin = getContext().getResources().getDimensionPixelSize(R.dimen.dimen_15);
      lp.leftMargin = getContext().getResources().getDimensionPixelSize(R.dimen.dimen_15);
      lp.rightMargin = getContext().getResources().getDimensionPixelSize(R.dimen.dimen_15);
    }
    this.addView((View) card, lp);
  }
}
