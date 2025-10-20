package io.tiklab.kaelthas.db.setting.controller;

import io.tiklab.core.Result;
import io.tiklab.kaelthas.db.database.model.DbInfo;
import io.tiklab.kaelthas.db.setting.service.DbSettingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

/**
 * 监控数据库的设置
 */
@RestController
@RequestMapping("/dbSetting")
public class DbSettingController {

    @Autowired
    private DbSettingService dbSettingService;

    /**
     * 根据id查询数据源
     */
    @RequestMapping(value = "/findDbInfoById",method = RequestMethod.POST)
    public Result<?> findDbInfoById(@NotNull String id){
        DbInfo dbInfo = dbSettingService.findDbInfoById(id);
        return Result.ok(dbInfo);
    }

    /**
     * 修改数据源
     */
    @RequestMapping(value = "/updateDbInfo",method = RequestMethod.POST)
    public void updateDbInfo(@RequestBody DbInfo dbInfo){
        dbSettingService.updateDbInfo(dbInfo);
    }


    /**
     * 删除数据源
     */
    @RequestMapping(value = "/deleteDbInfo",method = RequestMethod.POST)
    public Result<Void> deleteDbInfo(@NotNull String id){
        dbSettingService.deleteDbInfo(id);
        return Result.ok();
    }
}
