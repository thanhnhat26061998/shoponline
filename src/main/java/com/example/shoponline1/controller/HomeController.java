package com.example.shoponline1.controller;

import com.example.shoponline1.dao.ITrademarkDao;
import com.example.shoponline1.dto.CartInfo;
import com.example.shoponline1.dto.ProductDto;
import com.example.shoponline1.entity.Trademark;
import com.example.shoponline1.entity.User;
import com.example.shoponline1.service.IProductDetailService;
import com.example.shoponline1.utils.Utils;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.servlet.http.HttpServletRequest;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class HomeController {

    @Autowired
    private IProductDetailService productDetailService;
    
    @Autowired ITrademarkDao trademarkDao;

    @GetMapping("/home")
    public String listProducts(Model model, @RequestParam("page") Optional<Integer> page, HttpServletRequest request,
            @RequestParam("size") Optional<Integer> size, HttpSession session) {
        final int currentPage = page.orElse(1);
        final int pageSize = size.orElse(8);
        Page<ProductDto> productPage = productDetailService.findPaginated(PageRequest.of(currentPage - 1, pageSize));
        model.addAttribute("productPage", productPage);
        int totalPage = productPage.getTotalPages();
        if (totalPage > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPage)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }
        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
        }
        List<Trademark> td = trademarkDao.findAll();
        model.addAttribute("td", td);
        
        CartInfo myCart = Utils.getCartInSession(request);
        model.addAttribute("cartForm", myCart);
        
        return "business/home/shop";
    }

    @GetMapping("/search")
    public String search(@RequestParam("name") String name, Model model, HttpSession session, HttpServletRequest request) {
        List<ProductDto> product = productDetailService.findAllPrd();
        List<ProductDto> productPage = new ArrayList<ProductDto>();
        for (ProductDto productDto : product) {
            if (productDto.getProductName().equals(name)) {
                productPage.add(productDto);
                model.addAttribute("productPage", productPage);
            }

        }

        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
        }
        CartInfo myCart = Utils.getCartInSession(request);
        model.addAttribute("cartForm", myCart);
        List<Trademark> td = trademarkDao.findAll();
        model.addAttribute("td", td);
        
        return "business/home/shop";
    }

    @GetMapping("/filterByBrand")
    public String filterByBrand(@RequestParam("name") String name, Model model, HttpSession session, HttpServletRequest request) {
        List<ProductDto> product = productDetailService.findAllPrd();
        List<ProductDto> productPage = new ArrayList<ProductDto>();
        for (ProductDto productDto : product) {
            if (productDto.getBrandName().equals(name)) {
                productPage.add(productDto);
                model.addAttribute("productPage", productPage);
            }

        }

        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
        }
        CartInfo myCart = Utils.getCartInSession(request);
        model.addAttribute("cartForm", myCart);
        
        List<Trademark> td = trademarkDao.findAll();
        model.addAttribute("td", td);
        return "business/home/shop";
    }

    @GetMapping("/filterByPrice")
    public String filterByPrice(@RequestParam("price") double price, Model model, HttpSession session, HttpServletRequest request) {
        List<ProductDto> product = productDetailService.findAllPrd();
        List<ProductDto> productPage = new ArrayList<ProductDto>();
        for (ProductDto productDto : product) {
            if (productDto.getPriceAfter() <= price) {
                productPage.add(productDto);
                model.addAttribute("productPage", productPage);
            }

        }

        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
        }
        CartInfo myCart = Utils.getCartInSession(request);
        model.addAttribute("cartForm", myCart);
        
        List<Trademark> td = trademarkDao.findAll();
        model.addAttribute("td", td);

        return "business/home/shop";
    }

}
