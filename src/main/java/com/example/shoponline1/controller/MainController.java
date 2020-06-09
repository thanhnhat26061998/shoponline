/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.shoponline1.controller;

import com.example.shoponline1.dto.CartInfo;
import com.example.shoponline1.dto.CartLineInfo;
import com.example.shoponline1.dto.ProductCartDto;
import com.example.shoponline1.dto.ProductDto;
import com.example.shoponline1.entity.Product;
import com.example.shoponline1.entity.ProductDetail;
import com.example.shoponline1.entity.User;
import com.example.shoponline1.service.OrderServiceImpl;
import com.example.shoponline1.service.ProductDetailServiceImpl;
import com.example.shoponline1.service.ProductServiceImpl;
import com.example.shoponline1.utils.Utils;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

/**
 *
 * @author thanh
 */
@Controller
@Transactional
public class MainController {

    @Autowired
    private OrderServiceImpl orderDAO;

    @Autowired
    private ProductServiceImpl productDAO;

    @Autowired
    private ProductDetailServiceImpl detailServiceImpl;

    //@Autowired
    //private CustomerFormValidator customerFormValidator;
    @InitBinder
    public void myInitBinder(WebDataBinder dataBinder) {
        Object target = dataBinder.getTarget();
        if (target == null) {
            return;
        }
        System.out.println("Target=" + target);

        // Trường hợp update SL trên giỏ hàng.
        // (@ModelAttribute("cartForm") @Validated CartInfo cartForm)
        if (target.getClass() == CartInfo.class) {

        } // Trường hợp save thông tin khách hàng.
        // (@ModelAttribute @Validated CustomerInfo customerForm)
        //    else if (target.getClass() == CustomerForm.class) {
        //        dataBinder.setValidator(customerFormValidator);
        //    }

    }

    

    /*@RequestMapping("/")
    public String listProductHandler(Model model, //
            @RequestParam(value = "name", defaultValue = "") String likeName,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        final int maxResult = 5;
        final int maxNavigationPage = 10;

        PaginationResult<ProductInfo> result = productDAO.queryProducts(page, //
                maxResult, maxNavigationPage, likeName);

        model.addAttribute("paginationProducts", result);
        return "index";
    }**/
    @RequestMapping({"/buyProduct"})
    public String listProductHandler(HttpServletRequest request, Model model, //
            @RequestParam(value = "code", defaultValue = "") Integer code) {

        ProductDetail productDetail = null;
        if (code != null) {
            //product = productDAO.findById(code);
            productDetail = detailServiceImpl.findById(code);
        }
        if (productDetail != null) {

            // 
            CartInfo cartInfo = Utils.getCartInSession(request);

            ProductCartDto productInfo = new ProductCartDto(productDetail);

            cartInfo.addProduct(productInfo, 1);
        }

        return "redirect:/cart";
    }

    @RequestMapping({"/cartRemoveProduct"})
    public String removeProductHandler(HttpServletRequest request, Model model, //
            @RequestParam(value = "code", defaultValue = "") Integer code) {
        ProductDetail productDetail = null;
        if (code != null) {
            //product = productDAO.findById(code);
            productDetail = detailServiceImpl.findById(code);
        }
        if (productDetail != null) {

            CartInfo cartInfo = Utils.getCartInSession(request);

            ProductCartDto productInfo = new ProductCartDto(productDetail);

            cartInfo.removeProduct(productInfo);

        }

        return "redirect:/cart";
    }

    // POST: Giảm số lượng cho các sản phẩm đã mua.
    @RequestMapping(value = {"/decreaseProduct"}, method = RequestMethod.GET)
    public String shoppingCartDecreaseQty(HttpServletRequest request, //
            Model model, //
            @ModelAttribute("cartForm") CartInfo cartForm,
            @RequestParam(value = "code", defaultValue = "") Integer code) {

        Product product = null;
        if (code != null) {
            product = productDAO.findById(code);
        }
        if (product != null) {

            CartInfo cartInfo = Utils.getCartInSession(request);

            List<CartLineInfo> lines = cartInfo.getCartLines();
            for (CartLineInfo line : lines) {
                if ((line.getProductInfo().getProductId() == (code)) && (line.getQuantity() > 1)) {
                    int newQuantity = line.getQuantity() - 1;
                    if (line != null) {
                        line.setQuantity(newQuantity);
                    }
                }
            }
        }

        return "redirect:/cart";
    }

    // POST: Tăng số lượng cho các sản phẩm đã mua.
    @RequestMapping(value = {"/increaseProduct"}, method = RequestMethod.GET)
    public String shoppingCartIncreaseQty(HttpServletRequest request, //
            Model model, //
            @ModelAttribute("cartForm") CartInfo cartForm,
            @RequestParam(value = "code", defaultValue = "") Integer code) {

        Product product = null;
        if (code != null && code.intValue() > 0) {
            product = productDAO.findById(code);
        }
        if (product != null) {

            CartInfo cartInfo = Utils.getCartInSession(request);

            List<CartLineInfo> lines = cartInfo.getCartLines();
            for (CartLineInfo line : lines) {
                if ((line.getProductInfo().getProductId() == (code)) && (line.getQuantity() <= 10)) {
                    int newQuantity = line.getQuantity() + 1;
                    if (line != null) {
                        line.setQuantity(newQuantity);
                    }
                }
            }
        }

        return "redirect:/cart";
    }

