package com.briup.estore.web.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.briup.estore.bean.Book;
import com.briup.estore.bean.Line;
import com.briup.estore.bean.ShoppingCart;
import com.briup.estore.common.exception.BookException;
import com.briup.estore.service.IBookService;
import com.briup.estore.service.impl.BookServiceImpl;

@WebServlet("/user/shopcartAdd")
public class ShopcartAddServlet extends HttpServlet{
	private static final long serialVersionUID = 1L;
	private IBookService bookService = new BookServiceImpl();
	
	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		long id = Long.parseLong(req.getParameter("id"));
		String path = "/user/shopcart.jsp";
		try {
			Book book = bookService.findById(id);
			if((req.getSession().getAttribute("shoppingCart"))==null){
				ShoppingCart sc = new ShoppingCart();
				
				HttpSession session = req.getSession();
				session.setAttribute("shoppingCart", sc);
			}
			ShoppingCart shoppingCart = (ShoppingCart)req.getSession()
					.getAttribute("shoppingCart");
			Line line = new Line();
			line.setBook(book);
			shoppingCart.add(line);
			
		} catch (BookException e) {
			e.printStackTrace();
		}
		
		req.getRequestDispatcher(path).forward(req, resp);
	}
	
}
