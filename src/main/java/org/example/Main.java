package org.example;

import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class Main {
    public static void main(String[] args) {
        int n = 10; // вычисление факториала для числа 10

        ForkJoinPool forkJoinPool = new ForkJoinPool();
        FactorialTask factorialTask = new FactorialTask(n);

        long result = forkJoinPool.invoke(factorialTask);
        System.out.println("Факториал " + n + "! = " + result);
    }

    static class FactorialTask extends RecursiveTask<Long> {

        private final long end;
        private final long start;
        public FactorialTask(int n) {
            this.end = n;
            this.start = 1;
        }
        public FactorialTask(long start, long end) {
            this.end = end;
            this.start = start;
        }

        @Override
        protected Long compute() {
            long result;
            long diff = end - start;
            if (diff <= 4) {
                result = 1;
                for (int i = 0; i <= diff; i++) {
                    result = result * (start + i);
                }
                return result;
            }
            FactorialTask task1 = new FactorialTask(start, start + diff/2);
            FactorialTask task2 = new FactorialTask(start + diff/2 + 1, end);
            task1.fork();
            task2.fork();
            return task1.join() * task2.join();
        }
    }
}
