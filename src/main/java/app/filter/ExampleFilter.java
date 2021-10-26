package app.filter;

import java.io.CharArrayWriter;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class ExampleFilter
 */
@WebFilter(servletNames = { "EclipseServlet" })
public class ExampleFilter implements Filter {

    /**
     * Default constructor. 
     */
    public ExampleFilter() {
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Filter#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
		// Insertamos atributo en la petición
		request.setAttribute("filterAttribute", "atributo insertado en el request desde el filtro");
			
		// pass the request along the filter chain
		PrintWriter out = response.getWriter();
		HttpServletResponse httpResp = (HttpServletResponse) response;
		CharResponseWrapper wrapper = new CharResponseWrapper(httpResp);
        chain.doFilter(request, wrapper);
		
		// Añadimos texto a la salida
		if(wrapper.getContentType().startsWith("text/html")) {
			CharArrayWriter caw = new CharArrayWriter();
			caw.write(wrapper.toString().substring(0, wrapper.toString().indexOf("</body>")-1));
			caw.write("<p>\nTexto añadido desde filtro <font color='red'>" + this.getClass().getSimpleName() + "</font></p>");
			caw.write("\n</body></html>");
			response.setContentLength(caw.toString().length());
			out.write(caw.toString());
		} else {
		    out.write(wrapper.toString());
		}
		
		out.close();
	}

	/**
	 * @see Filter#init(FilterConfig)
	 */
	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
