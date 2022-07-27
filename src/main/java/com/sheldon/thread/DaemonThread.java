package com.sheldon.thread;

/**
 * @author fangxiaodong
 * @date 2021/08/11
 */
public class DaemonThread {

    public static void main(String[] args) {
        new WorkerThread().start();

        try {
            Thread.sleep(7500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        System.out.println("Main thread ending");
    }
}

class WorkerThread extends Thread{

    public WorkerThread() {
        setDaemon(true);
    }

    @Override
    public void run() {
        int count = 0;
        while(true){
            System.out.println("Hello from worker " + count++);

            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
