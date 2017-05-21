package com.seasonfif.matrix.annotation;

import android.support.annotation.IntDef;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

import static com.seasonfif.matrix.annotation.NestMode.AUTO;
import static com.seasonfif.matrix.annotation.NestMode.MANUAL;
import static com.seasonfif.matrix.annotation.NestMode.NONE;

/**
 * 创建时间：2017年05月18日11:57 <br>
 * 作者：zhangqiang <br>
 * 描述：Card嵌套类型
 */

@IntDef({ MANUAL, AUTO, NONE})
@Retention(RetentionPolicy.SOURCE)
public @interface NestMode{

   /**
   * 不允许嵌套
   */
   int NONE = 0;

   /**
   * 根据viewgroup特性自动嵌套
   * 比如LinearLayout的VERTICAL与HORIZONTAL
   */
   int AUTO = 1;

   /**
   * 手动布局嵌套
   */
   int MANUAL = 2;
}

