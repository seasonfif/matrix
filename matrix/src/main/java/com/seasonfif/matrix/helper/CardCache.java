package com.seasonfif.matrix.helper;

import android.util.SparseArray;

import com.seasonfif.matrix.card.ICard;

import java.lang.ref.WeakReference;

/**
 * 创建时间：2017年05月18日17:22 <br>
 * 作者：zhangqiang <br>
 * 描述：Card缓存
 */

public class CardCache {

  private static volatile CardCache instance;

  private Builder builder;

  private CardCache(){
    builder = new Builder();
  }

  public static CardCache getInstance(){
    if (instance == null){
      synchronized (CardCache.class){
        if (instance == null){
          instance = new CardCache();
        }
      }
    }
    return instance;
  }

  public void set(int type, ICard card){
    builder.put(type, card);
  }

  public ICard get(int type){
    WeakReference<ICard> weakCard = builder.get(type);
    if (weakCard != null){
      return weakCard.get();
    }
    return null;
  }

  public void update(int type, ICard card){
    builder.delete(type);
    builder.put(type, card);
  }


  private static class Builder{

    private SparseArray<WeakReference<ICard>> cache;
    public Builder (){
      cache = new SparseArray<>();
    }

    public Builder put(int key, ICard card){
      cache.put(key, new WeakReference<>(card));
      return this;
    }

    public Builder delete(int key){
      cache.delete(key);
      return this;
    }

    public WeakReference<ICard> get(int key){
      return cache.get(key);
    }

    public SparseArray<WeakReference<ICard>> build(){
      return cache;
    }
  }
}
