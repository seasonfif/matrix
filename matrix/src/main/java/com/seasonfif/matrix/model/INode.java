package com.seasonfif.matrix.model;

import java.util.List;

/**
 * 创建时间：2017年05月17日11:04 <br>
 * 作者：zhangqiang <br>
 * 描述：数据接口
 */

public interface INode<T> {

  int getType();

  int getWeight();

  String getDescription();

  T getData();

  List<? extends INode> getChildren();
}
