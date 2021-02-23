package com.cloth.controller;


import com.cloth.common.Result;
import com.cloth.common.StatusCode;
import com.cloth.entity.Paid;
import com.cloth.exception.PaidExecution;
import com.cloth.pojo.PaidVO;
import com.cloth.service.IPaidService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author jobob
 * @since 2021-01-29
 */
@RestController
@CrossOrigin
@RequestMapping("paid")
public class PaidController {
    @Autowired
    private IPaidService paidService;

    @RequestMapping(value = "/getallpaidlist",method = RequestMethod.GET)
    public Result getAllPaidList(){
        return new Result(true, StatusCode.SUCCESS,"查找租金信息列表成功",paidService.getAllPaidList());
    }

    @RequestMapping(value = "/getpaidlistbycondition",method = RequestMethod.POST)
    public Result getPaidListByCondition(@RequestBody Paid paid){
        return new Result(true, StatusCode.SUCCESS,"按条件查找租金信息列表成功",paidService.findPaidListByCondition(paid.getStatus(),paid.getName(),paid.getClothType(),paid.getUserlistId()));
    }

    @RequestMapping(value="/addpaid",method = RequestMethod.POST)
    public Result addPaid(@RequestBody PaidVO paidvo){
        PaidExecution pe;
        try{
            pe = paidService.addPaid(paidvo);
            if(pe.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"添加租金信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"添加租金信息失败：" + pe.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"添加租金信息失败：" + e.toString());
        }
    }

    @RequestMapping(value="/updatepaid",method = RequestMethod.POST)
    public Result updatePaid(@RequestBody Paid paid){
        PaidExecution pe;
        try{
            pe = paidService.updatePaid(paid);
            if(pe.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"修改租金信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"修改租金信息失败：" + pe.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"修改租金信息失败：" + e.toString());
        }
    }

    @RequestMapping(value="/deletepaid",method = RequestMethod.DELETE)
    public Result deletePaid(@RequestParam("paidId")Integer paidId){
        PaidExecution pe;
        try{
            pe = paidService.deletePaid(paidId);
            if(pe.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"删除租金信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"删除租金信息失败：" + pe.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"删除租金信息失败：" + e.toString());
        }
    }
}
