package com.sheldon.thread.aqs;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author fangxiaodong
 * @date 2021/10/11
 */
public class FindBugAQS {

    public volatile static int FLAG = 0;

    private static ThreadLocal<Integer> FLAG_STORE = new ThreadLocal<>();

    private static ThreadLocal<Integer> TIMES = ThreadLocal.withInitial(() -> 0);

    private Sync sync = new Sync();

    private static class Sync extends AbstractQueuedSynchronizer {

        private Sync(){
            setState(1);
        }

        public void lock(){
            FLAG_STORE.set(++FLAG);
            int state = getState();
            if(state == 1 && compareAndSetState(state, 0)){
                return;
            }
            acquire(1);
        }

        @Override
        protected boolean tryAcquire(int arg) {
            if(FLAG_STORE.get() == 2){
                Integer time = TIMES.get();
                if(time == 0){
                    TIMES.set(1);
                }else{
                    throw new RuntimeException("aqs bug");
                }
            }

            int state = getState();
            if(state == 1 && compareAndSetState(state, 0)){
                return true;
            }

            return false;
        }

        @Override
        protected boolean tryRelease(int arg) {
            setState(1);
            return true;
        }

        public void unlock(){
            release(1);
        }
    }

    public void lock(){
        sync.lock();
    }

    public void unlock(){
        sync.unlock();
    }

    private static volatile int number = 0;

    public static void main(String[] args) throws InterruptedException {
        List<Thread> list = new ArrayList<>();
        FindBugAQS aqs = new FindBugAQS();
        Thread t1 = new Thread(()->{
            try {
                aqs.lock();
                Thread.sleep(5 * 1000);
                number++;
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                aqs.unlock();
            }
        });
        t1.start();
        list.add(t1);

        for (int i = 0; i < 4; i++){
            Thread t = new Thread(()->{
                try {
                    aqs.lock();
                    Thread.sleep(5 * 100);
                    number++;
                } catch (InterruptedException e) {
                    e.printStackTrace();
                } finally {
                    aqs.unlock();
                }
            });
            t.start();
            list.add(t);
        }

        for (Thread thread : list) {
            thread.join();
        }

        System.out.println(number);
    }
}
