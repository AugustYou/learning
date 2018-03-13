package top.felixu.test;

import top.felixu.hunger.HungerSingleton;

import java.util.concurrent.CountDownLatch;

/**
 * @author : felixu
 * @createTime : 2018/3/12.
 * CountDownLatch在这里的用法可以看作是发令枪，可以理解为，运动员一个个入场，到达出发点，发令枪响，所有人一起跑
 * 这里就是循环启动线程，但是他们并没有start，而是在等待发令枪响，即count等于0的时候，每次countDown，count都会减1
 */
public class HungerTest {
    public static void main(String[] args) {
        int count = 2000;
        //发令枪，我就能想到运动员
        CountDownLatch latch = new CountDownLatch(count);
        long start = System.currentTimeMillis();
        for (int i = 0; i < count;i ++) {
            new Thread(){
                @Override
                public void run() {
                    try{

                        try {
                            // 阻塞
                            // count = 0 就会释放所有的共享锁
                            // 万箭齐发
                            latch.await();
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                        //必然会调用，可能会有很多线程同时去访问getInstance()
                        HungerSingleton hungerSingleton = HungerSingleton.getInstance();
                        System.out.println(System.currentTimeMillis() + ":" + hungerSingleton);

                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }.start(); //每循环一次，就启动一个线程,具有一定的随机性
            //每次启动一个线程，count --
            latch.countDown();
        }
        long end = System.currentTimeMillis();
        System.out.println("总耗时：" + (end - start));
    }
}
