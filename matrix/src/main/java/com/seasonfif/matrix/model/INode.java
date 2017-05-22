package com.seasonfif.matrix.model;

import com.seasonfif.matrix.annotation.NestMode;
import java.util.List;

/**
 * 创建时间：2017年05月17日11:04 <br>
 * 作者：zhangqiang <br>
 * 描述：数据接口
 */

public interface INode<T> {

  /**
   * Card类型
   * @return
   */
  int getType();

  /**
   * Card嵌套的位置权重
   * 只对{@link NestMode#AUTO}类型生效
   * 因为{@link NestMode#MANUAL}嵌套位置是自己控制的
   * @return
   */
  int getWeight();

  /**
   * Card的额外说明
   * @return
   */
  String getDescription();

  /**
   * Card节点的数据部分
   * 即该Card展示的数据
   * @return
   */
  T getData();

  /**
   * 该节点的子节点（们）
   * @return
   */
  List<? extends INode> getChildren();
}
