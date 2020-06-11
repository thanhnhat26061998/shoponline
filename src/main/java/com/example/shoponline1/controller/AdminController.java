package com.example.shoponline1.controller;

import com.example.shoponline1.dao.IColorDao;
import com.example.shoponline1.dao.IConfigDao;
import com.example.shoponline1.dao.IImageDao;
import com.example.shoponline1.dao.IOrderDao;
import com.example.shoponline1.dao.IOrderDetailDao;
import com.example.shoponline1.dao.IProductDao;
import com.example.shoponline1.dao.IProductDetailDao;
import com.example.shoponline1.dao.IPromotionDao;
import com.example.shoponline1.dao.ITrademarkDao;
import com.example.shoponline1.dto.ProductAdminDto;
import com.example.shoponline1.dto.ProductDetailAdminDto;
import com.example.shoponline1.entity.Color;
import com.example.shoponline1.entity.Configurator;
import com.example.shoponline1.entity.Images;
import com.example.shoponline1.entity.Order;
import com.example.shoponline1.entity.OrderDetail;
import com.example.shoponline1.entity.Product;
import com.example.shoponline1.entity.ProductDetail;
import com.example.shoponline1.entity.Promotion;
import com.example.shoponline1.entity.Trademark;
import com.example.shoponline1.entity.User;
import com.example.shoponline1.service.IProductService;
import com.example.shoponline1.service.ImageService;
import com.example.shoponline1.service.OrderServiceImpl;
import com.example.shoponline1.validation.productAdminValidation;
import com.example.shoponline1.validation.productAdminValidations;
import com.example.shoponline1.validation.promotionAdminValidation;
import com.example.shoponline1.validation.saveProductAdminValidation;
import com.example.shoponline1.validation.tradeMarkAdminValidation;

import java.awt.Image;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

@Controller
public class AdminController {

	@Autowired
	private IProductService productService;

	@Autowired
	private OrderServiceImpl orderService;

	@Autowired
	private IProductDao productDao;

	@Autowired
	private IConfigDao configDao;

	@Autowired
	private IColorDao colorDao;

	@Autowired
	private productAdminValidation prdValidation;

	@Autowired
	private productAdminValidations prdValidations;

	@Autowired
	private saveProductAdminValidation savePrdValidation;
	
	@Autowired
	private tradeMarkAdminValidation tradeMarValidation;
	
	@Autowired
	private promotionAdminValidation promotionValidation;

	@Autowired
	private IProductDetailDao productDetaiDao;

	@Autowired
	private IImageDao imageDao;

	@Autowired
	private ImageService imageService;

	@Autowired
	private ITrademarkDao trademarDao;

	@Autowired
	private IPromotionDao promotionDao;

	@Autowired
	private IOrderDao orderDao;
	
	@Autowired
	private IOrderDetailDao orderDetailDao;

	// admin home

	

