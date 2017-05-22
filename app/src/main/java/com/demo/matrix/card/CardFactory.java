package com.demo.matrix.card;

import android.content.Context;

import com.seasonfif.matrix.card.ICard;
import com.seasonfif.matrix.card.ICardFactory;

/**
 * 创建时间：2017年05月18日11:22 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */
public class CardFactory implements ICardFactory {

  public static final int TYPE_CONTAINER_CARD = 0x0000;

  public static final int TYPE_ITEM_CARD = 0x0001;

  public static final int TYPE_TEXT_CARD = 0x0002;

  public static final int TYPE_LISTVIEW_CARD = 0x0003;

  @Override
  public ICard createCard(Context context, int type) {
    ICard card;
    switch (type){
      case TYPE_CONTAINER_CARD:
        card = new ContainerCard(context);
        break;
      case TYPE_ITEM_CARD:
        card = new ItemCard(context);
        break;
      case TYPE_TEXT_CARD:
        card = new TextCard(context);
        break;
      case TYPE_LISTVIEW_CARD:
        card = new ListViewCard(context);
        break;
      default:
        card = new ContainerCard(context);
    }
    return card;
  }
}
