package cn.kungreat.bbs.controller;

import com.google.code.kaptcha.impl.DefaultKaptcha;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;

@Controller
public class KaptchaImage {

    @Autowired
    private DefaultKaptcha defaultKaptcha;

    @RequestMapping(value = "/image")
    public ModelAndView image(HttpServletRequest request, HttpServletResponse response){
        String code = defaultKaptcha.createText();
        request.getSession().setAttribute("image_code",code);
        request.getSession().setAttribute("time", new Date().getTime());
        BufferedImage image = defaultKaptcha.createImage(code);
        try {
            response.setContentType("image/jpeg");
            ImageIO.write(image,"JPEG",response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}