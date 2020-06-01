package gq.niweera.wordhunterapi.controller;

import gq.niweera.wordhunterapi.model.DefaultResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

@Controller
public class BasicErrorController implements ErrorController {
    private final static String ERROR_PATH = "/error";

    public BasicErrorController() {

    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    @RequestMapping(value = ERROR_PATH)
    @ResponseBody
    public DefaultResponse returnErrorResponse(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            int statusCode = Integer.parseInt(status.toString());

            if (statusCode == HttpStatus.NOT_FOUND.value()) {
                return new DefaultResponse("error-404");
            } else if (statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
                return new DefaultResponse("error-500");
            }
        }
        return new DefaultResponse("error-500");
    }
}
