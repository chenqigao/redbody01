package com.hh.sell.service.impl;

import com.hh.sell.mapper.OrderDetailRepository;
import com.hh.sell.mapper.OrderMasterRepository;
import com.hh.sell.enums.OrderStatuEnum;
import com.hh.sell.enums.PayStatusEnum;
import com.hh.sell.enums.ResultEnum;
import com.hh.sell.exceptions.SellException;
import com.hh.sell.pojo.*;
import com.hh.sell.service.OrderService;
import com.hh.sell.service.ProductService;
import com.hh.sell.util.KeyUtil;
import com.hh.sell.util.OrderMaster2OrderDTO;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService {
    @Autowired
    private OrderMasterRepository repository;

    @Autowired
    private ProductService productService;

    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderMasterRepository orderMasterRepository;

    @Transactional
    @Override
    public OrderDTO create(OrderDTO orderDTO) {

        //初始化商品总价为0
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

        //订单创建时生成订单编号orderId
        String orderId = KeyUtil.genUniqueKey();

        //1.查询商品
        List<OrderDetail> orderDetailList = orderDTO.getOrderDetailList();
        for(OrderDetail orderDetail:orderDetailList){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }

            //2.计算订单总价:每一件商品的价格的和即为这个订单的价格
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //3.订单详情入库（OrderDetail）
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);
        }
        //4.写入订单数据库（OrderMaster）
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        orderMaster.setOrderId(orderId);
        orderMaster.setOrderAmount(orderAmount);
        orderMaster.setOrderStatus(OrderStatuEnum.NEW.getCode());
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);

        //5.扣库存
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e -> new CartDTO(e.getProductId(), e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);

        return orderDTO;
    }

    @Override
    public OrderDTO findOnes(String orderId) {
        OrderMaster orderMaster = orderMasterRepository.getOne(orderId);
        if(orderMaster == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
        if(orderDetailList ==  null){
            throw new SellException(ResultEnum.ORDERDETAIL_NOR_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    /**
     * 根据买家微信openid查询买家订单列表
     * @param buyerOpenid
     * @param pageable
     * @return
     */
    @Override
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        //从数据库中查询出的是OrderMaster
        Page<OrderMaster> orderMasterPage
                = orderMasterRepository.findByBuyerOpenid(buyerOpenid, pageable);

        //返回的是OrderDTO,因此需要转换
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTO.convert(orderMasterPage.getContent());

        Page<OrderDTO> orderDTOPage
                = new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
        return orderDTOPage;
    }

    /**
     * 取消订单
     * @param orderDTO
     * @return
     */
    @Transactional
    @Override
    public OrderDTO cancel(OrderDTO orderDTO) {

        OrderMaster orderMaster = new OrderMaster();

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatuEnum.NEW.getCode())){
            //redbody  下面没有注释掉
            //log.error("【取消订单】订单信息不正确 orderId={},orderStatus={}", orderDTO.getOrderId(), orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatuEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            //redbody  下面没有注释掉
           // log.error("【取消订单】更新失败,orderMaster={}",orderMaster);
        }

        //返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            //redbody  下面没有注释掉
            //log.error("【取消订单】订单没有商品详情,orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.increaseStock(cartDTOList);

        //如果已经支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            //TODO
        }
        return orderDTO;
    }

    /**
     * 完结订单
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO finish(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatuEnum.NEW.getCode())){
            //redbody  下面没有注释掉
            //log.error("【完结订单】订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }

        //修改订单状态
        orderDTO.setOrderStatus(OrderStatuEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult==null){
            //redbody  下面没有注释掉
            //log.error("【完结订单】更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    /**
     * 完成支付
     * @param orderDTO
     * @return
     */
    @Override
    @Transactional
    public OrderDTO paid(OrderDTO orderDTO) {
        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatuEnum.NEW.getCode())){
            //redbody  下面没有注释掉
            //log.error("【订单支付完成】订单状态不正确,orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            //redbody  下面没有注释掉
            //log.error("【订单支付完成】订单支付状态不正确,orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_PAY_STATUS_ERROR);
        }
        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            //redbody  下面没有注释掉
            //log.error("【订单支付完成】更新失败,orderMaster={}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }
}
