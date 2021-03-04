package com.cloth.controller;


import com.cloth.common.Result;
import com.cloth.common.StatusCode;
import com.cloth.entity.ClothList;
import com.cloth.exception.ClothExecution;
import com.cloth.service.IClothListService;
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
@RequestMapping("cloth")
public class ClothListController {

    @Autowired
    private IClothListService clothListService;
    @RequestMapping(value = "/getallclothlist",method = RequestMethod.GET)
    public Result getAllClothList(){
        return new Result(true, StatusCode.SUCCESS,"查找服装列表成功",clothListService.clothList());
    }

    @RequestMapping(value = "/getclothlistbycondition",method = RequestMethod.POST)
    public Result getListByCondition(@RequestBody ClothList clothList){
        return new Result(true, StatusCode.SUCCESS,"按条件查找服装信息列表成功", clothListService.findClothListByCondition(clothList.getStatus(), clothList.getClothType(), clothList.getUserlistId()));
    }

    @RequestMapping(value="/addcloth",method = RequestMethod.POST)
    public Result addCloth(@RequestBody ClothList clothList){
        ClothExecution he;
        try{
            he = clothListService.addCloth(clothList);
            if(he.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"添加服装信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"添加服装信息失败：" + he.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"添加服装信息失败：" + e.toString());
        }
    }

    @RequestMapping(value="/updatecloth",method = RequestMethod.POST)
    public Result updateHouse(@RequestBody ClothList clothList){
        ClothExecution he;
        try{
            he = clothListService.updateCloth(clothList);
            if(he.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"修改服装信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"修改服装信息失败：" + he.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"修改服装信息失败：" + e.toString());
        }
    }

    @RequestMapping(value="/deletecloth",method = RequestMethod.DELETE)
    public Result deleteHouse(@RequestParam("clothId")Integer clothId){
        ClothExecution he;
        try{
            he = clothListService.deleteCloth(clothId);
            if(he.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"删除服装信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"删除服装信息失败：" + he.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"删除服装信息失败：" + e.toString());
        }
    }


}
