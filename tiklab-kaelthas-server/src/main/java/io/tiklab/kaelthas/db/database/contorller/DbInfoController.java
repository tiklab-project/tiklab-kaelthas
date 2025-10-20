package io.tiklab.kaelthas.db.database.contorller;

import io.tiklab.core.Result;
import io.tiklab.core.page.Pagination;
import io.tiklab.kaelthas.db.database.model.DbInfo;
import io.tiklab.kaelthas.db.database.service.DbInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.List;

@RestController
@RequestMapping("/dbInfo")
public class DbInfoController {

    @Autowired
    private DbInfoService dbInfoService;

    /**
     * 分页查询
     */
    @RequestMapping(value = "/findDbInfoPage",method = RequestMethod.POST)
    public Result<?> findDbInfoPage(@RequestBody DbInfo dbInfo){
        Pagination<DbInfo> pagination = dbInfoService.findDbInfoPage(dbInfo);
        return Result.ok(pagination);
    }

    /**
     * 查询出最后修改的四个数据库
     */
    @RequestMapping(value = "/findDropDown",method = RequestMethod.POST)
    public Result<List<DbInfo>> findDropDown(){
        List<DbInfo> dbInfoList = dbInfoService.findDropDown();
        return Result.ok(dbInfoList);
    }

    /**
     * 根据id查询数据源
     */
    @RequestMapping(value = "/findDbInfoById",method = RequestMethod.POST)
    public Result<?> findDbInfoById(@NotNull String id){
        DbInfo dbInfo = dbInfoService.findDbInfoById(id);
        return Result.ok(dbInfo);
    }

    /**
     * 添加数据源
     */
    @RequestMapping(value = "/createDbInfo",method = RequestMethod.POST)
    public Result<?> createDbInfo(@RequestBody DbInfo dbInfo){
        String dbId = dbInfoService.createDbInfo(dbInfo);
        return Result.ok(dbId);
    }

    /**
     * 修改数据源
     */
    @RequestMapping(value = "/updateDbInfo",method = RequestMethod.POST)
    public Result<Void> updateDbInfo(@RequestBody DbInfo dbInfo){
        dbInfoService.updateDbInfo(dbInfo);
        return Result.ok();
    }


    /**
     * 删除数据源
     */
    @RequestMapping(value = "/deleteDbInfo",method = RequestMethod.POST)
    public Result<?> deleteDbInfo(@NotNull String id){
        dbInfoService.deleteDbInfo(id);
        return Result.ok();
    }

    /**
     * 创建数据库的时候测试是否可用
     */
    @RequestMapping(value = "/testSql",method = RequestMethod.POST)
    public Result<?> testSql(@RequestBody DbInfo dbInfo){
        return dbInfoService.testSql(dbInfo);
    }
}
