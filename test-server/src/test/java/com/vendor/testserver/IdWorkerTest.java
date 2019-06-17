package com.vendor.testserver;

import java.util.*;


public class IdWorkerTest {


    static class IdWorkThread implements Runnable {

        private Set<Long> set;

        private IdWorker idWorker;

        private List<Long> list = new ArrayList<>();
        private Map<Long,String> map = new HashMap<>();
        private Map<Long,String> map2 = new Hashtable<>();
        private Vector<Long> testVector = new Vector<>();


        public int getCurIndex() {
            return curIndex;
        }

        public void setCurIndex(int curIndex) {
            this.curIndex = curIndex;
        }

        private int curIndex ;


        public IdWorkThread(Set<Long> set, IdWorker idWorker) {


            this.set = set;

            this.idWorker = idWorker;

            this.curIndex = 0;

        }


        public void run() {

            while (true) {

                long id  = idWorker.nextId();

                if (curIndex % 1000000 == 0) {
                    System.out.println("            real id:" + id);
                }


                if (!set.add(id)) {

                    System.out.println("duplicate:" + id);
                }

                curIndex++;
            }

        }

    }


    public static void main(String[] args) {

        Set<Long> set = Collections.synchronizedSet(new HashSet<Long>()) ;

        final IdWorker idWorker1 = new IdWorker(0, 0);

        final IdWorker idWorker2 = new IdWorker(1, 0);

        IdWorkThread workThread1 = new IdWorkThread(set, idWorker1);
        IdWorkThread workThread2 = new IdWorkThread(set, idWorker2);

        Thread t1 = new Thread(workThread1);

        Thread t2 = new Thread(workThread2);

        t1.setDaemon(true);

        t2.setDaemon(true);

        t1.start();

        t2.start();

        try {

            Thread.sleep(30000);

        } catch (InterruptedException e) {

            e.printStackTrace();
        }

        System.out.println("  workThread1 index:" + workThread1.getCurIndex());
        System.out.println("  workThread2 index:" + workThread1.getCurIndex());

    }

}