package me.hao0.trace.core;

import com.twitter.zipkin.gen.Span;
import java.util.ArrayList;
import java.util.List;

/**
 * Author: haolin
 * Email:  haolin.h0@gmail.com
 */
public class TraceContext {

    /**
     * The trace chain global id
     */
    private static ThreadLocal<Long> TRACE_ID = new InheritableThreadLocal<>();

    /**
     * The previous trace span's id, it will be next span's parent id if it isn't null
     */
    private static ThreadLocal<Long> SPAN_ID = new InheritableThreadLocal<>();

    /**
     * The current trace's span list
     */
    private static ThreadLocal<List<Span>> SPANS = new InheritableThreadLocal<>();

    private TraceContext(){}

    public static void setTraceId(Long traceId){
        TRACE_ID.set(traceId);
    }

    public static Long getTraceId(){
        return TRACE_ID.get();
    }

    public static Long getSpanId() {
        return SPAN_ID.get();
    }

    public static void setSpanId(Long spanId) {
        SPAN_ID.set(spanId);
    }

    public static void addSpan(Span span){
        SPANS.get().add(span);
    }

    public static List<Span> getSpans(){
        return SPANS.get();
    }

    public static void clear(){
        TRACE_ID.remove();
        SPAN_ID.remove();
        SPANS.remove();
    }

    public static void start() {
        SPANS.set(new ArrayList<Span>());
    }

    public static void print(){
        System.err.println("Current thread: " + Thread.currentThread() + ", trace context: traceId="
                + getTraceId() + ", spanId=" + getSpanId() + ", spans=" + getSpans());
    }
}
