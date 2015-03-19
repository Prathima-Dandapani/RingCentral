package com.prathima.ringcentral.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.prathima.ringcentral.util.PhoneCall;
import com.prathima.ringcentral.util.RingOut;
import com.prathima.ringcentral.util.RingStatusObject;
import com.prathima.ringcentral.util.Token;

/**
 * Servlet implementation class RingController
 */
@WebServlet("/RingController")
public class RingController extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private List<String> errMsg  ;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public RingController() {
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
		boolean isFormValid = true;
		errMsg = new ArrayList<String>();
		String action = request.getParameter("action");
		if("Get Duration".equalsIgnoreCase(action)){
			try {
				Double duration = PhoneCall.getDuration();
				request.setAttribute("duration",duration);
				request.setAttribute("ringstatus",null);
				getServletConfig().getServletContext().getRequestDispatcher("/RingSuccess.jsp").forward(request, response);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				errMsg.add(e.getMessage());
				request.setAttribute("errorMsg",errMsg);
				getServletConfig().getServletContext().getRequestDispatcher("/RingFailure.jsp").forward(request, response);
			}
			
		}
		
		else
		{
					
					
					for (Object key : request.getParameterMap().keySet()) {
						String fieldName = (String) key;
						String fieldVal = ((String[]) request.getParameterMap().get(fieldName))[0];			
						if(isFormValid){
							isFormValid = validate(fieldName, fieldVal);
						}
						else
						{
							validate(fieldName, fieldVal);
						}	
							
					}
					
					if (isFormValid) {
						try{
							
							// Generate Access Token
							String  accessToken= Token.getInstance().getAccess_token();
							String Caller = request.getParameter("caller");
							String Callee = request.getParameter("callee");
							//System.out.println(Caller);
							//System.out.println(Callee);
							RingStatusObject statusObject = RingOut.MakeCall(accessToken,Caller,Callee );
							request.setAttribute("ringstatus",statusObject);
							request.setAttribute("duration",null);
							getServletConfig().getServletContext().getRequestDispatcher("/RingSuccess.jsp").forward(request, response);
							
						}
						catch(Exception e){
							e.printStackTrace();
							errMsg.add(e.getMessage());
							request.setAttribute("errorMsg",errMsg);
							getServletConfig().getServletContext().getRequestDispatcher("/RingFailure.jsp").forward(request, response);
						}
					}
					else{
						
						request.setAttribute("errorMsg",errMsg);
						getServletConfig().getServletContext().getRequestDispatcher("/RingFailure.jsp").forward(request, response);
					}
		}
	}
	
	public boolean validate(String fieldName, String fieldValue) {

		if (fieldValue == null || fieldValue.isEmpty()) {
			errMsg.add(fieldName + " cannot be empty");
			return false;
		}
		return true;
	}

}
