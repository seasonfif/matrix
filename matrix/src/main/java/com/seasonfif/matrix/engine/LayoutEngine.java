package com.seasonfif.matrix.engine;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.ViewGroup;

import com.seasonfif.matrix.annotation.CardModel;
import com.seasonfif.matrix.annotation.NestMode;
import com.seasonfif.matrix.card.ICard;
import com.seasonfif.matrix.card.ICardFactory;
import com.seasonfif.matrix.helper.GsonHelper;
import com.seasonfif.matrix.helper.NodeComparator;
import com.seasonfif.matrix.model.INode;
import com.seasonfif.matrix.proxy.FactoryProxy;

import java.util.Collections;
import java.util.List;

/**
 * 创建时间：2017年05月17日16:20 <br>
 * 作者：zhangqiang <br>
 * 描述：布局引擎
 */

public class LayoutEngine {

  private Context context;
  private FactoryProxy factory;

  /**
   * LayoutEngine的构造方法
   * @param factory 卡片的代理工厂
   */
  public LayoutEngine(ICardFactory factory) {
    this.factory = new FactoryProxy(factory);
  }

  /**
   * 单独处理根节点
   * 其他层次的节点以根节点为parent布局
   * @param context
   * @param node
   * @return
   */
  public View layout(@NonNull Context context, @NonNull INode node) {
    this.context = context;
    ICard root;
    root = factory.createCard(context, node.getType());
    root.update(getCardModel(node.getData(), root));

    if (!isLeaf(node)){
      layout(node, root);
    }
    return (View)root;
  }

  /**
   * 采用递归方式深度遍历除根节点外的所有节点
   * @param node
   * @param card
   */
  private void layout(INode node, ICard card) {
    if (!(card instanceof ViewGroup)){
      throw new IllegalStateException("Card [type=" + node.getType() + "]是View，原生不支持嵌套");
    }

    if (card.getNestMode() == NestMode.NONE){
      throw new IllegalStateException("Card [type=" + node.getType() + "]不允许嵌套");
    }

    List<? extends INode> children = node.getChildren();
    if (card.getNestMode() == NestMode.AUTO){
      sortByWeight(children);
    }
    for (int i = 0; i < children.size(); i++) {
      INode child = children.get(i);
      ICard childCard = factory.createCard(context, child.getType());
      childCard.update(getCardModel(child.getData(), childCard));
      card.addCard(i, childCard);
      if (!isLeaf(child)){
        layout(child, childCard);
      }
    }
  }

  /**
   * 判断当前节点是否为叶子节点
   * @param node
   * @return
   */
  private boolean isLeaf(INode node){
    if (node.getChildren() == null){
      return true;
    }else{
      if (node.getChildren().size() <= 0){
        return true;
      }
      return false;
    }
  }

  /**
   * 兄弟节点按照权重排序
   * @param nodes
   */
  private void sortByWeight(List<? extends INode> nodes){
    Collections.sort(nodes, new NodeComparator());
  }

  /**
   * 通过注解获得model的具体类型
   * 然后通过Gson转换
   * @param data
   * @param card
   * @return
   */
  private Object getCardModel(Object data, ICard card){
    Object result;
    if (data instanceof String){
      String jsonStr = (String)data;
      CardModel cardModel = card.getClass().getAnnotation(CardModel.class);
      Class cls = cardModel.value();

      if (GsonHelper.isJSONArray(jsonStr)){
        result = GsonHelper.formatList(jsonStr, cls);
      }else{
        result = GsonHelper.format(jsonStr, cls);
      }
    }else{
      result = data;
    }
    return result;
  }
}
