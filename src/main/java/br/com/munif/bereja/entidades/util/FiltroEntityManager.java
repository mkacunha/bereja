/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.com.munif.bereja.entidades.util;

import br.com.munif.bereja.entidades.Usuario;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author munif
 */
@WebFilter(filterName = "FiltroEntityManager", urlPatterns = {"/*"})
public class FiltroEntityManager implements Filter {

    // The filter configuration object we are associated with.  If
    // this value is null, this filter instance is not currently
    // configured. 
    private FilterConfig filterConfig = null;

    public FiltroEntityManager() {
        criaUsuarioPadrao();
    }

    /**
     *
     * @param request The servlet request we are processing
     * @param response The servlet response we are creating
     * @param chain The filter chain we are processing
     *
     * @exception IOException if an input/output error occurs
     * @exception ServletException if a servlet error occurs
     */
    public void doFilter(ServletRequest request, ServletResponse response,
            FilterChain chain)
            throws IOException, ServletException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        Throwable problem = null;
        long inicio = System.currentTimeMillis();
        EntityManager em = Persistencia.getInstancia().getEntityManager();
        Persistencia.getInstancia().ip.set(request.getRemoteAddr().toString());
        String login;
     //   login = ((HttpServletRequest) request).getUserPrincipal().getName();
     //   Persistencia.getInstancia().login.set(login);

        //TODO descobrir a cervejaria do Usuario 
        try {
            em.getTransaction().begin();
            chain.doFilter(request, response);
            em.getTransaction().commit();
        } catch (Throwable t) {
            em.getTransaction().rollback();
            problem = t;
            t.printStackTrace();
        }
        Persistencia.getInstancia().destroyEntityManager();
        long tempo = System.currentTimeMillis() - inicio;
        HttpServletRequest hsr = (HttpServletRequest) request;
        System.out.println("-----> TEMPO " + tempo + "ms para " + hsr.getRequestURI());

        if (problem != null) {
            if (problem instanceof ServletException) {
                throw (ServletException) problem;
            }
            if (problem instanceof IOException) {
                throw (IOException) problem;
            }
            sendProcessingError(problem, response);
        }
    }

    /**
     * Return the filter configuration object for this filter.
     */
    public FilterConfig getFilterConfig() {
        return (this.filterConfig);
    }

    /**
     * Set the filter configuration object for this filter.
     *
     * @param filterConfig The filter configuration object
     */
    public void setFilterConfig(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
    }

    /**
     * Destroy method for this filter
     */
    public void destroy() {
    }

    /**
     * Init method for this filter
     */
    public void init(FilterConfig filterConfig) {
        this.filterConfig = filterConfig;
        if (filterConfig != null) {
        }
    }

    /**
     * Return a String representation of this object.
     */
    @Override
    public String toString() {
        if (filterConfig == null) {
            return ("FiltroEntityManager()");
        }
        StringBuffer sb = new StringBuffer("FiltroEntityManager(");
        sb.append(filterConfig);
        sb.append(")");
        return (sb.toString());
    }

    private void sendProcessingError(Throwable t, ServletResponse response) {
        String stackTrace = getStackTrace(t);

        if (stackTrace != null && !stackTrace.equals("")) {
            try {
                response.setContentType("text/html");
                PrintStream ps = new PrintStream(response.getOutputStream());
                PrintWriter pw = new PrintWriter(ps);
                pw.print("<html>\n<head>\n<title>Error</title>\n</head>\n<body>\n"); //NOI18N

                // PENDING! Localize this for next official release
                pw.print("<h1>The resource did not process correctly</h1>\n<pre>\n");
                pw.print(stackTrace);
                pw.print("</pre></body>\n</html>"); //NOI18N
                pw.close();
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        } else {
            try {
                PrintStream ps = new PrintStream(response.getOutputStream());
                t.printStackTrace(ps);
                ps.close();
                response.getOutputStream().close();
            } catch (Exception ex) {
            }
        }
    }

    public static String getStackTrace(Throwable t) {
        String stackTrace = null;
        try {
            StringWriter sw = new StringWriter();
            PrintWriter pw = new PrintWriter(sw);
            t.printStackTrace(pw);
            pw.close();
            sw.close();
            stackTrace = sw.getBuffer().toString();
        } catch (Exception ex) {
        }
        return stackTrace;
    }

    public void log(String msg) {
        filterConfig.getServletContext().log(msg);
    }

    private void criaUsuarioPadrao() {
        System.out.println("Verificando Usuários");
        EntityManager em = Persistencia.getInstancia().getEntityManager();
        em.getTransaction().begin();
        boolean temAdministrador = false;
        Query q = em.createQuery("from Usuario u");
        List<Usuario> usuarios = q.getResultList();
        System.out.println("Usuários " + usuarios.size());
        if (usuarios.isEmpty()) {
            System.out.println("Base sem usuário, criando usuário padrão");
            Usuario u = new Usuario();
            u.setEmail("admin");
            u.setSenha("123");
            em.persist(u);
        }
        em.getTransaction().commit();
    }

}
