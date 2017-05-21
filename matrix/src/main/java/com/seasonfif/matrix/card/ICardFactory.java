package com.seasonfif.matrix.card;

import android.content.Context;

/**
 * 创建时间：2017年05月17日13:49 <br>
 * 作者：zhangqiang <br>
 * 描述：获得Card的抽象工厂
 */

public interface ICardFactory {

  /**
   * 生成各种Card，根据type
   */
  ICard createCard(Context context, int type);
}
