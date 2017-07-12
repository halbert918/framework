package com.framework.process.support;

import com.framework.process.JobContext;
import com.framework.process.Node;
import com.framework.process.NodeWapper;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;

import java.util.Map;

/**
 * Created by Halbert on 2017/1/7.
 * node选择器，根据context中param传递的参数选择对应的node节点
 */
public class NodeExecutionDecider {

    private String id;
    /**
     * 配置分支选择路径
     */
    private String decide;
    /**
     * spel表达式
     */
    private String expression;

    /**
     * 根据参数选择分支
     * @param jobContext
     * @param prevNodeWapper
     * @return
     */
    public NodeWapper decide(JobContext jobContext, NodeWapper prevNodeWapper) {

        if(null != expression) {
            ExpressionParser parser = new SpelExpressionParser();
            //设置表达式
            Expression exp = parser.parseExpression(expression, new ParserContext() {
                @Override
                public boolean isTemplate() {
                    return true;
                }

                @Override
                public String getExpressionPrefix() {
                    return "#{";
                }

                @Override
                public String getExpressionSuffix() {
                    return "}";
                }
            });
            //EvaluationContext:解析context根节点下面的属性，eg:#{domain.name}
            StandardEvaluationContext ect = new StandardEvaluationContext(jobContext);
            decide = exp.getValue(ect, String.class);
        }

        for (NodeWapper nodeWapper : prevNodeWapper.getNodeWappers()) {
            Node node = nodeWapper.getNode();
            if(decide.equals(node.getName())) {
                return nodeWapper;
            }
        }

//        Map<Object, Object> params = jobContext.getParams();
//        if(params.containsKey(decide)) {
//            Object value = params.get(decide);
//            for (NodeWapper nodeWapper : prevNodeWapper.getNodeWappers()) {
//                Node node = nodeWapper.getNode();
//                if(value.equals(node.getName())) {
//                    return nodeWapper;
//                }
//            }
//        }
        return null;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDecide(String decide) {
        this.decide = decide;
    }

    public String getDecide() {
        return decide;
    }

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }
}
