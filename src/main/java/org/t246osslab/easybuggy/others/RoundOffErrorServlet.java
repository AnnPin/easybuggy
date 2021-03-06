package org.t246osslab.easybuggy.others;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Locale;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pmw.tinylog.Logger;
import org.t246osslab.easybuggy.utils.Closer;
import org.t246osslab.easybuggy.utils.HTTPResponseCreator;
import org.t246osslab.easybuggy.utils.MessageUtils;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/roe" })
public class RoundOffErrorServlet extends HttpServlet {

    protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {
        PrintWriter writer = null;
        int number = -1;
        double result = 0;
        String errorMessage = "";
        try {
            Locale locale = req.getLocale();
            String strNumber = req.getParameter("number");
            if (strNumber != null) {
                try {
                    number = Integer.parseInt(strNumber);
                } catch (NumberFormatException e) {
                    // ignore
                }
                if (1 <= number && number <= 9) {
                    result = number - 0.9;
                } else {
                    errorMessage = "<font color=\"red\">" + MessageUtils.getMsg("msg.enter.positive.number", locale)
                            + "</font>";
                }
            }

            StringBuilder bodyHtml = new StringBuilder();
            bodyHtml.append("<form action=\"roe\" method=\"post\">");
            if (result != 0) {
                bodyHtml.append("<input type=\"text\" name=\"number\" size=\"1\" maxlength=\"1\" value=" + strNumber + ">");
            } else {
                bodyHtml.append("<input type=\"text\" name=\"number\" size=\"1\" maxlength=\"1\">");
            }
            bodyHtml.append(" - 0.9 = ");
            if (result != 0) {
                bodyHtml.append(String.valueOf(result));
            }
            bodyHtml.append("<br>");
            bodyHtml.append("<br>");
            bodyHtml.append("<input type=\"submit\" value=\"" + MessageUtils.getMsg("label.calculate", locale) + "\">");
            bodyHtml.append("<br>");
            bodyHtml.append(errorMessage);
            bodyHtml.append("<br>");
            bodyHtml.append(MessageUtils.getMsg("msg.note.enter.one", locale));
            bodyHtml.append("</form>");
            HTTPResponseCreator.createSimpleResponse(res,
                    MessageUtils.getMsg("title.round.off.error.page", locale), bodyHtml.toString());

        } catch (Exception e) {
            Logger.error(e);
        } finally {
            Closer.close(writer);
        }
    }
}
