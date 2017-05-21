package com.seasonfif.matrix.helper;

import com.seasonfif.matrix.model.INode;

import java.util.Comparator;

/**
 * 创建时间：2017年05月19日13:00 <br>
 * 作者：zhangqiang <br>
 * 描述：节点通过权重weight排序
 */

public class NodeComparator implements Comparator<INode> {

  @Override public int compare(INode lhs, INode rhs) {
    return rhs.getWeight() - lhs.getWeight();
  }
}
