#致 Android 开发者，彻底认清 MVC、MVVM、MVP

对于绝大多数开发者来说，对于选择程序的架构，并没有太多的考虑。一方面是业务变化太快，怎么快，怎么来；另一方面，程序架构一般由团队的核心开发来选择，其他开发者可能会处于云里雾里的状态。本文会详细说明，并有代码参考，教你彻底认清 MVC、MVVM、MVP 的相关知识，希望对大家有用吧！

阅读之前请谨记

* 架构来源于业务，并没有好坏之分。好的架构是在业务、成本、时间之间取得一个完美的平衡
* 希望读者有自己的思考，具有怀疑和批判精神，千万不要 100% 相信本文的观点

### MVVM
MVVM 全名是 Model View ViewModel，其本质是在 View 和 Model 之间加入了一层中间层，将 Model 表示为一个可展示的对象，其结构图如下
![MVVM](article/mvvm.png)

* View
	* 用户动作映射成模型更新
	* 选择响应的视图
	* 获取模型数据
	* 模型展示、更新
* Model
	* 封装应用程序状态
	* 响应状态查询
	* 通知视图更新
* ViewModel
	* 将 Model 层数据适配为 View 层所需要的数据 

相对于 MVC 模式，View 层包含了 MVC 中 View、Controller 的职责，Model 的职责病没有发生变化，ViewModel 可以看做是一个适配器，将 Model 层的数据适配成 View 需要展示的数据。

MVVM 是一种设计范例，它也有多种实现方式，业内最多的实现方式是以 data binding 的形式去实现，而且在该设计范例中，更强调数据绑定的作用。例如在 ASP.NET 中最早就可以将一个数据源绑定到一个控件上。

MVVM 在 Android 中是如何实现的呢？谷歌官方实现了 data binding 的框架，下面我们来看看是如何实现的。

####build.gradle
```
android {

    dataBinding {
        enabled = true
    }
}
```
在 gradle 的默认设置中加入 databinding 的支持。

####View
#####activity\_home_item.xml
```
<?xml version="1.0" encoding="utf-8"?>
<layout xmlns:android="http://schemas.android.com/apk/res/android">
    <data>
        <variable name="task" type="com.android.mvp.data.bean.Task" />
    </data>
    
		 ...
		 
        <TextView
            android:id="@+id/tv_item_title"
            android:layout_width="match_parent"
            android:layout_height="wrap_content"
            android:layout_centerVertical="true"
            android:layout_toEndOf="@+id/btn_item_check"
            android:layout_toRightOf="@+id/btn_item_check"
            android:textColor="@color/text_grey"
            android:textSize="18sp"
            android:text="@{task.title}"/>

    </RelativeLayout>
</layout>
```
相对于 MVC 中的 activity\_home_item.xml，这里将 TextView 的显示字段直接绑定到 Task 对象的 title 字段上。这里只展示于 MVC 中不同的部分，下面的介绍中也是同理。

#####HomeListAdapter.java
```
@Override
public View getView(int position, View convertView, ViewGroup parent) {
    ActivityHomeItemBinding binding;
    Task task = mTasks.get(position);
    if (convertView == null) {
        binding = ActivityHomeItemBinding.inflate(mLayoutInflater, parent, false);
    } else {
        binding = DataBindingUtil.getBinding(convertView);
    }
    binding.setTask(task);
    binding.executePendingBindings();

    return binding.getRoot();
}
```
这里使用了框架生成的 ActivityHomeItemBinding 对象，更新的时候直接使用 setTask 即可将数据绑定到视图上，在 MVC 中的代码如下

```
@Override
public View getView(int position, View convertView, ViewGroup parent) {
    final ViewHolder viewHolder;
    Task task;
    task = mTasks.get(position);
    if (convertView == null) {
        convertView = mLayoutInflater.inflate(R.layout.activity_home_item, null);
        viewHolder = new ViewHolder();

        viewHolder.tvItemTitle = (TextView)convertView.findViewById(R.id.tv_item_title);
        convertView.setTag(viewHolder);
    } else {
        viewHolder = (ViewHolder) convertView.getTag();
    }

    bindData(viewHolder, task);
    return convertView;
}

private void bindData(ViewHolder viewHolder, Task task) {
    viewHolder.tvItemTitle.setText(task.getTitle());
}
```
其实这里可以看出来，使用了 databinding 框架，可以减少我们手工去绑定数据的过程，这里只是绑定一个控件，如果在大量控件的情况下，是能提高开发效率的。

####ViewModel
#####Task.java
```
public final class Task extends BaseObservable {

    private long mId;
    private String mTitle;

    public Task() {
        this.mId = 0L;
        this.mTitle = "";
    }

    public long getId() {
        return mId;
    }

    public void setId(long id) {
        mId = id;
    }

    @Bindable
    public String getTitle() {
        return mTitle;
    }

    public void setTitle(String title) {
        mTitle = title;
        notifyPropertyChanged(BR.title);
    }
}
```
很多文章将 ActivityHomeItemBinding 描述为 ViewModel 对象，是一种错误的说法，ActivityHomeItemBinding 只是一个自动生成的辅助工具类，数据要更新到视图上，需要使用 BaseObservable 提供的 @Bindable 注解。xml 文件中的 task.title 在显示时会调用 Task.getTitle 方法。

####Model
Model 层相对于 MVC 来说是一样的，并没有什么变化。

####小结
* MVVM 着重强调数据绑定，使用数据绑定工具，可以提高开发效率
* 抛开 MVVM 这种模式，其数据绑定用在其他模式上也一样实用，个人觉得，可以不把 MVVM 当作一种模式

