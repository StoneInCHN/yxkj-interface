package com.yxkj.filter;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;

/**
 * @author luzhang
 * 请求过滤器
 *
 */
public class HttpServletRequestFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        if (request instanceof HttpServletRequest) {
            /* 
             * 因为流只能读一次, 读了就没有了(再读就会报Stream closed 的错)
             * spring读过request, 为了后面的代码(例如aspect里面)还能够取得流
             * 在自己建的BodyReaderHttpServletRequestWrapper里面进行手动的操作（缓存body数据）
             * 参考-->https://my.oschina.net/vernon/blog/363693
             */
            requestWrapper = new BodyReaderHttpServletRequestWrapper((HttpServletRequest) request);
        }
        if (null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    @Override
    public void destroy() {

    }
}
