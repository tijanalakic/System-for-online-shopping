package manager.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.util.StringUtils;

import manager.bean.UserBean;
import manager.bean.ProductBean;
import manager.dto.Product;

/**
 * Servlet implementation class MainController
 */
@WebServlet("/Controller")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String USER = "user";
	private static final String PRODUCT_BEAN = "productBean";
	private static final String USER_BEAN = "userBean";
	private static final String ACTION = "action";

	// Pages
	public static final String LOGIN_PAGE = "/WEB-INF/login.html";
	public static final String HOME_PAGE = "/WEB-INF/pages/home.jsp";

	// Actions
	private static final String ACTION_ADD_PRODUCT = "add-product";
	private static final String ACTION_DELETE_PRODUCT = "remove-product";
	private static final String ACTION_LOGOUT = "logout";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MainController() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub

		request.setCharacterEncoding("UTF-8");
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(1800);

		session.setAttribute(USER, request.getRemoteUser());
		session.setAttribute(PRODUCT_BEAN, new ProductBean());
		session.setAttribute(USER_BEAN, new UserBean());
		String address = HOME_PAGE;
		String action = request.getParameter(ACTION);

		if (session.getAttribute("attack") != null) {
			UserBean userBean = (UserBean) session.getAttribute(USER_BEAN);
			userBean.logout();
			session.invalidate();
			address = "/";
		} else if (ACTION_ADD_PRODUCT.equals(action)) {

			String productName = request.getParameter("productName");
			String description = request.getParameter("description");
			Double price = Double.parseDouble(request.getParameter("price"));

			if (StringUtils.isNullOrEmpty(productName) || StringUtils.isNullOrEmpty(description)) {
				session.setAttribute("notification", "Product name or price is empty!");
			} else {

				Product product = new Product();
				product.setName(productName);
				;
				product.setDescription(description);
				product.setPrice(price);

				ProductBean.addProduct(product);

			}
		} else if (ACTION_DELETE_PRODUCT.equals(action)) {

			int id = Integer.parseInt(request.getParameter("id"));
			ProductBean productBean = new ProductBean();
			productBean.deleteProduct(id);

		} else if (ACTION_LOGOUT.equals(action)) {

			UserBean userBean = (UserBean) session.getAttribute(USER_BEAN);
			userBean.logout();
			session.invalidate();
			address = "/";

		}

		RequestDispatcher dispatcher = request.getRequestDispatcher(address);
		dispatcher.forward(request, response);

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
