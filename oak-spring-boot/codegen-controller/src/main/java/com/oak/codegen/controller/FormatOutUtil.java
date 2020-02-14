package com.oak.codegen.controller;


import com.alibaba.fastjson.JSONObject;

public class FormatOutUtil {
    /**
     * @param resString
     * @return String
     * @throws
     * @Description 响应数据格式化
     * @author zhuiyi
     * @date 20190628
     */
    private static String responseFormat(String resString){

        StringBuffer jsonForMatStr = new StringBuffer();
        int level = 0;
        for(int index=0;index<resString.length();index++)//将字符串中的字符逐个按行输出
        {
            //获取s中的每个字符
            char c = resString.charAt(index);

            //level大于0并且jsonForMatStr中的最后一个字符为\n,jsonForMatStr加入\t
            if (level > 0  && '\n' == jsonForMatStr.charAt(jsonForMatStr.length() - 1)) {
                jsonForMatStr.append(getLevelStr(level));
            }
            //遇到"{"和"["要增加空格和换行，遇到"}"和"]"要减少空格，以对应，遇到","要换行
            switch (c) {
                case '{':
                case '[':
                    jsonForMatStr.append(c + "\n");
                    level++;
                    break;
                case ',':
                    jsonForMatStr.append(c + "\n");
                    break;
                case '}':
                case ']':
                    jsonForMatStr.append("\n");
                    level--;
                    jsonForMatStr.append(getLevelStr(level));
                    jsonForMatStr.append(c);
                    break;
                default:
                    jsonForMatStr.append(c);
                    break;
            }
        }
        return jsonForMatStr.toString();
    }

    private static String getLevelStr(int level) {
        StringBuffer levelStr = new StringBuffer();
        for (int levelI = 0; levelI < level; levelI++) {
            levelStr.append("\t");
        }
        return levelStr.toString();
    }

    public static void formatContent(Object object){
        System.out.println("对象＝=>"+object.getClass().getSimpleName());
        System.out.println("---------------------打印格式化数据----------------------");
        System.out.println(responseFormat(JSONObject.toJSONString(object)));
    }




    public static void main(String[] args) {
        String content="{\"header\":{\"transSn\":\"e33128bb7622462ebfb2cbfcc46baa14\",\"dateTime\":\"20181002110000\",\"serviceCode\":\"********\",\"appId\":\"999999999999\",\"bizId\":\"000000\",\"version\":\"1.0\",\"resType\":\"A\",\"resCode\":\"SSS000\",\"resMsg\":\"交易成功(业务附加消息:*****)\"},\"resBody\":{\"operator\":\"lgh\",\"rowNumStart\":\"1\",\"pageRowNum\":\"100\",\"pageFlag\":\"0\",\"totalRowNum\":\"1\",\"orderFlag\":\"0\",\"orderField\":\"\",\"result\":[{\"flag\":\"1\",\"riskCode\":\"00567000\",\"riskName\":\"567*********\",\"prem\":\"520.00\",\"amntOrMult\":\"***\",\"amnt\":\"200000\",\"polNo\":\"12177\"}]}}";
        System.out.println(responseFormat(content));

    }
}
