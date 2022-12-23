package filters;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import models.Account;

import java.io.IOException;

@WebFilter(urlPatterns = "/redactor")
public class SimpleUserFilter extends HttpFilter {
    @Override
    public void doFilter(HttpServletRequest servletRequest, HttpServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (servletRequest.getSession(false) == null) {
            servletResponse.sendRedirect(servletRequest.getContextPath() + "/login");
        } else {
            Account user = (Account) servletRequest.getSession(false).getAttribute("user");
            if (user == null || !user.getRole().equals("redactor")) {

                if (user == null) {
                    servletResponse.sendRedirect(servletRequest.getContextPath() + "/login");
                } else {
                    servletResponse.sendRedirect(servletRequest.getContextPath() + "/");
                }
            } else {
                filterChain.doFilter(servletRequest, servletResponse);
            }
        }
    }
}
