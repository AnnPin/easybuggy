package org.t246osslab.easybuggy.troubles;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.pmw.tinylog.Logger;
import org.t246osslab.easybuggy.utils.HTTPResponseCreator;
import org.t246osslab.easybuggy.utils.MessageUtils;

@SuppressWarnings("serial")
@WebServlet(urlPatterns = { "/filedescriptorleak" })
public class FileDescriptorLeakServlet extends HttpServlet {

    protected void doGet(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

        File file = new File(System.getProperty("java.io.tmpdir"), "test.txt");
        try {
            FileOutputStream fos1 = new FileOutputStream(file);
        } catch (IOException e) {
            Logger.error(e);
        }
        StringBuilder bodyHtml = new StringBuilder();
        bodyHtml.append(MessageUtils.getMsg("msg.file.descriptor.leak.occur", req.getLocale()));

        HTTPResponseCreator.createSimpleResponse(res, null, bodyHtml.toString());
    }
}
