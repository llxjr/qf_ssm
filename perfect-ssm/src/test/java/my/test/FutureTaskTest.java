package my.test;

import java.util.Date;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.FutureTask;

/** 
 * @description 
 * @author  chj 
 * @date 创建时间：2018-6-5 上午9:25:52 
 * @version 1.0 
 * @since  
 */
public class FutureTaskTest {
	private static final Executor exec = Executors.newFixedThreadPool(5);
	
	static Runnable runnable = new Runnable() {
		@Override
		public void run() {
			for (int i = 0; i < 5000; ++ i) {
				System.out.println("Thread ID: " + Thread.currentThread().getId() 
						+ ", name: " + Thread.currentThread().getName());
			}
		}
	};
	Callable<Object> callable = new Callable<Object>() {
		@Override
		public Object call() throws Exception {
			return null;
		}
		
	};
	
	public static void main(String[] args) {
		ExecutorService executor = Executors.newSingleThreadExecutor();  
        FutureTask<String> task = new FutureTask<String>(new Callable<String>() {  
  
            @Override  
            public String call() throws Exception {  
                long b = new Date().getTime();  
                System.out.println("call begin " + b);     
                StringBuilder sb = new StringBuilder();  
                for (int i = 0; i < 100000; i++) {  
                    sb.append(i).append(",");  
                }  
                System.out.println("call end " + (new Date().getTime() - b));  
                return sb.toString();  
            }  
        });  
        executor.execute(task);  
        long begin = new Date().getTime();  
        System.out.println("begin" + begin);  
        try {  
            // System.out.println(task.get());  
            task.get();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } catch (ExecutionException e) {  
            e.printStackTrace();  
        }  
        System.out.println("end  " + (new Date().getTime() - begin));  
        begin = new Date().getTime();  
        System.out.println("begin" + begin);  
        try {  
            task.get();  
        } catch (InterruptedException e) {  
            e.printStackTrace();  
        } catch (ExecutionException e) {  
            e.printStackTrace();  
        }  
        System.out.println("end  " + (new Date().getTime() - begin));  
        executor.shutdown();  
	}
}
