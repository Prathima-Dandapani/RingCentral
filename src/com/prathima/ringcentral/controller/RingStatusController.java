package com.prathima.ringcentral.controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prathima.ringcentral.util.PhoneCall;
import com.prathima.ringcentral.util.RingStatusObject;

/**
 * Servlet implementation class RingStatusController
 */
@WebServlet("/RingStatusController")
public class RingStatusController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RingStatusController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	ArrayList<String>	errMsg = new ArrayList<String>();	
    try{		
	  RingStatusObject ringstatus =  (RingStatusObject)  getServletContext().getAttribute("ringstatus");
	  RingStatusObject newringstatus = (RingStatusObject)  PhoneCall.checkStatus(ringstatus);
	  request.setAttribute("ringstatus",newringstatus);
	  getServletConfig().getServletContext().getRequestDispatcher("/RingSuccess.jsp").forward(request, response);
    }
    catch(Exception e){
    	e.printStackTrace();
		errMsg.add(e.getMessage());
		request.setAttribute("errorMsg",errMsg);
		getServletConfig().getServletContext().getRequestDispatcher("/RingFailure.jsp").forward(request, response);
    }
	}

}
