package cn.kungreat.bbs.controller;

import cn.kungreat.bbs.customimg.Captcha;
import org.apache.commons.lang3.RandomStringUtils;
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

/*    @Autowired
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
    }*/

    @RequestMapping(value = "/image")
    public ModelAndView image2(HttpServletRequest request, HttpServletResponse response){
        String randomStr = RandomStringUtils.randomAlphabetic(4).toLowerCase();
        request.getSession().setAttribute("image_code", randomStr);
        request.getSession().setAttribute("time", new Date().getTime());
        BufferedImage bi = new Captcha().generate(453, 68, randomStr).getImage();
        try {
            response.setContentType("image/jpeg");
            ImageIO.write(bi, "JPEG", response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }
}