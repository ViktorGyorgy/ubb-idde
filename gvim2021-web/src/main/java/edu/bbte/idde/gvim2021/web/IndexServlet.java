package edu.bbte.idde.gvim2021.web;

import com.github.jknack.handlebars.Template;
import edu.bbte.idde.gvim2021.apartmentad.backend.dao.ApartmentAdDao;
import edu.bbte.idde.gvim2021.apartmentad.backend.dao.memory.ApartmentAdMemoryDaoFactory;
import edu.bbte.idde.gvim2021.utilities.HandlebarsTemplateFactory;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@WebServlet("/index")
@Slf4j
public class IndexServlet extends HttpServlet {
    private static final ApartmentAdDao APARTMENT_AD_MEMORY_DAO =
            ApartmentAdMemoryDaoFactory.INSTANCE.getApartmentAdMemoryDao();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.info("GET index");

        req.setAttribute("apartmentAds", APARTMENT_AD_MEMORY_DAO.findAll());

        Template template = HandlebarsTemplateFactory.getTemplate("index");
        template.apply(APARTMENT_AD_MEMORY_DAO.findAll(), resp.getWriter());

    }
}
