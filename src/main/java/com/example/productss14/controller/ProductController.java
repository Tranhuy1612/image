package com.example.productss14.controller;

import com.example.productss14.model.Product;
import com.example.productss14.service.ProductService;

import java.io.File;
import java.io.IOException;
import java.util.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

@WebServlet(name = "ProductController", value = "/ProductController")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024 * 1,
        maxFileSize = 1024 * 1024 * 5,
        maxRequestSize = 1024 * 1024 * 10
)
public class ProductController extends HttpServlet {
    protected ProductService productService;

    public void init() throws ServletException {
        productService = new ProductService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if (action == null) {
            displayProducts(productService.findAll(), request, response);
        } else {
            switch (action) {
                case "CREATE":
                    request.getRequestDispatcher("/WEB-INF/newProduct.jsp").forward(request, response);
                case "DELETE":
                    Long idDel = Long.valueOf(request.getParameter("id"));
                    productService.delete(idDel);
                    break;
                case "EDIT":
                    Long editProductId = Long.valueOf(request.getParameter("id"));
                    Product editProduct = productService.findById(editProductId);
                    request.setAttribute("editProduct", editProduct);
                    request.getRequestDispatcher("/WEB-INF/editProduct.jsp").forward(request, response);
                    break;

                case "SEARCH":
                    String searchName = request.getParameter("search-name");
                    String sort = request.getParameter("sort");
                    String by = request.getParameter("by");
                    // lọc dữ liệu cần tim kiếm
                    List<Product> listSearch = searchAndSort(searchName, sort, by);
                    request.setAttribute("search-name", searchName);
                    request.setAttribute("sort", sort);
                    request.setAttribute("by", by);
                    displayProducts(listSearch, request, response);
                    break;
                case "DETAIL":
                    Long id = Long.valueOf(request.getParameter("id"));
                    Product p = productService.findById(id);
                    request.setAttribute("product", p);
                    request.getRequestDispatcher("/WEB-INF/productDetail.jsp").forward(request, response);
            }
//            displayProducts(productService.findAll(), request, response);
            response.sendRedirect("/");
        }
    }

    protected void displayProducts(List<Product> all, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        request.setAttribute("products", all);
        request.getRequestDispatcher("/WEB-INF/listProduct.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");
        if (action != null) {
            switch (action) {
                case "ADD":
                    String name = request.getParameter("name");
                    String des = request.getParameter("des");
                    Double price = Double.valueOf(request.getParameter("price"));
                    int stock = Integer.parseInt(request.getParameter("stock"));
                    Collection<Part> listImageUrl = request.getParts();
                    String avatar = "";
                    List<String> listImageUrls = new ArrayList<>();
                    Date date = new Date();
                    String imagePath = "C:\\Users\\admin\\Desktop\\Product-ss14\\src\\main\\webapp\\image";
                    for (Part part : listImageUrl) {
                        // lấy avatar
                        if (part.getName().equals("image")) {
                            //ghi 1 ảnh vào thư mục chỉ định
                            part.write(imagePath + File.separator + date.getTime() + part.getSubmittedFileName());
                            avatar = part.getSubmittedFileName();
                        } else if (part.getName().equals("imageUrls")) {
                            // xử lí nhiều file
                            part.write(imagePath + File.separator + part.getSubmittedFileName());
                            listImageUrls.add(part.getSubmittedFileName());
                        }
                    }
                    Product newProduct = new Product(null, name, des, listImageUrls, price, stock, avatar);
                    productService.save(newProduct);
//                    displayProducts(productService.findAll(), request, response);
                    response.sendRedirect("/");
                    break;
                case "UPDATE":
                    Long updateId = Long.valueOf(request.getParameter("id"));
                    String newName = request.getParameter("name");
                    String newDes = request.getParameter("des");
                    Double newPrice = Double.valueOf(request.getParameter("price"));
                    int newStock = Integer.parseInt(request.getParameter("stock"));

                    // Update the product
                    Product updatedProduct = productService.findById(updateId);
                    updatedProduct.setName(newName);
                    updatedProduct.setDescriptions(newDes);
                    updatedProduct.setPrice(newPrice);
                    updatedProduct.setStock(newStock);

                    // Save the updated product
                    productService.save(updatedProduct);
                    response.sendRedirect("/");
                    break;
            }
        }
    }

    protected List<Product> searchAndSort(String name, String sort, String by) {
        // lọc dữ liệu
        List<Product> listSearch = new ArrayList<>();
        for (Product p : productService.findAll()) {
            if (p.getName().toLowerCase().contains(name.toLowerCase())) {
                listSearch.add(p);
            }
        }
        // sắp xếp
        switch (sort) {
            case "name":
                if (by.equalsIgnoreCase("ASC")) {
                    listSearch.sort(Comparator.comparing(Product::getName));
                } else {
                    listSearch.sort(Comparator.comparing(Product::getName).reversed());
                }
                break;
            case "price":
                if (by.equalsIgnoreCase("ASC")) {
                    listSearch.sort(Comparator.comparing(Product::getName));
                } else {
                    listSearch.sort(Comparator.comparing(Product::getPrice).reversed());
                }
                break;
            case "stock":
                if (by.equalsIgnoreCase("ASC")) {
                    listSearch.sort(Comparator.comparing(Product::getStock));
                } else {
                    listSearch.sort(Comparator.comparing(Product::getStock).reversed());
                }
                break;
        }
        return listSearch;
    }

    @Override
    public void destroy() {
        super.destroy();
    }
}