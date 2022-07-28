import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;
import java.util.concurrent.TimeUnit;

/**
 * @author Dragon code!
 * future配合线程池异步多线程任务的配合，能显著提高程序的执行效率
 * @create 2022-07-27 22:17
 */
public class FutureThreadPoolDemo {
    /**
     * 三个任务使用三个线程来处理，利用线程池（假如每次new一个Thread，太浪费资源，会有GC这些工作），大概400毫秒。
     */
    public static void main(String[] args) {
        //3个任务，目前开启多个异步任务线程来处理，请问耗时多少？
        long begin = System.currentTimeMillis();
        //先使用线程池工具类来创建三个线程的线程池
        ExecutorService threadPool = Executors.newFixedThreadPool(3);
        //接着我们来创建异步任务一
        FutureTask<String> futureTask1 = new FutureTask<String>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            return "任务一执行完毕";
        });

        //创建异步任务二
        FutureTask<String> futureTask2 = new FutureTask<String>(() -> {
            try {
                TimeUnit.MILLISECONDS.sleep(300);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return "任务二执行完毕";
        });

        //任务三直接由我们的主线程来执行
        try {
            TimeUnit.MILLISECONDS.sleep(300);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        /**
         * 按照之前的写法执行线程
         */
//        new Thread(futureTask1,"thread1").start();
//        new Thread(futureTask2,"thread2").start();


        /**
         * 进一步提升程序的性能，使用线程池做到线程复用，也避免了new线程之后会进行我们的垃圾回收！
         * 让我们的线程池来认领第一、二个异步任务，然后第三个交给我们的主线程来执行
         */

        threadPool.submit(futureTask1);
        threadPool.submit(futureTask2);
        long end = System.currentTimeMillis();
        System.out.println("----costTime: " +   (end - begin) + "毫秒");

        System.out.println(Thread.currentThread().getName() + "\t-------end");

        //关闭线程池的资源
        threadPool.shutdown();
    }
}
