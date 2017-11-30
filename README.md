# UESTC  
邦清答辩移动端技术支持  
## 1. MVP模式  
之前用过现成的MVP模式的框架，但是当自己写的时候还是有点懵逼，期间改过了一次，思路就稍微有点清晰了。接下来就说一说这个项目中的MVP模式是怎么个情况：由于这个项目有两个Activity，其中的LoginAcitvity比较简单，就是实现一个登录的功能，所以在MVP上并没有什么能学到的，另一个AnswerActivity作为主界面，在MVP方面还是有可以学习和思考的地方的，所以下文中的MVP都是说的AnswerActivity的MVP模式。  
### 1.1 顶层接口  
最顶层的接口有  
* IMVPPresenter  
* IMVPModel  
* IMVPView  
这三个并没有定义任何东西，只是作为一个顶层的接口；  
### 1.2 第二层接口
第二层的接口：  
* BasePresenter  
* BaseView  
* BaseModel  
这些接口中都定义了一些一定，必须实现的方法；  
```  
public interface BaseModel extends MVPModel {
}

public interface BaseView extends MVPView {

    EventListener getListener();
    void setEventListener(EventListener eventListener);
    void initView(ClassList classList);
    void initFragment(CurrentClass currentClass,int position);
}  

public interface BasePresenter extends MVPPresenter {
    void attach(BaseView answerView, BaseModel answerModel);
    //List getInitDataFromModel();
    //void pushScores();
    //List getClassDetails();
    BaseView getView();
    BaseModel getModel();
}  
```  
看在BasePresenter中定义的方法：  
* void attach（BaseView answerView, BaseModel answerModel）  
* BaseView getView（）  
* BaseModel getModel（）  