	// list product
	@RequestMapping("/admin")
	public String product(HttpSession session, Model model, HttpServletRequest request) {

		int page = 0;
		int size = 10;
		if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
			page = Integer.parseInt(request.getParameter("page")) - 1;
		}
		if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
			size = Integer.parseInt(request.getParameter("size"));
		}
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		Page<Product> prd = productDao.findAll(PageRequest.of(page, size));
		model.addAttribute("product", prd);
		return "system/products/product/product";
	}
	
	@RequestMapping("/admin/product")
	public String producttt() {
		return "redirect:/admin";
	}
	// delete product

	@GetMapping("/admin/delete")
	public String deleteProduct(@RequestParam("id") int id) {
		List<OrderDetail> ordt = orderDetailDao.findAll();
		for (OrderDetail orderDetail : ordt) {
			if (orderDetail.getProduct().getId()!=id) {
				Product prd = productService.findById(id);
				List<ProductDetail> prdDt = prd.getProductDetail();
				for (ProductDetail productDetail : prdDt) {
					productDetaiDao.deleteById(productDetail.getId());
				}
				productDao.deleteById(id);
				return "redirect:/admin";
			}
		}
		
		return "redirect:/admin";
	}

	// add product

	@RequestMapping("/admin/addproduct")
	public String addProduct(HttpSession session, Model model) {

		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		List<Trademark> td = trademarDao.findAll();
		model.addAttribute("td", td);
		List<Promotion> pr = promotionDao.findAll();
		model.addAttribute("pr", pr);
		ProductAdminDto prdto = new ProductAdminDto();
		model.addAttribute("prdto", prdto);

		return "system/products/product/addPrd";
	}

	// edit product

	@RequestMapping("/admin/editproduct")
	public String edtProduct(HttpSession session, Model model, @RequestParam("id") int id) {

		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		List<Trademark> td = trademarDao.findAll();
		model.addAttribute("td", td);
		List<Promotion> pr = promotionDao.findAll();
		model.addAttribute("pr", pr);

		Product prd = productDao.findById(id).get();
		ProductAdminDto prdto = new ProductAdminDto();
		prdto.setId(id);
		prdto.setName(prd.getName());
		prdto.setNote(prd.getNotes());
		model.addAttribute("prdto", prdto);
		return "system/products/product/addPrd";
	}

	// save product

	@PostMapping("admin/saveprd")
	public String saveProduct(@ModelAttribute("prdto") ProductAdminDto prdDto, Model model, BindingResult bindingResult,
			HttpSession session) {
		savePrdValidation.validate(prdDto, bindingResult);
		if (bindingResult.hasErrors()) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			List<Trademark> td = trademarDao.findAll();
			model.addAttribute("td", td);
			List<Promotion> pr = promotionDao.findAll();
			model.addAttribute("pr", pr);
			return "system/products/product/addPrd";
		}

		Product prd = new Product();
		prd.setName(prdDto.getName());
		prd.setNotes(prdDto.getNote());
		prd.setPromotion(promotionDao.findById(prdDto.getPromotionId()).get());
		prd.setTrademark(trademarDao.findById(prdDto.getTrademarkId()).get());
		if (prdDto.getId() != 0) {
			prd.setId(prdDto.getId());
		}
		productDao.save(prd);

		return "redirect:/admin";
	}

	// product detail -------------------------//

	// list product detail
	@RequestMapping("/admin/productdetail")
	public String productDetail(HttpSession session, Model model, @RequestParam("id") int id) {

		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		Product prd = productService.findById(id);
		model.addAttribute("prd", prd);
		List<ProductDetail> prdDt = prd.getProductDetail();
		model.addAttribute("prdDt", prdDt);
		return "system/products/productDetail/productDetail";
	}

	// add product detail

	@RequestMapping("/admin/addproductDt")
	public String addProductDetail(HttpSession session, Model model, @RequestParam("id") int id) {

		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		List<Configurator> config = configDao.findAll();
		model.addAttribute("config", config);
		List<Color> color = colorDao.findAll();
		model.addAttribute("color", color);
		ProductDetailAdminDto prdDT = new ProductDetailAdminDto();
		prdDT.setProductId(id);
		model.addAttribute("prdDT", prdDT);
		return "system/products/productDetail/addPrd";
	}

	// edit product detail
	@RequestMapping("/admin/editdt")
	public String editProductDetail(HttpSession session, Model model, @RequestParam("id") int id) {

		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);

		List<Configurator> config = configDao.findAll();
		model.addAttribute("config", config);
		List<Color> color = colorDao.findAll();
		model.addAttribute("color", color);

		ProductDetail prdDetail = productDetaiDao.findById(id).get();

		ProductDetailAdminDto prdDT = new ProductDetailAdminDto();
		prdDT.setAmount(String.valueOf(prdDetail.getAmount()));
		prdDT.setPrice(String.valueOf(prdDetail.getPrice()));
		prdDT.setId(prdDetail.getId());
		prdDT.setProductId(prdDetail.getProduct().getId());
		prdDT.setColorId(prdDetail.getColor().getId());
		prdDT.setConfigId(prdDetail.getConfigurator().getId());
		model.addAttribute("prdDT", prdDT);
		return "system/products/productDetail/editPrd";
	}

	// save prodduct detail

	@PostMapping("admin/save")
	public String savePrd(@ModelAttribute("prdDT") ProductDetailAdminDto prdDto, Model model,
			BindingResult bindingResult, HttpSession session, @RequestParam("file") MultipartFile file,
			@RequestParam("file2") MultipartFile file2) {
		prdValidation.validate(prdDto, bindingResult);
		if (bindingResult.hasErrors()) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			List<Configurator> config = configDao.findAll();
			model.addAttribute("config", config);
			List<Color> color = colorDao.findAll();
			model.addAttribute("color", color);
			return "system/products/productDetail/addPrd";
		}
		Images img = new Images();

		img.setImage1(imageService.uploadImage(file));
		img.setImage2(imageService.uploadImage(file2));
		imageDao.save(img);

		ProductDetail prdDt = new ProductDetail();
		prdDt.setAmount(Integer.parseInt(prdDto.getAmount()));
		prdDt.setPrice(Double.parseDouble(prdDto.getPrice()));
		prdDt.setColor(colorDao.findById(prdDto.getColorId()).get());
		prdDt.setConfigurator(configDao.findById(prdDto.getConfigId()).get());
		prdDt.setProduct(productService.findById(prdDto.getProductId()));
		prdDt.setImages(img);
		if (prdDto.getId() != 0) {
			prdDt.setId(prdDto.getId());
		}
		prdDt = productDetaiDao.save(prdDt);
		return "redirect:/admin/productdetail?id=" + prdDto.getProductId();
	}
	// save

	@PostMapping("admin/saves")
	public String savePrds(@ModelAttribute("prdDT") ProductDetailAdminDto prdDto, Model model,
			BindingResult bindingResult, HttpSession session, @RequestParam("file") MultipartFile file,
			@RequestParam("file2") MultipartFile file2) {
		prdValidations.validate(prdDto, bindingResult);
		if (bindingResult.hasErrors()) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
			List<Configurator> config = configDao.findAll();
			model.addAttribute("config", config);
			List<Color> color = colorDao.findAll();
			model.addAttribute("color", color);
			return "system/products/productDetail/addPrd";
		}
		Images img = new Images();

		img.setImage1(imageService.uploadImage(file));
		img.setImage2(imageService.uploadImage(file2));
		imageDao.save(img);

		ProductDetail prdDt = new ProductDetail();
		prdDt.setAmount(Integer.parseInt(prdDto.getAmount()));
		prdDt.setPrice(Double.parseDouble(prdDto.getPrice()));
		prdDt.setColor(colorDao.findById(prdDto.getColorId()).get());
		prdDt.setConfigurator(configDao.findById(prdDto.getConfigId()).get());
		prdDt.setProduct(productService.findById(prdDto.getProductId()));
		prdDt.setImages(img);
		if (prdDto.getId() != 0) {
			prdDt.setId(prdDto.getId());
		}
		prdDt = productDetaiDao.save(prdDt);
		return "redirect:/admin/productdetail?id=" + prdDto.getProductId();
	}

	// delete detail product

	@GetMapping("/admin/deletedt")
	public String deleteDetailProduct(@RequestParam("id") int id) {
		ProductDetail prds = productDetaiDao.findById(id).get();
		int ids = prds.getProduct().getId();
		List<OrderDetail> ordt = orderDetailDao.findAll();
		for (OrderDetail orderDetail : ordt) {
			for (ProductDetail prdt2 : orderDetail.getProduct().getProductDetail()) {
				if (prdt2.getId()!=id) {
					
					productDetaiDao.deleteById(id);

					return "redirect:/admin/productdetail?id=" + ids;
				}
			}
			
		}



		return "redirect:/admin/productdetail?id=" + ids;

	}
	
	// add promotion

	@RequestMapping("/admin/product/addpromotion")
	public String addPromotion(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		Promotion promotion = new Promotion();
		model.addAttribute("promotion", promotion);

		return "system/products/product/addPromotion";
	}

	@RequestMapping("/admin/savepromotion")
	public String savePromotion(@ModelAttribute("promotion") Promotion promotion, Model model, BindingResult bindingResult) {
		promotionValidation.validate(promotion, bindingResult);
		if (bindingResult.hasErrors()) {
			return "system/products/product/addPromotion";
		}
		promotionDao.save(promotion);
		return "redirect:/admin/product";
	}
	
	// add trademerk

	@RequestMapping("/admin/product/addtrademark")
	public String addTrade(HttpSession session, Model model) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		Trademark trademark = new Trademark();
		model.addAttribute("trademark", trademark);

		return "system/products/product/addTrademark";
	}

	@RequestMapping("/admin/savetrademark")
	public String saveTrade(@ModelAttribute("trademark") Trademark trademark, Model model, BindingResult bindingResult) {
		
		tradeMarValidation.validate(trademark, bindingResult);
		if (bindingResult.hasErrors()) {
			return "system/products/product/addTrademark";
		}
		trademarDao.save(trademark);
		return "redirect:/admin/product";
	}

	// ---------------------order---------------------//

	@RequestMapping("/admin/orders")
	public String order(HttpSession session, Model model, HttpServletRequest request) {

		User user = (User) session.getAttribute("user");

		model.addAttribute("user", user);

		return "system/order/list_order";
	}

	@RequestMapping("/admin/order")
	public String orders(Model model, HttpSession session, HttpServletRequest request) {
		int page = 0;
		int size = 7;
		if (request.getParameter("page") != null && !request.getParameter("page").isEmpty()) {
			page = Integer.parseInt(request.getParameter("page")) - 1;
		}
		if (request.getParameter("size") != null && !request.getParameter("size").isEmpty()) {
			size = Integer.parseInt(request.getParameter("size"));
		}
		if (session.getAttribute("user") != null) {
			User user = (User) session.getAttribute("user");
			model.addAttribute("user", user);
		}
		User user = (User) session.getAttribute("user");
		Page<Order> order = orderDao.findAll(PageRequest.of(page, size));
//		List<Order> order = orderDao.findAll();
		model.addAttribute("order", order);
		model.addAttribute("user", user);
		long millis=System.currentTimeMillis();  
		java.sql.Date date=new java.sql.Date(millis);  
		System.out.println(date);  
		model.addAttribute("date", date);

		return "system/order/list_order";
	}

	@RequestMapping("/admin/searchs")
	public String searchs(HttpSession session, Model model, @RequestParam("status") String status,
			@RequestParam("begin") @DateTimeFormat(pattern = "yyyy-MM-dd") Date begin,
			@RequestParam("end") @DateTimeFormat(pattern = "yyyy-MM-dd") Date end) {

		List<Order> order = orderService.searchPrd(status, begin, end);
		model.addAttribute("order", order);

		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);

		return "system/order/list_order";
	}

	@RequestMapping("/admin/deleteorder")
	public String deleteOrder(@RequestParam("id") int id) {
		Order or = orderDao.findById(id).get();

		if (or.getStatus().equals("delivered")) {
			List<OrderDetail> orDt = or.getOrderDetail();
			for (OrderDetail orderDetail : orDt) {
				orderDetailDao.deleteById(orderDetail.getId());
			}
			orderDao.deleteById(id);
		}

		return "redirect:/admin/order";
	}

	
	// update status
	@RequestMapping("/admin/statusorder")
	public String statusOrder(@RequestParam("id") int id) {
		Order or = orderDao.findById(id).get();
		if (or.getStatus().equals("confirmed")) {
			or.setStatus("shipping");
			orderDao.save(or);
			return "redirect:/admin/order";
		}
		if (or.getStatus().equals("shipping")) {
			or.setStatus("delivered");
			orderDao.save(or);
			return "redirect:/admin/order";
		}
		if (or.getStatus().equals("delivered")) {
			or.setStatus("confirmed");
			orderDao.save(or);
			return "redirect:/admin/order";
		}
		return "redirect:/admin/order";

	}
	// order detail
	
	@RequestMapping("/admin/orderdetail")
	public String orderDetail(@RequestParam("id") int id,Model model, HttpSession session) {
		User user = (User) session.getAttribute("user");
		model.addAttribute("user", user);
		Order or = orderDao.findById(id).get();
		List<OrderDetail> order = or.getOrderDetail();
		model.addAttribute("order", order);
		return "system/order/list_order_detail";
	}

}
