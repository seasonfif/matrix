package com.demo.matrix.cardmodel;

import com.seasonfif.matrix.model.INode;

import java.util.List;

/**
 * 创建时间：2017年05月17日11:06 <br>
 * 作者：zhangqiang <br>
 * 描述：
 */

public class Node implements INode<String> {

  private int type;
  private String des;
  private int weight;
  private String data;
  private List<Node> children;

  @Override public int getType() {
    return type;
  }

  @Override public int getWeight() {
    return weight;
  }

  @Override public String getDescription() {
    return des;
  }

  @Override public String getData() {
    return data;
  }

  @Override public List<? extends INode> getChildren() {
    return children;
  }
}
