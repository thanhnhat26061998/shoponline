/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.example.shoponline1.service;

import com.example.shoponline1.dao.IOrderDao;
import com.example.shoponline1.dao.IOrderDetailDao;
import com.example.shoponline1.dao.IProductDao;
import com.example.shoponline1.dao.IProductDetailDao;
import com.example.shoponline1.dto.CartInfo;
import com.example.shoponline1.dto.CartLineInfo;
import com.example.shoponline1.dto.OrderDetailInfo;
import com.example.shoponline1.dto.OrderInfo;
import com.example.shoponline1.entity.Order;
import com.example.shoponline1.entity.OrderDetail;
import com.example.shoponline1.entity.Product;
import com.example.shoponline1.entity.ProductDetail;
import com.example.shoponline1.entity.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author thanh
 */
@Service
public class OrderServiceImpl implements IOrderService {

    @Autowired
    private IOrderDao iOrderDao;

    @Autowired
    private IProductDao iProductDao;
    
    @Autowired
    private IProductDetailDao productDetailDao;

    @Autowired
    private IOrderDetailDao iOrderDetailDao;
    
    @Autowired
    private EntityManager entityManager;
    
    @Autowired
	private IOrderDao orderDao;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveOrder(CartInfo cartInfo, User user) {
        //Session session = 
    	
        //order.getUsers();
    	double total=0; 
        List<CartLineInfo> lines = cartInfo.getCartLines();
        Order order = new Order();
        
        for (CartLineInfo line : lines) {
        	OrderDetail detail = new OrderDetail();
            detail.setPrice(line.getAmount());
            detail.setQuantity(line.getQuantity());
            int code = line.getProductInfo().getProductId();
            ProductDetail prdDt = productDetailDao.findById(code).get();
            Product product = iProductDao.findById(prdDt.getProduct().getId()).get();
            detail.setColor(prdDt.getColor().getName());
            detail.setConfig(prdDt.getConfigurator().getRom());
            detail.setDiscountvalue(prdDt.getProduct().getPromotion().getDiscountvalue());
            detail.setProduct(product);
            detail.setOrder(order);
            total += line.getAmount();
            iOrderDetailDao.save(detail);
   // set order         
            order.setDeliveryTime(new Date());
            order.setUsers(user);
            order.setDeliveryAdress(user.getAddress());
            order.setStatus("confirmed");
            order.setTotals(total);
            iOrderDao.save(order);
            cartInfo.setOrderNum(order.getOrderId());
            
            
        }
        
        
        
    }

    @Override
    public List<OrderInfo> getOrderInfo(int userId) {
        List<Order> orderList = iOrderDao.findAll();
        List<OrderInfo> or = new ArrayList<OrderInfo>();
        OrderInfo orderInfo = new OrderInfo();
        for (Order order : orderList) {
        	
            if (order.getUsers().getId() == userId) {
                orderInfo.setId(order.getOrderId());
                orderInfo.setOrderDate(order.getDeliveryTime());
                or.add(orderInfo);
            }
        }
        return or;
    }
/*
    @Override
    public List<OrderInfo> getAllOrderInfo(int userId) {
        
        TypedQuery<OrderInfo> query = entityManager.createQuery(
                "select o.orderId, o.deliveryTime, p.name, od.price from OrderDetail od
                        join Order o
                                join Product p
                                        where o.users.id= ?1",
        )
        
        public void whenMultipleEntitiesAreListedWithJoin_ThenCreatesMultipleJoins() {
    TypedQuery<Phone> query
      = entityManager.createQuery(
      "SELECT ph FROM Employee e
      JOIN e.department d
      JOIN e.phones ph
      WHERE d.name IS NOT NULL", Phone.class);
    List<Phone> resultList = query.getResultList();
     
    // Assertions...
}@Query("select o.orderId, o.deliveryTime, p.name, od.price from OrderDetail od"
            + " join Order o "
            + " on od.order.orderId = o.orderId "
            + " join Product p "
            + " on od.id = p.id "
            + " where o.users.id= ?1")
    List<OrderDetailInfo> findUser(int userid);
        
    /*    List<Order> orderList = iOrderDao.findAll();
        List<OrderDetail> orderDetails = iOrderDetailDao.findAll();
        
        OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
        
        List<OrderInfo> orderInfo = new ArrayList<>();
        List<OrderDetailInfo> orderDetailInfos = new ArrayList<>();
        for (Order order : orderList) {
            if (order.getUsers().getId() == userId) {
                OrderInfo orderInfo1 = new OrderInfo();
                
                orderInfo1.setId(order.getOrderId());
                orderInfo1.setOrderDate(order.getDeliveryTime());
                for (OrderDetail orderDetail : orderDetails) {
                    if(orderDetail.getOrder().getOrderId() == orderInfo1.getId()){
                        orderDetailInfo.setProductName(orderDetail.getProduct().getName());
                        orderDetailInfo.setPrice(orderDetail.getPrice());
                        orderDetailInfos.add(orderDetailInfo);
                    }
                }
                orderInfo1.setOrderDetailInfos(orderDetailInfos);
                
                orderInfo.add(orderInfo1);
            }
        }
        return orderInfo;
    }*/

    @Override
    public List<OrderDetailInfo> orderDetailInfos(int orderId) {
        List<OrderDetail> orderDetails = iOrderDetailDao.findAll();
        OrderDetailInfo orderDetailInfo = new OrderDetailInfo();
        List<OrderDetailInfo> orderDetailInfos = new ArrayList<>();
        for (OrderDetail orderDetail1 : orderDetails) {
            if (orderDetail1.getOrder().getOrderId() == orderId) {

                orderDetailInfo.setPrice(orderDetail1.getPrice());
                orderDetailInfo.setProductName(orderDetail1.getProduct().getName());
                orderDetailInfos.add(orderDetailInfo);
            }
        }

        return orderDetailInfos;
    }
    
    public List<Order> searchPrd( String status, Date begin, Date end){
		List<Order> or = orderDao.findAll();
		List<Order> order = new ArrayList<Order>();
		for (Order o : or) {
			if (o.getStatus().equals(status)
					&& end.after(o.getDeliveryTime())&& begin.before(o.getDeliveryTime())) {
					order.add(o);
			}
		}
		return order;
	}
	
	
	public Page<Order> findPaginated(Pageable pageable) {
		int pageSize = pageable.getPageSize();
        int currentPage = pageable.getPageNumber();
        int startItem = currentPage * pageSize;
        List<Order> or = orderDao.findAll();

        List<Order> list;
        if (or.size()<startItem) {
			list =Collections.emptyList();
		}else {
			int toIndex = Math.min(startItem+pageSize, or.size());
			list = or.subList(startItem, toIndex);
		}      
        Page<Order> productPage = new PageImpl<Order>(list, PageRequest.of(currentPage, pageSize), or.size());
		return productPage;
	}

}
