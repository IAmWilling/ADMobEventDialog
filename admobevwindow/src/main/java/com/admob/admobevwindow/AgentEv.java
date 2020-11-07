package com.admob.admobevwindow;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
/**
 * @author 玉米
 * @date 2020-11-07
 * @describe 埋点注解
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface AgentEv {
    // Activity
    String act();

    //事件描述
    String desc() default "";
}
