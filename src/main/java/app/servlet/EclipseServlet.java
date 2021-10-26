package app.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EclipseServlet
 * 
 * Este servlet se ha generado a partir de la plantilla predefinida de Eclipse IDE
 * 
 * El servlet esta mapeado con anotaciones
 */
@WebServlet(name = "EclipseServlet", urlPatterns = "/EclipseServlet")
public class EclipseServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public EclipseServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see Servlet#init(ServletConfig)
	 */
	public void init(ServletConfig config) throws ServletException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see Servlet#destroy()
	 */
	public void destroy() {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
//		Algunos de los datos que podemos obtener de la cabecera de la peticion:
		
//		request.getHeader(String x): De vuelve el valor de la cabecera indicada, null si no hay una cabecera con ese nombre
//		request.getHeaders(String x): Algunas cabeceras (como Accept-Language) pueden tener una lista de valores
//		request.getHeaderNames(): Devuelve el nombre de todas las cabeceras presentes
//		request.getMethod(): Indica el método HTTP: GET, POST
//		request.getQueryString(): Devuelve la query string que hay en el URL, o null si no hay
//		request.getParameter(): Devuelve el parámetro especificado
		
//		Parametros de la peticion:		
//		String parameter = request.getParameter("parameterName");
//		Map<String, String[]> parameterMap = request.getParameterMap();
		
//		Atributos de la aplicacion:
//		request.setAttribute("attributeName", attribute);
//		Permite pasar cualquier objeto entre los diferentes servlets de nuestra aplicacion diferentes cuando redirigimos
		
//		Para modificar o añadir valores a la cabecera de la respuesta:
		
//		Sustituir headers:
//		response.setHeader()
//		response.setDateHeader()
//		response.setIntHeader()
//		
//		Añadir headers:
//		response.addHeader()
//		response.addDateHeader()
//		response.addIntHeader()
//		setContentType() se utiliza para especificar el tipo MIME
//		setContentLenght()
//		...
		

		
//		response.setStatus(int code): codigo de respuesta (normalmente se utilizan constantes, tales como SC_OK, SC_NOT_FOUND)
//		response.sendError(int code, String mensaje): codigo y mensaje de error
//		response.sendRedirect(String url): redireccion a otra pagina (sin codigo, porque el cod. asociado 302)		
		
//		Redirecciones:
		
// 		- indirecta (manda una respuesta), utiliza siempre GET:
//		response.sendRedirect("URL");
		
// 		- directa (reenvia la peticion), solo dentro de la aplicacion, GET o POST segun sea la peticion original, permite el paso de datos como atributos
//		request.setAttribute("otro", "valor insertado desde EclipseServlet");
//		request.getRequestDispatcher("/NetbeansServlet").forward(request, response); // la respuesta la proporciona el servlet al que se reenvía
//		request.getRequestDispatcher("/NetbeansServlet").include(request, response); // la respuesta la manda aquí, y se incluye en la de éste
		
		System.out.println("param1=" + request.getParameter("param1"));
		System.out.println("filterAttribute=" + request.getAttribute("filterAttribute"));
		
		//response.getWriter().append("Served at: ").append(request.getContextPath());
		Map<String, String[]> parameterMap = request.getParameterMap();
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		printResponse(out, parameterMap);
		out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

	private PrintWriter printResponse(PrintWriter out, Map<String, String[]> parameterMap) {
		
		PrintWriter res = out;
		
		res.println("<html>");
		res.println("<title>Servlet de pruebas</title>");
		res.println("<body>");
		res.println("<div>Hola Mundo 2 (desde " + this.getClass().getSimpleName() +")</div>");
		parameterMap.keySet().forEach(x -> res.println("<div>Par&aacute;metro " + x + " = " + String.join(",", parameterMap.get(x)) +"</div>"));
		res.println("</body>");
		res.println("</html>");
		
		return res;
	}
	
}
