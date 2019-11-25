package ru.rosbank.javaschool.web.servlet;

import ru.rosbank.javaschool.util.SQLLib;
import ru.rosbank.javaschool.web.constant.Constants;
import ru.rosbank.javaschool.web.model.BurgerModel;
import ru.rosbank.javaschool.web.model.OrderPositionModel;
import ru.rosbank.javaschool.web.model.ProductModel;
import ru.rosbank.javaschool.web.repository.*;
import ru.rosbank.javaschool.web.service.BurgerAdminService;
import ru.rosbank.javaschool.web.service.BurgerUserService;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;
import java.io.IOException;

public class FrontServlet extends HttpServlet {
    private BurgerUserService burgerUserService;
    private BurgerAdminService burgerAdminService;


    @Override
    public void init() throws ServletException {
        log(Constants.LOG_MESSAGE_INIT);
        try {
            InitialContext initialContext = new InitialContext();
            DataSource dataSource = (DataSource) initialContext.lookup("java:/comp/env/jdbc/db");
            SQLLib sqlTemplate = new SQLLib();
            ProductRepository productRepository = new ProductRepositoryJdbcImpl(dataSource, sqlTemplate);
            OrderRepository orderRepository = new OrderRepositoryJdbcImpl(dataSource, sqlTemplate);
            OrderPositionRepository orderPositionRepository = new OrderPositionRepositoryJdbcImpl(dataSource, sqlTemplate);
            burgerUserService = new BurgerUserService(productRepository, orderRepository, orderPositionRepository);
            burgerAdminService = new BurgerAdminService(productRepository);

            insertInitialData(productRepository);
        } catch (NamingException e) {
            e.printStackTrace();
        }
    }

