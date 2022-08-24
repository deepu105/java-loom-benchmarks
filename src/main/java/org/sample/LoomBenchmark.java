package org.sample;


import org.openjdk.jmh.annotations.*;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

import java.time.Duration;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

@Fork(value = 1, jvmArgs = {"-Xms512m", "-Xmx1024m", "--enable-preview"})
@BenchmarkMode({Mode.Throughput, Mode.AverageTime})
@Warmup(time = 2)
@State(Scope.Benchmark)
@Timeout(time = 60)
@Threads(2)
public class LoomBenchmark {

    public static void main(String[] args) throws RunnerException {
        Options opt = new OptionsBuilder()
                .include(LoomBenchmark.class.getSimpleName())
                .forks(1)
                .build();

        new Runner(opt).run();
    }

    @Benchmark
    public long platformThreadPool() {
        try (var executor = Executors.newCachedThreadPool()) {
            IntStream.range(0, 10_000).forEach(i -> executor.submit(() -> {
                Thread.sleep(Duration.ofSeconds(1));
                System.out.println(i);
                return i;
            }));
        }
        return System.currentTimeMillis();
    }

    @Benchmark
    public long platformThreadPerTask() {
        try (var executor = Executors.newThreadPerTaskExecutor(Executors.defaultThreadFactory())) {
            IntStream.range(0, 10_000).forEach(i -> executor.submit(() -> {
                Thread.sleep(Duration.ofSeconds(1));
                System.out.println(i);
                return i;
            }));
        }
        return System.currentTimeMillis();
    }

    @Benchmark
    public long virtualThreadPerTask() {
        try (var executor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> executor.submit(() -> {
                Thread.sleep(Duration.ofSeconds(1));
                System.out.println(i);
                return i;
            }));
        }
        return System.currentTimeMillis();
    }
}
