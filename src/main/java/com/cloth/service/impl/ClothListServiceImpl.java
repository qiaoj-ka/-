package com.cloth.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.cloth.exception.ClothExecution;
import com.cloth.exception.ClothOperationException;
import com.cloth.service.IClothListService;
import com.cloth.entity.ClothList;
import com.cloth.mapper.ClothListMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author jobob
 * @since 2021-01-29
 */
@Service
public class ClothListServiceImpl extends ServiceImpl<ClothListMapper, ClothList> implements IClothListService {

    @Resource
    private ClothListMapper clothListMapper;

    @Override
    public List<ClothList> clothList() {
        QueryWrapper<ClothList> queryWrapper=new QueryWrapper<>();
        Page<ClothList> page=new Page<>(1,5);
        IPage<ClothList> clothListIPage=clothListMapper.selectPage(page,queryWrapper);
        return clothListIPage.getRecords();
    }

    @Override
    public List<ClothList> findClothListByCondition(String status, String detail, Integer userlistId) {
        QueryWrapper<ClothList> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("status",status).or()
                .eq("cloth_type",detail).or()
                .eq("userlist_id",userlistId);
        if(status==null&&detail==null&&userlistId==null){
            return clothListMapper.selectList(new QueryWrapper<ClothList>());
        }
        return clothListMapper.selectList(queryWrapper);
    }

    @Override
    public ClothExecution addCloth(ClothList clothList) {
        if(clothList == null){
            return new ClothExecution(false,"添加服装信息为空");
        }
        try{
            int effectedNum = clothListMapper.insert(clothList);
            if(effectedNum < 1){
                return new ClothExecution(false,"添加服装信息失败");
            }
        }catch (Exception e){
            throw new ClothOperationException(e.toString());
        }
        return new ClothExecution(true);
    }

    @Override
    public ClothExecution updateCloth(ClothList clothList) {
        if(clothList == null){
            return new ClothExecution(false,"更新服装信息为空");
        }

        try{
            int effectedNum = clothListMapper.updateById(clothList);
            if(effectedNum < 1){
                return new ClothExecution(false,"更新服装信息失败");
            }
        }catch (Exception e){
            throw new ClothOperationException(e.toString());
        }
        return new ClothExecution(true);
    }

    @Override
    public ClothExecution deleteCloth(Integer clothId) {
        if(clothId == null){
            return new ClothExecution(false,"删除服装信息Id为空");
        }
        try{
            int effectedNum = clothListMapper.deleteById(clothId);
            if(effectedNum < 1){
                return new ClothExecution(false,"删除服装信息失败");
            }
        }catch (Exception e){
            throw new ClothOperationException(e.toString());
        }
        return new ClothExecution(true);
    }
}
