package login_logout;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import tool.Action;

public class LogoutAction extends Action {

	public String execute(
		HttpServletRequest request, HttpServletResponse response
	)throws Exception{

		HttpSession session=request.getSession();

		if (session.getAttribute("teacher")!=null){
			session.removeAttribute("teacher");
			return "logout-out.jsp";
		}
		return "logout-error.jsp";
	}

}
