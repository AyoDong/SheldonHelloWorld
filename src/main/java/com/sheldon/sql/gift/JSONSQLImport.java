package com.sheldon.sql.gift;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;

import java.io.File;
import java.util.List;

/**
 * @author fangxiaodong
 * @date 2021/01/08
 */
public class JSONSQLImport {

    /**
     * 1. 补充 customer_gift 的 gift_rule_id (直接写一个 SQL 解决)
     * 2. 补充 customer_gift_detail 中的数据
     */
    private static final String CUSTOMER_GIFT = "src/main/resources/重庆世茂茂悦府三期（重庆照母山三期）31.json";
    private static final String GIFT_DETAIL = "src/main/resources/重庆世茂茂悦府三期（重庆照母山三期) 礼品.json";

    private static final String BONUS = "src/main/resources/t_bonus.json";
    private static final String BONUS_RULE = "src/main/resources/t_bonus_rule.json";
    private static final String GIFT = "src/main/resources/t_gift.json";
    private static final String GIFT_RULE = "src/main/resources/t_gift_rule.json";

    public static void main(String[] args) throws Exception {

//        JSONObject customerGiftJSONObject = getJSONObject(CUSTOMER_GIFT);
//        List<String> customerGiftIds = getFieldValues(customerGiftJSONObject, "customer_gift_id");
//        JSONObject giftJSONObject = getJSONObject(GIFT_DETAIL);
//        printInsertCustomerGiftSQL(giftJSONObject, customerGiftIds, "t_bonus");

        printInsertCustomerGiftSQL(BONUS, "t_bonus");
        printInsertCustomerGiftSQL(BONUS_RULE, "t_bonus_rule");
        printInsertCustomerGiftSQL(GIFT, "t_gift");
        printInsertCustomerGiftSQL(GIFT_RULE, "t_gift_rule");
    }

    private static void printInsertCustomerGiftSQL(String fileName, String tableName) throws Exception {
        JSONObject giftJSONObject = getJSONObject(fileName);
        JSONArray dataList = giftJSONObject.getJSONArray("data");
        List<String> fields = getFields(dataList, Lists.newArrayList());

        for (Object dataObj : dataList) {
            JSONObject data = (JSONObject)dataObj;
            StringBuffer sql = new StringBuffer();
            sql.append("INSERT INTO `jyy_ams_activity`.`" + tableName + "`");
            sql.append(" (");
            sql.append(String.join(",", fields));
            sql.append(" )");
            sql.append(" VALUES");
            sql.append(" (");

            for (int i = 0; i < fields.size(); i++) {
                if(i != 0){
                    sql.append(",");
                }
                sql.append("'" + data.getString(fields.get(i)) + "'");
            }
            sql.append(");");
            System.out.println(sql.toString());
        }
    }

    private static List<String> getFields(JSONArray dataList, List<String> exceptFields){
        List<String> fields = Lists.newArrayList();
        JSONObject data = (JSONObject)dataList.get(0);
        for (String key : data.keySet()) {
            if(!exceptFields.contains(key)){
                fields.add(key);
            }
        }
        return fields;
    }

    private static JSONObject getJSONObject(String url) throws Exception{
        File file = new File(url);
        String string = FileUtils.readFileToString(file);
        JSONObject json = (JSONObject)JSONObject.parse(string);
        return json;
    }

    private static List<String> getFieldValues(JSONObject jsonObject, String field){
        List<String> customerGiftIds = Lists.newArrayList();
        JSONArray dataList = jsonObject.getJSONArray("data");
        for (Object dataObj : dataList) {
            JSONObject data = (JSONObject)dataObj;
            customerGiftIds.add(data.getString(field));
        }

        return customerGiftIds;
    }
}
