package com.lynn.user.aop;

import com.alibaba.fastjson.JSON;
import com.google.common.collect.Lists;
import com.lynn.user.result.Code;
import com.lynn.user.result.Result;
import com.lynn.user.result.SingleResult;
import org.apache.commons.lang.StringUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.aop.ThrowsAdvice;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

@Component
@Aspect
public class WebExceptionAspect implements ThrowsAdvice{

    public static final Logger logger = LoggerFactory.getLogger(WebExceptionAspect.class);

    @Pointcut("@annotation(org.springframework.web.bind.annotation.GetMapping)")
    private void webPointcut() {
    }

    @AfterThrowing(pointcut = "webPointcut()",throwing = "e")
    public void afterThrowing(Exception e) throws Throwable {
        logger.debug("exception 来了！");
        SingleResult<String> result = new SingleResult<>();
        result.setCode(Code.ERROR);
        if(StringUtils.isNotBlank(e.getMessage())){
            result.setMessage(e.getMessage());
        }
        writeContent(JSON.toJSONString(result));
    }

    /**
     * 将内容输出到浏览器
     *
     * @param content
     *            输出内容
     */
    private void writeContent(String content) {
        HttpServletResponse response = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes())
                .getResponse();
        response.reset();
        response.setCharacterEncoding("UTF-8");
        response.setHeader("Content-Type", "text/plain;charset=UTF-8");
        response.setHeader("icop-content-type", "exception");
        PrintWriter writer = null;
        try {
            writer = response.getWriter();

            writer.print((content == null) ? "" : content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