这三个方法分别用来绑定，得到View对象，得到Model对象，这里并没有在BasePresenter中定义BaseView和BaseModel变量，但是却知道有这么两个方法需要后续实现，所以就再次定义了，让后来实现这个接口的类来实现；这就体现了面向接口编程的特点，**只注重需求，不注重实现**。之前在这里的时候卡住了，因为BaseView的实现类除了BaseView接口定义的这几个方法外，还有可能有其他的方法在Presenter内部被调用，那么这里getView返回一个BaseView不就没法调用那个方法了吗？我后来改MVP的时候的理解是，我们之所以在BasePresenter的getView方法中返回一个BaseView这么一个抽象的对象（这里的抽象指的是，对于功能的实现太少，太过于抽象），而不是返回一个最终BaseView的实现类（功能全面，具体）。首先，不管返回哪一个，都是能够调用BaseView中的方法的，但是如果返回的是具体的实现类，那么弊端是各种方法调用导致太乱，可读性降低，后期读代码的时候逻辑混乱；View和Model都是具体的实现类，导致MVP模式不是很清晰。因此，在这一步一定要返回BaseView和BaseModel这种抽象的对象，而不是具体的实现类，如果遇到了需要调用具体的实现类中的方法，那就对BaseView或者BaseModel做一个强制类型转换的操作。  
### 1.3 抽象类  
在第二层的接口中我们定义了一些方法，这些方法是我们根据需求，推断出的一定会使用到的方法，但是怎么使用却不能一下子看出，但是到了抽象类这里，我们已经可以知道一些接口的方法的实现框架是什么样了（注意：这里是指接口里的方法的大体怎么实现，不是说具体的实现逻辑），进而对这些方法做一些完善。这体现了抽象类与接口的不同，**接口是一种标准，抽象类只是这种标准下的中间产物，是一种待加工的产品，最后的产品一定是实现类**。  
```  
abstract class AnswerModel implements BaseModel{
}  

public abstract class AnswerView extends AppCompatActivity implements BaseView {

    protected EventListener listener;

    @Override
    public EventListener getListener() {
        return listener;
    }

    @Override
    public void setEventListener(EventListener listener) {
        this.listener = listener;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}  

abstract class AnswerPre implements BasePresenter {

    protected BaseView mAnswerView;
    protected BaseModel mAnswerModel;

    protected Activity activity;
    @Override
    public void attach(BaseView baseView, BaseModel baseModel) {
        this.mAnswerModel=baseModel;
        this.mAnswerView=baseView;
        baseView.getListener().attachPresenter(this);
    }

    public BaseModel getModel() {
        return mAnswerModel;
    }

    @Override
    public BaseView getView() {
        return mAnswerView;
    }
}
```  
可以看到，在Presenter的抽象类中，我们实现了getView和getModel方法，这两个方法的实现框架我们是可以想到的，所以就在这里加工了，而不是选择在具体的实现类中去实现，这样一来就降低了实现类中的代码的量。**降低实现类中的代码量，提高MVP模式的清晰度**这就是MVP中抽象类作为中间产品的作用。  
### 1.4 实现类  
到了实现类的时候，反而变得简单了，实现类对于继承抽象类或者实现接口，其中的方法该实现的去实现就好了，在实现过程中，发现其他的需求则增加自己的方法就可以了。  
## 2. MVP下的View和Model交互  
看上面的代码会发现有一个EventListener存在，在这个项目中任何View与Model的交互的方式都是View将需求传递给其内部的EventListener，然后Listener再传给Listener内部的Presenter，最后由Presenter传给Model。可能有些人会觉得多余，直接View将需求传递给Presenter不就好了，干啥中间多一个Listener？那样做的话，View中就要有一个Presenter变量来进行事件的传递，这么一来的话View中有Presenter的引用，Presenter中有View的引用，增加了代码之间的耦合，会使得可读性和逻辑性降低。而加入一个Listener，由Listener来通知Presenter则就使得这个流程显得清晰。  
```  
public interface EventListener extends Serializable{
    void attachPresenter(BasePresenter basePresenter);
    void callPresenterToRefreshFragment(String classID,int position);
}  

abstract class AnswerListener implements EventListener {
    protected BasePresenter presenter;
    public void attachPresenter(BasePresenter answerPre)
    {
        this.presenter=answerPre;
    }
    public BasePresenter getPresenter()
    {
        return this.presenter;
    }
}  

public class AnswerListenerImpl extends AnswerListener {

    public void callPresenterToPostScore(ScorePost data,int position)
    {
        ((AnswerPreImpl)getPresenter()).postScore(data,position);
    }

    @Override
    public void callPresenterToRefreshFragment(String classID,int position) {
        ((AnswerPreImpl)getPresenter()).refreshFragment(classID,position);
    }
}
```  
Listener的设计和MVP很像，也是根据需求得到接口，在逐步加工得到实现类。  
## 3. 监听EditText内容的变化限定只能输入一位小数  
由于后台需要上传的打分只能是一为小数，但是用户可能输入的字符是不一定的，单单是依靠EditText的属性inputType是无法满足需求的，所以就需要设置监听来进行约束。好在Android内部有一个TextWatcher专门用来实现这一功能，这是一个接口，根据需求实现功能需要重写三个方法：  
```  
public class MyTextWatcher implements TextWatcher {
    @Override
    public void beforeTextChanged(CharSequence s, int start, int count, int after) {

    }

    @Override
    public void onTextChanged(CharSequence s, int start, int before, int count) {

    }

    @Override
    public void afterTextChanged(Editable s) {
        switch (s.length())
        {
            case 0:
                break;
            case 1:
                oneChar(s);
                break;
            case 2:
                twoChar(s);
                break;
            case 3:
                threeChar(s);
                break;
            case 4:
                fourChar(s);
                break;
            case 5:
                fiveChar(s);
                break;
            default:
                fiveChar(s.delete(5,s.length()-1));
                break;
        }
    }
```  
根据名字我们就能够看出三个方法分别在“改变前”“改变时”“改变后”被调用，我们通常是通过重写第三个方法来实现功能：
```
private void oneChar(Editable s)
    {
        if(s.toString().equals("."))
            s.delete(0,1);
    }

    private void twoChar(Editable s)
    {
        if(!s.toString().endsWith(".")&&s.toString().startsWith("0"))
            s.delete(0,1);
    }

    /*
    处理x.x   x..   xx.   xxx  三种情况
    其中需要处理的是x.. 和xxx二重情况
     */
    private void threeChar(Editable s)
    {
        if(s.toString().contains(".."))
            s.delete(2,3);
        if(!s.toString().contains("."))
        {
            s=Integer.parseInt(s.toString())>100?s.delete(2,3):s;
        }
    }

    /*
    处理100.  100x  xx..  xx.x  x.x.  x.xx四种情况
    需要处理的只有100x  xx..  x.x.  x.xx四种
     */
    private void fourChar(Editable s)
    {
        //100x
        if(s.toString().contains("100")&&!s.toString().endsWith("."))
            s.replace(s.length()-1,s.length(),".");
        //xx..
        if(s.toString().endsWith(".."))
            s.delete(s.length()-1,s.length());
        //x.x.
        if(s.toString().endsWith(".")&&!s.toString().endsWith("..")&&!s.toString().contains("100"))
            s.delete(s.length()-1,s.length());
        //x.xx
        if(s.subSequence(0,2).toString().endsWith(".")&&!s.toString().endsWith("."))
            s.delete(s.length()-1,s.length());
    }

    /*
    处理100.x  100..  xx.x.  xx.xx
     */
    private void fiveChar(Editable s)
    {
        if(s.toString().contains("100.")&&!s.toString().endsWith("0"))
            s.replace(s.length()-1,s.length(),"0");
        if(!s.toString().contains("100."))
            s.delete(s.length()-1,s.length());
    }
```
## 4. 类似于QQ的保存登录状态  
之前没有接触过这种需要客户提交登录到后台的处理，接触过以后，对这种看起来复杂的逻辑有了更加清晰的认识，原来是如此的简单：  
### 4.1 登录  
登录需要我们提交用户名和密码，如果登录成功的话，返回的是一个token；其实重要的就是这个token字符串，一般这种需要登录的东西，后续的网络数据都需要根据你的身份（实际上这个token就唯一标记了一个用户）来进行请求，所以只要我们在第一次登录成功后保存了这个token，之后登录就可以跳过登录界面的输入密码和用户名的过程。通常保存这个token是用的SharedPreferences实现的：  
```  
  /*
        如果SharedPreferences中的token数据为空，说明是第一次登陆
        则启动loginActivity；如果不为空，就跳过loginActivity界面，直接启动主界面
         */
        TOKEN=preferences.getString("token",null);

        /*
        如果是第一次登陆，保存user信息到本地
         */
        if(getIntent().getStringExtra("token")!=null)
        {
            //这里的editor是SharePreferences.editor()
            TOKEN=getIntent().getStringExtra("token");
            SharedPreferences.Editor editor=preferences.edit();
            editor.putString("username",getIntent().getStringExtra("username"));
            editor.putString("token",getIntent().getStringExtra("token"));
            editor.apply();
        }  
        
        if(TOKEN==null)
        {
            Intent intent=new Intent(this, LoginActivity.class);
            pre.loadInitialDataWithoutFragment();
            startActivity(intent);
        }
```
## 5. 增加需求-RecyclerView滑到底弹出提示  
对于如何判断RecyclerView是否滑动到了底部，这个问题我是第一次接触。有必要特别记录一下。  
```
class MyScrollListener extends RecyclerView.OnScrollListener{
        int visiableLastPosition;
        LinearLayoutManager manager;
        public void onScrollStateChanged(RecyclerView recyclerView, int newState){
            super.onScrollStateChanged(recyclerView,newState);
            //manager=(LinearLayoutManager) recyclerView.getLayoutManager();
            //visiableLastPosition=manager.findLastVisibleItemPosition();
            if(newState==RecyclerView.SCROLL_STATE_IDLE&&visiableLastPosition==manager.getItemCount()-1)
                ((AnswerActivity)answerView).showToast("已经到底了");

        }
        public void onScrolled(RecyclerView recyclerView, int dx, int dy){
            super.onScrolled(recyclerView,dx,dy);
            manager=(LinearLayoutManager) recyclerView.getLayoutManager();
            visiableLastPosition=manager.findLastVisibleItemPosition();
        }
    }      
```

实现就是通过加一个滑动的监听就可以了，根据源代码的注释可以了解到，这个抽象类的第一个方法调用在滑动的时候，第二个方法调用在滑动结束的时候。  
1. manager.findLastVisibleItemPosition():这个方法的意思是，返回手机屏幕中的RecyclerView所显示的最后一个视图在adapter数据源中的position；  所以只要这个position等于所有的数据的数目-1，就说明这个最后一个数据了，也就是visiableLastPosition==manager.getItemCount()-1；  
2. 而newState有三个值：  
    * int SCROLL_STATE_DRAGGING：The RecyclerView is currently being dragged by outside input such as user touch input.  
    * int SCROLL_STATE_IDLE：The RecyclerView is not currently scrolling. 
    * int SCROLL_STATE_SETTLING：The RecyclerView is currently animating to a final position while not under outside control.
