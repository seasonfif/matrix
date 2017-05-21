package com.seasonfif.matrix.proxy;

import android.content.Context;

import com.seasonfif.matrix.card.ICard;
import com.seasonfif.matrix.card.ICardFactory;
import com.seasonfif.matrix.helper.CardCache;

/**
 * 创建时间：2017年05月18日18:05 <br>
 * 作者：zhangqiang <br>
 * 描述：工厂代理
 */

public class FactoryProxy implements ICardFactory {

  private CardCache cardCache;
  private ICardFactory factory;

  public FactoryProxy(ICardFactory factory){
    this.factory = factory;
    cardCache = CardCache.getInstance();
  }

  @Override public ICard createCard(Context context, int type) {

    /*ICard card = cardCache.get(type);
    if (card == null){
      card = factory.createCard(context, type);
      cardCache.set(type, card);
    }*/
    ICard card = factory.createCard(context, type);

    return card;
  }
}
