#致 Android 开发者，彻底认清 MVC、MVVM、MVP

对于绝大多数开发者来说，对于选择程序的架构，并没有太多的考虑。一方面是业务变化太快，怎么快，怎么来；另一方面，程序架构一般由团队的核心开发来选择，其他开发者可能会处于云里雾里的状态。本文会详细说明，并有代码参考，教你彻底认清 MVC、MVVM、MVP 的相关知识，希望对大家有用吧！

阅读之前请谨记

* 架构来源于业务，并没有好坏之分。好的架构是在业务、成本、时间之间取得一个完美的平衡
* 希望读者有自己的思考，具有怀疑和批判精神，千万不要 100% 相信本文的观点

### MVC
MVC 全名是 Model View Controller，顾名思义，Controller 作为控制器，通过用户输入，控制视图的展示，还可以通过 Model 层的数据组件获取数据，进而改变 View 层的展示，其结构图如下

![MVC](article/mvc.png)

* Controller
	* 用户动作映射成模型更新
	* 选择响应的视图
* View
	* 获取模型数据
	* 模型展示、更新
	* 收集用户行为，发送给控制器
* Model
	* 封装应用程序状态
	* 响应状态查询
	* 通知视图更新

这里将 Controller、View 放在同一级别，主要是为了说明其调用关系，Controller 对 View 是单向调用，Controller 和 View 对 Model 的调用也是单向的，以实箭头表示。Model 将数据传递给 Controller 或者是 View，传递的方式可以是调用的时候返回，也可以是以回调接口的方式传递，这里用虚箭头表示。

MVC 和 MVVM、MVP 一样，只是一种设计典范，有多种实现形式，例如 ASP.NET MVC 中，控制器（Controller）只是充当了一个 router 的作用，根据用户的请求，返回不同的页面（View），每一个页面调用 db 对象获取数据（Model），展示到页面中。在 JSP 中，控制器是 servlet，视图是 jsp 页面，模型是 bean 对象和 db。

在 Android 中，MVC 又各表示什么呢？Activity 属于控制器，它接收了所有的用户输入请求；layout.xml 等各种界面布局属于视图；各种 bean、repository 等属于模型。不过在 Android 中，也可以把 Activity 也看作视图，它响应用户的输入，从模型层获取数据，进而控制视图的显示与隐藏，主要原因是 xml 没有自处理的能力，只能靠 Activity 来控制，这样就只能把 Activity 和 xml 等都归属于视图，类似在 iOS 中 ViewController 的作用。所以在 Android 中使用 MVC 模式，很容易就写出超级复杂的 Activity 类，业务越复杂，代码量越膨胀，经常出现万能 Activity。在 iOS 中也一样，iOS 中直接将 View 和 Controller 合成了一个，名字就叫 ViewController，这也是目前在 Android 中使用 MVC 一个非常不好的地方。

很多文章会说 MVC 中的 Model 层不能解耦，个人觉得是一种错误的解释。MVC 的出现正是为了将用户输入、视图显示、业务逻辑分离，实现解耦。之所以会给大家留下 Model 层不能和 View、Controller 解耦的现状，其实是因为并没有将 Model 层抽象出来，Model 层属于 Controller、View 的下层，可以以接口的形式来给出，这样接口和实现即可分离，为何不能解耦？

下面将以一个 Android 的代码示例来说明 MVC 的使用。

#### View
#####activity\_home.xml
```
<?xml version="1.0" encoding="utf-8"?>
<LinearLayout xmlns:android="http://schemas.android.com/apk/res/android"
                android:layout_width="match_parent"
                android:layout_height="match_parent"
                android:background="@color/background"
                android:orientation="vertical">

    <RelativeLayout
        android:id="@+id/toolbar"
        android:layout_width="match_parent"
        android:layout_height="@dimen/toolbar_height"
        android:layout_alignParentTop="true"
        android:background="@color/base_color">

        <ImageView
            android:id="@+id/btn_back"
            android:layout_width="wrap_content"
            android:layout_height="match_parent"
            android:layout_centerVertical="true"
            android:paddingLeft="16dp"
            android:paddingRight="16dp"
            android:src="@drawable/selector_button_title_back"/>

        <TextView
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:layout_centerInParent="true"
            android:text="@string/app_name"
            android:textColor="@color/white"
            android:textSize="18sp"/>
    </RelativeLayout>

    <ListView
        android:id="@+id/list"
        android:layout_width="match_parent"
        android:layout_height="0dp"
        android:layout_weight="1"
        android:groupIndicator="@android:color/transparent"/>

</LinearLayout>
```
这里包含一个标题栏，一个列表。

