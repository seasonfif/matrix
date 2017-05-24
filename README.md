Matrix
========

一个轻量级的基于卡片模板的自动化布局框架

**关于我**

> 我的其他项目：
>[github第三方Android客户端（适配Material Design）](https://github.com/seasonfif/github)

## **原理**
该框架依据View叠加的树形结构，通过与之对应的树形json快捷简便的动态控制Card的布局结构。实现原理如下：

 1. 解析服务端下发的树形json数据（节点类型需实现`INode`接口）；
 2. 布局引擎深度遍历树的节点，根据节点类型生成（生成方法为`IFactory`的实现类）各节点对应的Card（Card为使用时实现`ICard`接口的View或ViewGroup）；
 3. 将同一深度的兄弟节点依据权重（权重为使用时`INode`接口获得）排序；
 4. 递归遍历非叶子节点，将Card根据嵌套类型（`NestMode`）回调给具体的Card实现布局嵌套；
 5. 递归完成之后，布局引擎将返回根据树形json布局完成的View。

## **特性**
该框架具有强大的Card布局功能，其主要功能特性如下:

 1. 框架以Card为最小单位布局（一个Card就是实现ICard接口的自定义View或ViewGroup），不关心Card内部的布局结构
 2. 框架解析服务端数据从而生成View，因此可实现真正的动态化布局
 3. 框架支持Card的自动嵌套（比如LinearLayout的VERTICAL与HORIZONTAL）和手动嵌套（预先占坑ViewGroup）
 4. 框架支持树形节点的Card数据部分为统一数据Bean或独自数据bean（通过json字符串支持，框架提供的`Node`节点模型即为此种类型）
 5. 框架支持以注解（`CardModel`）形式设置独自数据bean的类型
 6. 框架支持的独自数据bean的类型可以为普通对象（对应json对象）和列表对象（对应json数组）

## **设置Gradle依赖**
1. Add the JitPack repository to your build file
```
allprojects {
    repositories {
        ...
        maven { url 'https://jitpack.io' }
    }
}
```
2. Add the dependency
```
dependencies {
        compile 'com.github.seasonfif:matrix:v1.0'
}
```
## **使用方法**
框架使用特别简单：

```java
 1. 应用开始执行的地方使用IFactory对象初始化Matrix
    Matrix.init(new CardFactory());
    
 2. 需要动态生成View的地方（node为INode对象）
    View view = Matrix.getEngine().produce(MainActivity.this, node);
    setContentView(view);
```

除以上的框架调用之外，使用时应该优先完成以下的内容：
```
 1. 一个实现INode接口的节点数据模型（如果计划使用json字符串作为Card数据模型的下发方式则可以使用框架提供的Node对象）
    
 2. 一个实现IFactory抽象工厂接口的Card工厂类
 
 3. 至少有一个实现ICard接口的卡片以及与之对应的卡片数据模型（如果为独自的Card数据模型需要在ICard实现类加上类注解@CardModel(xxx.class)）
 
 总而言之就是事先准备好项目需要动态布局各种的Card
```

## **示例**
提供代码片段以帮助大家更快地理解其使用方法

**1. 一个实现INode接口的节点数据模型**
```java
以框架中的实现为例
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
```

**2. 一个实现IFactory抽象工厂接口的Card工厂类**

```java
public class CardFactory implements ICardFactory {
  public static final int TYPE_CONTAINER_CARD = 0x0000;
  public static final int TYPE_ITEM_CARD = 0x0001;
  @Override
  public ICard createCard(Context context, int type) {
    ICard card;
    switch (type){
      case TYPE_CONTAINER_CARD:
        card = new ContainerCard(context);
        break;
      case TYPE_ITEM_CARD:
        card = new ItemCard(context);
        break;
      default:
        card = new ContainerCard(context);
    }
    return card;
  }
}
```

**3. 一个实现ICard接口的卡片以及与之对应的卡片数据模型**

```java
卡片实现
@CardModel(TextCardBean.class)
public class TextCard extends TextView implements ICard<TextCardBean> {
  public TextCard(Context context) {
    this(context, null);
  }
  public TextCard(Context context, AttributeSet attrs) {
    this(context, attrs, 0);
  }
  public TextCard(Context context, AttributeSet attrs, int defStyleAttr) {
    super(context, attrs, defStyleAttr);
    init();
  }

  private void init() {
    setBackgroundColor(Color.parseColor("#9ccc65"));
    setTextColor(Color.WHITE);
  }
  @Override public int getNestMode() {
      return NestMode.NONE;
  }
  @Override
  public void update(TextCardBean data) {
    if (data != null){
      setText(data.text);
    }
  }
  @Override public void addCard(int index, ICard card) {

  }
}

卡片对应的数据Bean
public class TextCardBean {
    public String text;
}
```

## **TODO**
 1. Card缓存机制（由于View体系中同一View对象不能嵌套，因此将缓存Card添加到同为自己的父布局时会异常），如果有好的实现思路可以提issue 

## **第三方**
框架使用[Gson](https://github.com/google/gson) （A Java serialization/deserialization library to convert Java Objects into JSON and back）作为json字符串转化为数据对象的工具

开源许可
====

```
Copyright 2017 seasonfif.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  http://www.apache.org/licenses/LICENSE-2.0
    
Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```