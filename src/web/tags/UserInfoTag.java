package web.tags;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;

public class UserInfoTag extends TagSupport {
    private UserInfo user;
    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = this.pageContext.getOut();
            if(user == null) {
                out.println("No UserInfo Found...");
                return SKIP_BODY;
            }
            out.println("<table width='500px' border='1' align='center'>");
            out.println("<tr>");
            out.println("<td width='20%'>Username:</td>");
            out.println("<td>" + user.getUserName() + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Age:</td>");
            out.println("<td>" + user.getAge() + "</td>");
            out.println("</tr>");
            out.println("<tr>");
            out.println("<td>Email:</td>");
            out.println("<td>" + user.getEmail() + "</td>");
            out.println("</tr>");
            out.println("</table>");
        } catch(Exception e) {
            throw new JspException(e.getMessage());
        }
        return SKIP_BODY;
    }
   
    @Override
    public int doEndTag() throws JspException {
        return EVAL_PAGE;
    }
 
    @Override
    public void release() {
        super.release();
        this.user = null;
    }
   
    //getter and setters
    public UserInfo getUser() {
        return user;
    }
    public void setUser(UserInfo user) {
        this.user = user;
    }
}
