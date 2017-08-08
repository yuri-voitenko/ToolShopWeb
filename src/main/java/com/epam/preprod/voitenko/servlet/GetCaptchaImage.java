package com.epam.preprod.voitenko.servlet;

import com.epam.preprod.voitenko.captcha.Captcha;
import com.epam.preprod.voitenko.repository.CaptchaRepository;
import com.epam.preprod.voitenko.strategy.CaptchaStrategy;

import javax.imageio.ImageIO;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.RenderedImage;
import java.io.IOException;
import java.io.OutputStream;

@WebServlet("/getCaptchaImage")
public class GetCaptchaImage extends HttpServlet {
    @Override
    protected void service(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws ServletException, IOException {
        CaptchaStrategy strategy = (CaptchaStrategy) getServletContext().getAttribute("strategy");
        int idCaptcha = strategy.getIdCaptcha(httpServletRequest);
        if (idCaptcha != -1) {
            Captcha captcha = CaptchaRepository.getCaptcha(idCaptcha);
            captcha.updateImage();
            OutputStream osImage = httpServletResponse.getOutputStream();
            ImageIO.write((RenderedImage) captcha.getImage(), "jpeg", osImage);
            osImage.flush();
            osImage.close();
        }
    }
}