####Controller
#####HomeActivity.java
```
public class HomeActivity extends AppCompatActivity {

    private ListView mList;
    private HomeListAdapter mListAdapter;

    private TaskRepository mRepository;
    private BaseScheduler mScheduler;
    private BaseThread mMainThread;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        mList = (ListView)findViewById(R.id.list);

        mListAdapter = new HomeListAdapter(this);
        mList.setAdapter(mListAdapter);

        mRepository = TaskRepository.getInstance(
                Injection.provideLocalProxy(),
                Injection.provideRemoteProxy());
        mScheduler = ThreadPoolScheduler.getInstance();
        mMainThread = new HandlerThread();
    }

    @Override
    public void onResume() {
        super.onResume();
        initPage();
    }

    private void initPage() {
        mScheduler.execute(new Runnable() {
            @Override
            public void run() {
                List<Task> tasks = mRepository.getTasks();
                onTasksLoaded(tasks);

                tasks = mRepository.refreshTasks();
                onTasksLoaded(tasks);
            }
        });
    }

    private void onTasksLoaded(final List<Task> tasks) {
        if (tasks != null) {
            mMainThread.post(new Runnable() {
                @Override
                public void run() {
                    mListAdapter.setTasks(tasks);
                }
            });
        }
    }
}
```
这里直接使用 TaskRepository 获取数据，获取到之后，设置给 list 的 adapter 去展示。先调用 getTasks 获取本地数据，通知界面更新之后，再调用 refreshTasks 获取服务端数据来做刷新。

####Model
#####TaskRepository.java
```
public class TaskRepository implements Repository {

    private static TaskRepository sInstance;

    public static TaskRepository getInstance() {
        if (sInstance == null) {
            synchronized (TaskRepository.class) {
                if (sInstance == null) {
                    sInstance = new TaskRepository();
                }
            }
        }
        return sInstance;
    }

    public static TaskRepository getInstance(LocalProxy localProxy, RemoteProxy remoteProxy) {
        TaskRepository taskRepository = getInstance();
        taskRepository.setLocalProxy(localProxy);
        taskRepository.setRemoteProxy(remoteProxy);
        return taskRepository;
    }

    private LocalProxy mLocalProxy;
    private RemoteProxy mRemoteProxy;

    private TaskRepository() {
    }

    public void setLocalProxy(LocalProxy localProxy) {
        mLocalProxy = localProxy;
    }

    public LocalProxy getLocalProxy() {
        return mLocalProxy;
    }

    public RemoteProxy getRemoteProxy() {
        return mRemoteProxy;
    }

    public void setRemoteProxy(RemoteProxy remoteProxy) {
        mRemoteProxy = remoteProxy;
    }

    @Override
    public List<Task> getTasks() {
        return mLocalProxy.getAll();
    }

    @Override
    public List<Task> refreshTasks() {
        return mRemoteProxy.getAllTask();
    }
}
```

这里可以看到 TaskRepository 实现了 Repository 接口，定义如下

```
public interface Repository {

    List<Task> getTasks();

    List<Task> refreshTasks();
}
```
Model 层可以使用接口来抽象，达到解耦的目的。
####小结
* 对于复杂度不高的业务，使用 MVC，代码会比较少，比较直接，也能快速实现
* 对于没有太多合作的业务来说，可以使用 MVC，可以由同一人来实现 View 和 Controller 部分。
