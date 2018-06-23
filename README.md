![image](https://github.com/FortuneDream/PocketExpression/blob/master/readme_logo.png)

# 表情包斗图

### 功能简介
斗图神器！
直接在聊天界面可以直接开始斗图！

### 开源库
* **T-MVP** 架构
* 加载图片 [Fresco](https://github.com/facebook/fresco)
* 图片选择器[TakePhoto](https://github.com/crazycodeboy/TakePhoto)
* 事件调度器 [RxJava2](https://github.com/ReactiveX/RxJava)
* 万能适配器[BRVAH](https://github.com/CymChad/BaseRecyclerViewAdapterHelper)
* 本地数据库 [GreenDao](https://github.com/greenrobot/greenDAO)
* 事件总线 [EventBus](https://github.com/greenrobot/EventBus)
* 权限 [AndPermission](https://github.com/yanzhenjie/AndPermission)

### 技术尝试
* AccessibilityService监控顶层Activity并通过Broadcast与SuspendService通信
* 拖拽IconWindow+表情包列表半屏Activity（生命周期）
* 抽取共有Module，做成aar文件,模块化开发
* Scrapy爬虫框架
* 图片压缩上传
* ContentProvider应用间共享数据（与口袋乐谱账号关联）
* 各种适配高低版本

### 项目进度

* 细节优化。

* [官方网站](http://http://pocketexpression.bmob.site/)

* Andriod兼容版本：**4.1.1** 及以上

### 注意事项

* 权限说明
    * 手机信息（上传手机型号，Android版本，Crash分析）   
    * SD卡读写权限（本地存储表情包）

#### By 兔子先生（毕业以后不叫鹏君了）