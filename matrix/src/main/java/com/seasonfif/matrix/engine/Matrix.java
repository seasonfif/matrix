package com.seasonfif.matrix.engine;

import android.content.Context;
import android.view.View;

import com.seasonfif.matrix.card.ICardFactory;
import com.seasonfif.matrix.model.INode;

/**
 * 创建时间：2017年05月17日14:18 <br>
 * 作者：zhangqiang <br>
 * 描述：Matrix
 */

public class Matrix {

  private static volatile Matrix singleton = null;

  private static ICardFactory sFactory;

  private LayoutEngine engine;

  public static void init(ICardFactory factory){
    sFactory = factory;
  }

  private Matrix(ICardFactory factory) {
    engine = new LayoutEngine(factory);
  }

  public static Matrix getEngine(){
    if (singleton == null){
      synchronized (Matrix.class){
        if (singleton == null){
          singleton = new Matrix(sFactory);
        }
      }
    }
    return singleton;
  }

  public View produce(Context context, INode node){
    return engine.layout(context, node);
  }
}
