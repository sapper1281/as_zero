/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class URLFilter implements Filter {

    public void destroy() {
// TODO Auto-generated method stub 
    }

    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain) throws IOException, ServletException {
// TODO Auto-generated method stub 
        HttpServletRequest httpServletRequest = ((HttpServletRequest) request);
        String servletPath = ((HttpServletRequest) request).getServletPath();
        if (servletPath.indexOf('.') < 0) {
            String dispatchURL = servletPath + ".xhtml";
            try {

                httpServletRequest.getRequestDispatcher(dispatchURL).forward(request, response);
            } catch (Exception e) {
            }
        } else{
            chain.doFilter(request, response);
        }

    }

    public void init(FilterConfig arg0) throws ServletException {
// TODO Auto-generated method stub 
    }
}
