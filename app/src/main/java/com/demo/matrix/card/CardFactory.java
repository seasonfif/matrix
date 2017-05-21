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

  @Override
  public ICard createCard(Context context, int type) {
    ICard card;
    switch (type){
      case ContainerCard.TYPE_CONTAINER_CARD:
        card = new ContainerCard(context);
        break;
      case ItemCard.TYPE_ITEM_CARD:
        card = new ItemCard(context);
        break;
      case TextCard.TYPE_TEXT_CARD:
        card = new TextCard(context);
        break;
      case ListViewCard.TYPE_LISTVIEW_CARD:
        card = new ListViewCard(context);
        break;
      default:
        card = new ContainerCard(context);
    }
    return card;
  }
}
