import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * @author Dragon code!
 * @create 2022-07-27 20:37
 */
public class CompletableFutureDemo {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        //实现主线程开启的同时运行子线程并获取子线程的结果要用到我们的FutureTask类封装我们的子线程
        FutureTask<String> futureTask = new FutureTask<String>(new MyThread());
        //开启线程,Thread参数是要求我们runnable接口的实现对象，不可以直接传我们的callable接口的实现类对象，
        //哪么解决办法就是FutureTask其实是实现了我们的Runnable接口，所以futureTask其实就是我们的Runnable接口的实现类对象
        new Thread(futureTask,"t1").start();
        //获取子线程执行的结果
        System.out.println(futureTask.get());
    }
}

/**
 * 执行具体的逻辑
 */
class MyThread implements Callable<String> {
    public String call() throws Exception {
        System.out.println("---- come in call() ----异步执行");
        System.out.println("我就测试一下分支切换之前进行我们的commit操作会不会将改变带到另一个分支");
        return "hello Callable 返回值";
    }
}
