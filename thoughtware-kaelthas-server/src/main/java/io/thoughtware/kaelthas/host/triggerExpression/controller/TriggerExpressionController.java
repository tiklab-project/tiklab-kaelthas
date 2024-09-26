package io.thoughtware.kaelthas.host.triggerExpression.controller;

import io.thoughtware.core.Result;
import io.thoughtware.kaelthas.host.triggerExpression.model.TriggerExpression;
import io.thoughtware.kaelthas.host.triggerExpression.service.TriggerExpressionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/triggerExpression")
public class TriggerExpressionController {

    @Autowired
    private TriggerExpressionService triggerExpressionService;

    @RequestMapping(value = "/findTriggerExpressionAll",method = RequestMethod.POST)
    public Result<List<TriggerExpression>> findTriggerExpressionAll(){
        List<TriggerExpression> expressionList = triggerExpressionService.findTriggerExpressionAll();
        return Result.ok(expressionList);
    }
}
