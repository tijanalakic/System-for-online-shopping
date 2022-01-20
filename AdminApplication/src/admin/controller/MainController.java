package admin.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.mysql.cj.util.StringUtils;

import admin.bean.UserBean;
import admin.dto.Role;
import admin.dto.User;

/**
 * Servlet implementation class MainController
 */
@WebServlet("/Controller")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String USER = "user";
	private static final String USER_BEAN = "userBean";
	private static final String ACTION = "action";

	// Pages
	public static final String LOGIN_PAGE = "/WEB-INF/login.html";
	public static final String HOME_PAGE = "/WEB-INF/pages/home.jsp";

	// Actions
	private static final String ACTION_ADD_USER = "add-user";
	private static final String ACTION_DELETE_USER = "remove-user";
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
		session.setAttribute(USER_BEAN, new UserBean());

		String address = HOME_PAGE;
		String action = request.getParameter(ACTION);

		if (session.getAttribute("attack") != null) {
			UserBean userBean = (UserBean) session.getAttribute(USER_BEAN);
			userBean.logout();
			session.invalidate();
			address = "/";
		} else if (ACTION_ADD_USER.equals(action)) {

			String username = request.getParameter("newusername");
			String password = request.getParameter("password");
			String role = request.getParameter("role");

			if (StringUtils.isNullOrEmpty(username) || StringUtils.isNullOrEmpty(password)
					|| StringUtils.isNullOrEmpty(role)) {
				session.setAttribute("notification", "Username or password is empty!");
			} else {

				UserBean userBean = new UserBean();
				User user = new User();
				user.setUsername(username);
				user.setPassword(password);

				if (role.equals(Role.ADMIN.getRole())) {
					user.setRole(Role.ADMIN);
				} else if (role.equals(Role.ADMIN_PRODUCT.getRole())) {
					user.setRole(Role.ADMIN_PRODUCT);
				} else {
					user.setRole(Role.CUSTOMER);
				}

				userBean.addUser(user);

			}
		} else if (ACTION_DELETE_USER.equals(action)) {

			int id = Integer.parseInt(request.getParameter("id"));
			UserBean userBean = new UserBean();
			userBean.deleteUser(id);

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
