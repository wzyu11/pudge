package io.transwarp.pudge.spring.annotation;

import io.transwarp.pudge.spring.server.PudgeServerAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * Created by Nirvana on 2017/12/15.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(PudgeServerAutoConfiguration.class)
public @interface EnablePudgeServer {
}
