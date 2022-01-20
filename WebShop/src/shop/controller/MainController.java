package shop.controller;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import shop.bean.ProductBean;
import shop.bean.TransactionBean;
import shop.bean.UserBean;
import shop.dto.Item;

/**
 * Servlet implementation class MainController
 */
@WebServlet("/Controller")
public class MainController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String USER = "user";
	private static final String PRODUCT_BEAN = "productBean";
	private static final String TRANSACTION_BEAN = "transactionBean";
	private static final String USER_BEAN = "userBean";
	private static final String ACTION = "action";

	// Pages
	public static final String LOGIN_PAGE = "/WEB-INF/login.html";
	public static final String HOME_PAGE = "/WEB-INF/pages/home.jsp";
	public static final String SHOP_PAGE = "/WEB-INF/pages/shop.jsp";

	// Actions
	private static final String ACTION_ADD_ITEM = "add-item";
	private static final String ACTION_BUY = "buy";
	private static final String ACTION_BACK = "back";
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

		TransactionBean transactionBean = new TransactionBean();
		ProductBean productBean = new ProductBean();
		UserBean userBean = (UserBean) session.getAttribute(USER_BEAN);

		if (session.getAttribute("attack") != null) {
			userBean.logout();
			session.invalidate();
			address = "/";
		} else if (ACTION_ADD_ITEM.equals(action)) {

			Item item = new Item();
			int id = Integer.parseInt(request.getParameter("id"));
			int quantity = Integer.parseInt(request.getParameter("quantity"));

			if (id < 1 || quantity < 1) {
				session.setAttribute("notification", "Item quantity is empty!");
			} else {

				item.setProduct(productBean.getProductById(id));
				item.setQuantity(quantity);
				transactionBean.addItem(item);

			}
		} else if (ACTION_BUY.equals(action)) {

			transactionBean.setUser((String) session.getAttribute(USER));
			transactionBean.createTransaction();
			session.setAttribute(TRANSACTION_BEAN, transactionBean);
			address = SHOP_PAGE;

		} else if (ACTION_BACK.equals(action)) {

			transactionBean = new TransactionBean();
			address = HOME_PAGE;

		} else if (ACTION_LOGOUT.equals(action)) {

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
