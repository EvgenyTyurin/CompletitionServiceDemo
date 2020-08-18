import java.util.concurrent.*;

/**
 * Demonstrate work of CompletionService class
 * Produce future results and then consume them
 */

public class CompletionServiceDemo {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        int taskN = 3;
        ExecutorService executorService = Executors.newFixedThreadPool(taskN);
        CompletionService<MyResult> completionService =
                new ExecutorCompletionService<>(executorService);
        // Generate future results
        for (int i = 1; i <= taskN; i++ )
            completionService.submit(new CalculateTask(i));
        // Consume and print results
         for (int i = 1; i <= taskN; i++ ) {
             Future<MyResult> future = completionService.take();
             System.out.println(future.get()); // 1*1=2, 2*2=4, 3*3=9
         }
        executorService.shutdown();
    }

    // Task to calculate result
    private static class CalculateTask implements Callable<MyResult> {
        private final int argument;

        public CalculateTask(int argument) {
            this.argument = argument;
        }

        @Override
        public MyResult call() {
            MyResult myResult = new MyResult(argument);
            myResult.setResult(argument * argument);
            return myResult;
        }
    }

    // Result contains argument and result for printing
    private static class MyResult {
        private final int argument;
        private int result;

        public MyResult(int argument) {
            this.argument = argument;
        }

        public void setResult(int result) {
            this.result = result;
        }

        @Override
        public String toString() {
            return "argument=" + argument +
                    " result=" + result;
        }
    }
}
