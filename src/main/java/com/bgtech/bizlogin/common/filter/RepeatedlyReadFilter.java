package com.bgtech.bizlogin.common.filter;

import com.bgtech.cloud.web.config.mvc.logger.filter.RepeatedlyReadRequestWrapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 反复写过滤器
 */
@Slf4j
public class RepeatedlyReadFilter implements Filter {
    private String excludedSuffix;

    public RepeatedlyReadFilter() {
    }

    public RepeatedlyReadFilter(String excludedSuffix) {
            this.excludedSuffix = excludedSuffix;
    }

    @Override
    public void init(FilterConfig config) throws ServletException {
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
            throws IOException, ServletException {
        ServletRequest requestWrapper = null;
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        if (this.ixExcluded(httpServletRequest)) {
            chain.doFilter(request, response);
            return;
        }
        if (request instanceof HttpServletRequest) {
            requestWrapper = new RepeatedlyReadRequestWrapper(httpServletRequest);
        }
        if (null == requestWrapper) {
            chain.doFilter(request, response);
        } else {
            chain.doFilter(requestWrapper, response);
        }
    }

    private boolean ixExcluded(HttpServletRequest request) {
        if (StringUtils.isEmpty(excludedSuffix)) {
            return false;
        }
        return request.getServletPath().endsWith(excludedSuffix);
    }

    @Override
    public void destroy() {

    }

}
