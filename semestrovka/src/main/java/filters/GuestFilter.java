package filters;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebFilter(urlPatterns = {"/edit", "/profile"})
public class GuestFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        if (servletRequest.getSession(false) == null) {
            servletResponse.sendRedirect(servletRequest.getContextPath() + "/login");
        } else {
            if (servletRequest.getSession(false).getAttribute("user") == null) {
                servletResponse.sendRedirect(servletRequest.getContextPath() + "/login");
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }

}
