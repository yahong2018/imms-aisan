package com.zhxh.imms.web;

import com.zhxh.imms.data.CrudLogic;
import com.zhxh.imms.data.DbQuery;
import lombok.Getter;
import lombok.Setter;

import java.util.Map;

@Getter
@Setter
public class FilterExpression {
    private String L;
    private String O;
    private Object R;
    private String J;

    public FilterExpression() {
    }

    public FilterExpression(String l, String o, Object r) {
        this(l, o, r, null);
    }

    public FilterExpression(String l, String o, Object r, String j) {
        this.L = l;
        this.O = o;
        this.R = r;
        this.J = j;
    }

    public static void fillWhere(Class clazz, DbQuery query, FilterExpression... expressions) {
        if (expressions == null || expressions.length == 0) {
            return;
        }

        StringBuilder builder = new StringBuilder();
        Map<String, String> fieldsMap = CrudLogic.getFieldsMap(clazz);
        for (FilterExpression expr : expressions) {
            String property = expr.getL();
            String field = fieldsMap.get(property);

            if (expr.getJ() != null) {
                builder.append(" ").append(expr.getJ()).append(" ");
            }
            String parameter = property;
            if (query.containsKey(parameter)) {
                parameter = parameter + "_1";
            }

            builder.append(field).append(" ").append(expr.getO()).append(" #{").append(parameter).append("}");
            query.put(parameter, expr.getR());
        }
        query.setWhere(builder.toString());
    }
}
