package com.seasonfif.matrix.card;

import com.seasonfif.matrix.annotation.NestMode;
import com.seasonfif.matrix.model.INode;

/**
 * 创建时间：2017年05月17日13:58 <br>
 * 作者：zhangqiang <br>
 * 描述：card接口
 */

public interface ICard<T> {

  /**
   * 是否允许嵌套
   * @return {@link NestMode}
   */
  @NestMode
  int getNestMode();

  /**
   * 将数据源{@link INode#getData()}绑定到Card
   */
  void update(T data);

  /**
   * 嵌套card，根据index
   *
   * @param index 兄弟节点的index
   * @param card 被嵌套的card
   */
  void addCard(int index, ICard card);
}
