package com.fibers.core;

import co.paralleluniverse.fibers.Fiber;
import co.paralleluniverse.fibers.SuspendExecution;

import java.util.concurrent.ExecutionException;

/**
 * Created by mohan.pandian on 10/03/17.
 */
public class FiberCallableTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException, SuspendExecution {
        FiberExecutorCompletionService<String> completionService = new FiberExecutorCompletionService();

        startFiber(completionService, "Hello World 1", 3000);
        startFiber(completionService, "Hello World 2", 2000);
        startFiber(completionService, "Hello World 3", 1000);
        System.out.println(completionService.take().get());
        System.out.println(completionService.take().get());
        System.out.println(completionService.take().get());
    }

    private static void startFiber(FiberExecutorCompletionService completionService, String message, long sleep) {
        new Fiber<Void>() {
            @Override
            protected Void run() throws SuspendExecution, InterruptedException {
                completionService.submit(new FiberCallable(message, sleep));
                return null;
            }
        }.start();
    }
}
