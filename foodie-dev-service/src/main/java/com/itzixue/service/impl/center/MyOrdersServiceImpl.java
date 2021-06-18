package com.itzixue.service.impl.center;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.itzixue.mapper.OrdersMapperCustom;
import com.itzixue.pojo.vo.MyOrdersVO;
import com.itzixue.service.center.MyOrdersService;
import com.itzixue.utils.PagedGridResult;
import javafx.beans.binding.ObjectExpression;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class MyOrdersServiceImpl implements MyOrdersService {

    @Autowired
    private OrdersMapperCustom ordersMapperCustom;

    @Transactional(propagation = Propagation.SUPPORTS)
    @Override
    public PagedGridResult queryMyOrders(String userId, Integer orderStatus, int page, int size) {
        Map<String, Object> map = new HashMap<>();
        map.put("userId",userId);
        if(orderStatus!=null){
            map.put("orderStatus",orderStatus);
        }
        PageHelper.startPage(page,size);

        List<MyOrdersVO> myOrdersVOS = ordersMapperCustom.queryMyOrder(map);

        return setterPagedGrid(myOrdersVOS,page);
    }

    //设置分页信息
    private PagedGridResult setterPagedGrid(List<?> list,Integer page){
        PageInfo pageInfo = new PageInfo<>(list);
        PagedGridResult pagedGridResult = new PagedGridResult();
        pagedGridResult.setPage(page);
        pagedGridResult.setRecords(pageInfo.getTotal());//总记录数
        pagedGridResult.setRows(list);
        pagedGridResult.setTotal(pageInfo.getPages()); //总页数
        return pagedGridResult;
    }
}
