package com.oracle.cart.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.oracle.cart.service.CartService;
import com.oracle.manager.mapper.CartMapper;
import com.oracle.manager.pojo.Cart;

@Service
public class CartServiceImpl implements CartService{

	@Autowired
	private CartMapper cartMapper;
	
	@Override
	public Boolean addProductToCart(Cart cart) {
		Cart cart2 = cartMapper.selectOne(cart);
		if (cart2!=null) {
			cart.setNum(cart2.getNum()+1);
			cart.setUpdated(new Date());
			return false;
		}
		cart.setCreated(new Date());
		cart.setUpdated(cart.getCreated());
		cartMapper.insertSelective(cart);
		return true;
	}

	@Override
	public List<Cart> queryAllCartByUserId(Long userId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		List<Cart> list = cartMapper.select(cart);
		return list;
	}

	@Override
	public Boolean updateNumOnCart(Long userId, Long productId, Integer num) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setProductId(productId);
		Cart cart2 = cartMapper.selectOne(cart);
		if (cart2!=null) {
			cart.setId(cart2.getId());
			cart.setUpdated(new Date());
			cart.setNum(num);
			cartMapper.updateByPrimaryKeySelective(cart);
			return true;
		}
		
		return false;
	}

	@Override
	public Boolean deleteProductByUserIdAndProductId(Long userId, Long productId) {
		Cart cart = new Cart();
		cart.setUserId(userId);
		cart.setProductId(productId);
		int i = cartMapper.delete(cart);
		return i==0?false:true;
	}

}