    private void insertInitialData(ProductRepository productRepository) {
        productRepository.save(new ProductModel("Burger 3", 100, 1, Constants.NO_PHOTO, Constants.DESCRIPTION_BY_DEFAULT, "burgers"));
        productRepository.save(new ProductModel("Burger 4", 200, 2, Constants.NO_PHOTO, Constants.DESCRIPTION_BY_DEFAULT, "burgers"));
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getMethod().equals("GET")) {
            doGet(req, resp);
        }
        if (req.getMethod().equals("POST")) {
            doPost(req, resp);
        }
    }


    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI().substring(req.getContextPath().length());
        if (url.startsWith("/admin/edit")) {
            int id = Integer.parseInt(req.getParameter("id"));
            req.setAttribute(Constants.ITEM, burgerAdminService.getById(id));
            req.setAttribute(Constants.ITEMS, burgerAdminService.getAll());
            req.getRequestDispatcher("/WEB-INF/admin/frontpage.jsp").forward(req, resp);
            return;
        }
        if (url.startsWith("/admin")) {
            if (url.equals("/admin")) {
                req.setAttribute(Constants.ITEMS, burgerAdminService.getAll());
                req.getRequestDispatcher("/WEB-INF/admin/frontpage.jsp").forward(req, resp);
                return;
            }
            return;
        }
        if (url.startsWith("/more")) {
            int id = Integer.parseInt(req.getParameter(Constants.PRODUCTS_COLUMN_ID));
            ProductModel model = burgerUserService.getById(id);
            req.setAttribute(Constants.PRODUCTS_COLUMN_NAME, model.getName());
            req.setAttribute(Constants.PRODUCTS_COLUMN_PRICE, model.getPrice());
            req.setAttribute(Constants.PRODUCTS_COLUMN_QUANTITY, model.getQuantity());
            req.setAttribute(Constants.PRODUCTS_COLUMN_IMAGE, model.getImageUrl());
            req.setAttribute(Constants.PRODUCTS_COLUMN_DESCRIPTION, model.getDescription());
            req.setAttribute(Constants.PRODUCTS_COLUMN_CATEGORY, model.getCategory());
            req.getRequestDispatcher("/WEB-INF/more.jsp").forward(req, resp);
            return;
        }
        if (url.startsWith("/update")) {
            int id = Integer.parseInt(req.getParameter(Constants.ORDERPOSITIONS_COLUMN_ID));
            OrderPositionModel model = burgerUserService.getPositionById(id);
            req.setAttribute(Constants.ORDERPOSITIONS_COLUMN_ID, model.getId());
            req.setAttribute(Constants.ORDERPOSITIONS_COLUMN_ORDERID, model.getOrderId());
            req.setAttribute(Constants.ORDERPOSITIONS_COLUMN_PRODUCTID, model.getProductId());
            req.setAttribute(Constants.ORDERPOSITIONS_COLUMN_PRODUCTNAME, model.getProductName());
            req.setAttribute(Constants.ORDERPOSITIONS_COLUMN_PRICE, model.getProductPrice());
            req.setAttribute(Constants.ORDERPOSITIONS_COLUMN_QUANTITY, model.getProductQuantity());
            req.getRequestDispatcher("/WEB-INF/update.jsp").forward(req, resp);
            return;
        }

        if (url.startsWith("/remove")) {
            if (url.equals("/remove")) {
                int id = Integer.parseInt(req.getParameter(Constants.ORDERPOSITIONS_COLUMN_ID));
                burgerUserService.removePositionById(id);
                resp.sendRedirect("/");
                return;
            }
            return;
        }

        if (url.equals("/")) {
            HttpSession session = req.getSession();
            if (session.isNew()) {
                int orderId = burgerUserService.createOrder();
                session.setAttribute(Constants.BURGERSERVICE_ATTRIBUTE_ORDERID, orderId);
            }
            int orderId = (Integer) session.getAttribute(Constants.BURGERSERVICE_ATTRIBUTE_ORDERID);
            req.setAttribute(Constants.BURGERSERVICE_ATTRIBUTE_ORDEREDITEMS, burgerUserService.getAllOrderPosition(orderId));
            req.setAttribute(Constants.ITEMS, burgerUserService.getAll());
            req.getRequestDispatcher("/WEB-INF/frontpage.jsp").forward(req, resp);
            return;
        }
    }


    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String url = req.getRequestURI().substring(req.getContextPath().length());
        if (url.startsWith("/admin")) {
            if (url.equals("/admin")) {
                int id = Integer.parseInt(req.getParameter(Constants.PRODUCTS_COLUMN_ID));
                String name = req.getParameter(Constants.PRODUCTS_COLUMN_NAME);
                int price = Integer.parseInt(req.getParameter(Constants.PRODUCTS_COLUMN_PRICE));
                int quantity = Integer.parseInt(req.getParameter(Constants.PRODUCTS_COLUMN_QUANTITY));
                String image = req.getParameter(Constants.PRODUCTS_COLUMN_IMAGE);
                String description = req.getParameter(Constants.PRODUCTS_COLUMN_DESCRIPTION);
                String category = req.getParameter(Constants.PRODUCTS_COLUMN_CATEGORY);
                // TODO: validation
                burgerAdminService.save(new ProductModel(id, name, price, quantity, image, description, category));
                if (category == "burgers") {
                    String cutlet_meat = req.getParameter("cutlet_meat");
                    int cutlet_count = Integer.parseInt(req.getParameter("cutlet_count"));
                    burgerAdminService.saveBurger(id, cutlet_meat, cutlet_count);
                }
                if (category == "potatoes") {
                    int weight = Integer.parseInt(req.getParameter("weight_in_g"));
                    burgerAdminService.savePotato(id, weight);
                }
                if (category == "drinks") {
                    int volume = Integer.parseInt(req.getParameter("volume_in_ml"));
                    burgerAdminService.saveDrink(id, volume);
                }
                if (category == "desserts") {
                    String syrup = req.getParameter("syrup");
                    burgerAdminService.saveDessert(id, syrup);
                }
                resp.sendRedirect(url);
                return;
            }
            return;
        }
        if (url.startsWith("/update")) {
            if (url.equals("/update")) {
                int id = Integer.parseInt(req.getParameter(Constants.ORDERPOSITIONS_COLUMN_ID));
                int order_id = Integer.parseInt(req.getParameter(Constants.ORDERPOSITIONS_COLUMN_ORDERID));
                int product_id = Integer.parseInt(req.getParameter(Constants.ORDERPOSITIONS_COLUMN_PRODUCTID));
                String product_name = req.getParameter(Constants.ORDERPOSITIONS_COLUMN_PRODUCTNAME);
                int price = Integer.parseInt(req.getParameter(Constants.ORDERPOSITIONS_COLUMN_PRICE));
                int quantity = Integer.parseInt(req.getParameter(Constants.ORDERPOSITIONS_COLUMN_QUANTITY));
                burgerUserService.updatePosition(new OrderPositionModel(id, order_id, product_id, product_name, price, quantity));
                resp.sendRedirect("/");
                return;
            }
            return;
        }
        if (url.equals("/")) {
            HttpSession session = req.getSession();
            if (session.isNew()) {
                int orderId = burgerUserService.createOrder();
                session.setAttribute(Constants.BURGERSERVICE_ATTRIBUTE_ORDERID, orderId);
            }
            int orderId = (Integer) session.getAttribute(Constants.BURGERSERVICE_ATTRIBUTE_ORDERID);
            int id = Integer.parseInt(req.getParameter(Constants.BURGERSERVICE_ATTRIBUTE_ORDERPOSITIONID));
            int quantity = Integer.parseInt(req.getParameter(Constants.BURGERSERVICE_ATTRIBUTE_QUANTITY));
            burgerUserService.order(orderId, id, quantity);
            resp.sendRedirect(url);
            return;
        }
    }

    @Override
    public void destroy() {
        log(Constants.LOG_MESSAGE_DESTROY);
    }
}