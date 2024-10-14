var conversionData = {

    expressionFiled: [],

    //将上报的数据进行塞值,匹配定义的表达式
    setReportData: function (expression, value) {
        this.expressionFiled.push({
            name: expression,
            value: value
        })
        return this.expressionFiled;
    },

    judgement: function (triggerExpression) {
        this.expressionFiled.map(function (item) {
            if (triggerExpression.indexOf(item.name) !== -1) {
                for (var i = 0; i < 10; i++) {
                    triggerExpression = triggerExpression.replace(item.name, item.value);
                }
            }
        })
        return eval(triggerExpression);
    },

    setNullFiled:function (){
        this.expressionFiled = [];
    }

};

