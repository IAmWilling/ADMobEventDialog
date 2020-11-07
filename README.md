# ADMobEventDialog
产品喜欢的通用埋点展示工具

## 说明
#### 为何开发
- 使用系统自带的Toast查看太慢了，而且弹窗真的很烦，会有部分东西被遮挡
- 方便产品查看埋点，具体的触发时机

#### 如何使用
> 安装
```javascript
allprojects {
		repositories {
			...
			maven { url 'https://jitpack.io' }
		}
	}
```
```javascript
dependencies {
	 implementation 'com.github.IAmWilling:ADMobEventDialog:1.0.0'
}
```
> 代码
在Application中onCreate中第一行：
```java
@Override
    public void onCreate() {
        super.onCreate();
        //这里第二个参数类型为Class，如果想要有当前页面的埋点查看功能
        //需要将自己定义好的埋点工具类填入，并且需要配合注解@AgentEv使用
        ADMobEv.getInstance().init(this,null);
    }
```
接着,在上报埋点的文件中加入：
```java
//这是友盟埋点上报 确保上报后立即 加入到工具类中
MobclickAgent.onEvent(context, eventId);
//事件id，事件描述
ADMobEv.getInstance().addEv("xx","xxxxx");
```
> 注解使用
```java
//注意 这些都应当在初始化的第二个参数的类文件中
@AgentEv(act = "activity名称",desc = "xxxxxx")
public static final String BTN_CLICK = "btn_click";
```
## 最后
当看到第一个界面出现蓝色小球时，表面加载完毕了，小球是可拖动的，不影响操作。
## 如何实现的
1.注解 -> 反射 -> 映射数据 -> 监听activity生命周期postOnCreated -> PopupWindow(含可滚动ViewGroup)
