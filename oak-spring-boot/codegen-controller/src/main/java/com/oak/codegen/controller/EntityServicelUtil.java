package com.oak.codegen.controller;

import cn.hutool.core.io.FileUtil;
import org.apache.commons.lang3.StringUtils;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by zhuiyi on 19/6/20.
 */
public class EntityServicelUtil {

    private static final String PACKAGE_SEPARATOR = ".";
//    public static  String entitiesPackage = "com.oaknt.yunxin.entities";
//    public static  String servicePackage = "com.oaknt.yunxin.services";
//    public static  String codeGenerateDir="/Users/zhuiyi/generatecode";

    public static  String entitiesPackage = "";
    public static  String servicePackage = "";
    public static  String codeGenerateDir="";



    /** add by zhuiyi 20190620
     * 获取指定包下简洁类名
     * @return List
     */

    private static List<String> getClazzName() {
        List<String> list = ClazzUtils.getClazzName(entitiesPackage, false);
        //过滤以"E_"开头的类文件
        String filter_pre = "E_";
        list.removeIf(s -> s.startsWith(filter_pre));
        return list;
    }

    /** add by zhuiyi 20190620
     * 通过类名生成代码文件
     * @param className 类名称
     * @return List
     */
    private static void entity2Service (String className, Map<String, Class> entityMapping) throws Exception{
        if(!checkPath()){
            return;
        }
        String entitiesPath=entitiesPackage+PACKAGE_SEPARATOR+className;
        if(Character.isUpperCase(className.charAt(0))){
            className=(new StringBuilder()).append(Character.toLowerCase(className.charAt(0))).append(className.substring(1)).toString();
        }
        String servicePath= servicePackage+PACKAGE_SEPARATOR+className;
        ServiceModelUtil.entity2ServiceModel(Class.forName(entitiesPath), entityMapping,
                servicePath, codeGenerateDir);
    }

    /** add by zhuiyi 20190620
     * 代码文件复制到项目空间
     * @return List
     */
    private static  void copyEntityDir(){
        if(!checkPath()){
            return;
        }
        String srcDir=codeGenerateDir+File.separator+"services"+File.separator+"com"+File.separator;
        String totalPath=Class.class.getClass().getResource("/").getPath();
        String destDir=totalPath.substring(0,totalPath.indexOf(File.separator+"services"+File.separator)+9)+"/src/main/java/".replaceAll("/",File.separator);;
        FileUtil.copy(srcDir,destDir,false);
    }

    /** add by zhuiyi 20190620
     * 生成代码并复制到项目控制
     * @return
     */
    public static void  entityGenerateAndCopy(String className, Map<String, Class> entityMapping)throws Exception{
        if(!checkPath()){
            return;
        }
        entity2Service(className, entityMapping);
        copyEntityDir();
    }

    /** add by zhuiyi 20190620
     * 批量生成代码并复制到项目控制
     * @return
     */
    public static void  batchEntityGenerateAndCopy(List<String> classNameList)throws Exception{
        if(!checkPath()||(classNameList==null||classNameList.size()==0)){
            return;
        }
        Map<String, Class> entityMapping = new HashMap<>();
        for(String className:classNameList) {
            entity2Service(className, entityMapping);
        }
        copyEntityDir();
    }

    /** add by zhuiyi 20190620
     * 全部生成代码并复制到项目控制
     * @Param excludeList 排除的对象名称
     * @return
     */
    public static void  allEntityGenerateAndCopy(List<String> excludeList)throws Exception{
        if(!checkPath()){
            return;
        }
        List<String> allList=getClazzName();
        allList.removeAll(excludeList);
        batchEntityGenerateAndCopy(allList);
    }


    public static Boolean checkPath(){
        if(StringUtils.isEmpty(entitiesPackage)|| StringUtils.isEmpty(servicePackage)|| StringUtils.isEmpty(codeGenerateDir)){
            System.out.println("路径不能为空");
            return false;
        }
        return true;
    }

    public static void main(String[] args) {


    }
}