    // GET: Hiển thị giỏ hàng.
    @RequestMapping(value = {"/cart"}, method = RequestMethod.GET)
    public String shoppingCart(HttpServletRequest request, Model model, HttpSession session) {

        CartInfo myCart = Utils.getCartInSession(request);
        model.addAttribute("cartForm", myCart);

        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
        }

        return "business/cart/cart";
    }

    // GET: Nhập thông tin khách hàng.
/*    @RequestMapping(value = {"/shoppingCartCustomer"}, method = RequestMethod.GET)
    public String shoppingCartCustomerForm(HttpServletRequest request, Model model,
            @ModelAttribute("cartForm") CartInfo cartForm,
            HttpSession session) {

        CartInfo cartInfo = Utils.getCartInSession(request);

        if (cartInfo.isEmpty()) {
            return "redirect:/cart";
        }

        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
        }
        
        //CustomerForm customerForm = new CustomerForm(customerInfo);
        //model.addAttribute("customerForm", user);
        model.addAttribute("cartForm", cartInfo);

        return "business/cart/customerInfo";
    }

        // POST: Save thông tin khách hàng.
    @RequestMapping(value = {"/shoppingCartCustomer"}, method = RequestMethod.POST)
    public String shoppingCartCustomerSave(HttpServletRequest request, //
            Model model, //
            @ModelAttribute("customerForm") @Validated CustomerForm customerForm, //
            BindingResult result, //
            final RedirectAttributes redirectAttributes) {

        if (result.hasErrors()) {
            customerForm.setValid(false);
            // Forward tới trang nhập lại.
            return "customerInfo";
        }

        customerForm.setValid(true);
        CartInfo cartInfo = Utils.getCartInSession(request);
        CustomerInfo customerInfo = new CustomerInfo(customerForm);
        cartInfo.setCustomerInfo(customerInfo);

        return "redirect:/shoppingCartConfirmation";
    }*/
    // GET: Xem lại thông tin để xác nhận.
    @RequestMapping(value = {"/shoppingCartConfirmation"}, method = RequestMethod.GET)
    public String shoppingCartConfirmationReview(HttpServletRequest request,
            Model model, HttpSession session) {
        CartInfo cartInfo = Utils.getCartInSession(request);

        if (cartInfo == null || cartInfo.isEmpty()) {

            return "redirect:/cart";
        }

        if (session.getAttribute("user") != null) {
            User user = (User) session.getAttribute("user");
            model.addAttribute("user", user);
        } else {
            return "business/user/login";
        }

        model.addAttribute("myCart", cartInfo);

        return "business/cart/infoConfirm";
    }

    // POST: Gửi đơn hàng (Save).
    @RequestMapping(value = {"/confirmed"}, method = RequestMethod.GET)

    public String shoppingCartConfirmationSave(HttpServletRequest request,
            Model model, HttpSession session) {
        CartInfo cartInfo = Utils.getCartInSession(request);

        if (cartInfo.isEmpty()) {

            return "redirect:/cart";
        } else if (session.getAttribute("user") == null) {
            return "business/user/login";
        }
        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        try {
            orderDAO.saveOrder(cartInfo, user);
        } catch (Exception e) {

            return "business/cart/infoConfirm";
        }

        // Xóa giỏ hàng khỏi session.
        Utils.removeCartInSession(request);

        // Lưu thông tin đơn hàng cuối đã xác nhận mua.
        Utils.storeLastOrderedCartInSession(request, cartInfo);

        return "redirect:/shoppingCartFinalize";
    }

    @RequestMapping(value = {"/shoppingCartFinalize"}, method = RequestMethod.GET)
    public String shoppingCartFinalize(HttpServletRequest request, Model model,
            HttpSession session) {

        CartInfo lastOrderedCart = Utils.getLastOrderedCartInSession(request);

        if (lastOrderedCart == null) {
            return "redirect:/cart";
        }

        User user = (User) session.getAttribute("user");
        model.addAttribute("user", user);
        
        model.addAttribute("lastOrderedCart", lastOrderedCart);
        return "business/cart/finish";
    }

    /*   @RequestMapping(value = {"/productImage"}, method = RequestMethod.GET)
    public void productImage(HttpServletRequest request, HttpServletResponse response, Model model,
            @RequestParam("code") Integer code) throws IOException {
        Product product = null;
        if (code != null) {
            product = this.productDAO.findProduct(code);
        }
        if (product != null && product.getImage() != null) {
            response.setContentType("image/jpeg, image/jpg, image/png, image/gif");
            response.getOutputStream().write(product.getImage());
        }
        response.getOutputStream().close();
    }*/
}
