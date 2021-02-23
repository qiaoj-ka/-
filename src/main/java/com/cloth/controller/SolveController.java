package com.cloth.controller;


import com.cloth.common.Result;
import com.cloth.common.StatusCode;
import com.cloth.entity.Solve;
import com.cloth.exception.SolveExecution;
import com.cloth.service.ISolveService;
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
@RequestMapping("solve")
public class SolveController {
    @Autowired
    private ISolveService solveService;

    @RequestMapping(value = "/getallsolvelist",method = RequestMethod.GET)
    public Result getAllSolveList(){
        return new Result(true, StatusCode.SUCCESS,"查找故障信息列表成功",solveService.findSolveListByCondition(null,null,null,null));
    }

    @RequestMapping(value = "/getsolvelistbycondition",method = RequestMethod.POST)
    public Result getSolveListByCondition(@RequestBody Solve solve){
        return new Result(true, StatusCode.SUCCESS,"按条件查找故障信息列表成功",solveService.findSolveListByCondition(solve.getStatus(),solve.getName(),solve.getClothType(),solve.getUserlistId()));
    }

    @RequestMapping(value="/addsolve",method = RequestMethod.POST)
    public Result addSolve(@RequestBody Solve solve){
        SolveExecution se;
        try{
            se = solveService.addSolve(solve);
            if(se.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"添加故障信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"添加故障信息失败：" + se.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"添加故障信息失败：" + e.toString());
        }
    }

    @RequestMapping(value="/updatesolve",method = RequestMethod.POST)
    public Result updateSolve(@RequestBody Solve solve){
        SolveExecution se;
        try{
            se = solveService.updateSolve(solve);
            if(se.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"修改故障信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"修改故障信息失败：" + se.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"修改故障信息失败：" + e.toString());
        }
    }

    @RequestMapping(value="/deletesolve",method = RequestMethod.DELETE)
    public Result deleteSolve(@RequestParam("solveId")Integer solveId){
        SolveExecution se;
        try{
            se = solveService.deleteSolve(solveId);
            if(se.isFlag()){
                return new Result(true,StatusCode.SUCCESS,"删除故障信息成功");
            }else {
                return new Result(false,StatusCode.ERROR,"删除故障信息失败：" + se.getReason());
            }
        }catch (Exception e){
            return new Result(false,StatusCode.ERROR,"删除故障信息失败：" + e.toString());
        }
    }
}
