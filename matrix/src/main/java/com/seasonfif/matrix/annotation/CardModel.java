package com.seasonfif.matrix.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 创建时间：2017年05月18日18:18 <br>
 * 作者：zhangqiang <br>
 * 描述：注解Card对应的数据类型
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface CardModel {
    Class value();
}
