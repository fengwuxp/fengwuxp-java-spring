package com.oak.codegen;

import com.levin.commons.dao.annotation.Ignore;
import com.levin.commons.dao.annotation.Like;
import com.levin.commons.service.domain.Desc;
import com.levin.commons.service.domain.InjectDomain;
import com.levin.commons.service.domain.Secured;
import com.wuxp.basic.enums.DescriptiveEnum;
import freemarker.template.Configuration;
import freemarker.template.DefaultObjectWrapperBuilder;
import freemarker.template.Template;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.Accessors;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.core.ResolvableType;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import javax.persistence.*;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Modifier;
import java.text.MessageFormat;
import java.util.*;

public final class ServiceModelUtil {

    private static final Logger logger = LoggerFactory.getLogger(ServiceModelUtil.class);
    private static Set<Class> baseTypes = new HashSet<>();
    private static Set<Class> collectionsTypes = new HashSet<>();
    private static Set<String> notUpdateNames = new HashSet<>();

    static {
        baseTypes.add(Integer.class);
        baseTypes.add(Long.class);
        baseTypes.add(Boolean.class);
        baseTypes.add(Short.class);
        baseTypes.add(Byte.class);
        baseTypes.add(String.class);
        baseTypes.add(Double.class);
        baseTypes.add(Float.class);
        baseTypes.add(Date.class);

        collectionsTypes.add(List.class);
        collectionsTypes.add(Set.class);
        collectionsTypes.add(Map.class);

        notUpdateNames.add("addTime");
        notUpdateNames.add("updateTime");
        notUpdateNames.add("sn");
    }

    /**
     * 实体转服务模型
     *
     * @param entityClass 实体类
     * @param target      输出目录
     */
    public static void entity2ServiceModel(Class entityClass, Map<String, Class> entityMapping, String basePackageName, String target) throws Exception {
        basePackageName = basePackageName.toLowerCase();
        List<FieldModel> fields = buildFieldModel(entityClass, entityMapping, true);

        String desc = entityClass.isAnnotationPresent(Desc.class)
                ? ((Desc) entityClass.getAnnotation(Desc.class)).value()
                : entityClass.getSimpleName();

        String serviceTarget = target + File.separator + "services";
        String serviceTestTarget = target + File.separator + "test";
        String controllerTarget = target + File.separator + "controller";

        buildInfo(entityClass, desc, basePackageName, fields, serviceTarget);
        buildEvt(entityClass, desc, basePackageName, fields, serviceTarget);
        buildService(entityClass, desc, basePackageName, fields, serviceTarget);
        buildServiceTest(entityClass, desc, basePackageName, fields, serviceTestTarget);
        buildController(entityClass, desc, basePackageName, fields, controllerTarget);

    }


    private static void buildInfo(Class entityClass, String desc, String basePackageName, List<FieldModel> fields, String target) throws Exception {

        String className = MessageFormat.format("{0}Info", entityClass.getSimpleName());
        String packageName = MessageFormat.format("{0}.info", basePackageName);

        FieldModel pkField = getPkField(entityClass, fields);


        Map<String, Object> params = new HashMap<>();
        params.put("desc", desc);
        params.put("className", className);
        params.put("packageName", packageName);
        params.put("entityName", entityClass.getSimpleName());
        //params.put("serialVersionUID", serialVersionUID);
        params.put("fields", fields);
        params.put("pkField", pkField);

        String dir = MessageFormat.format("{0}/{1}/", target, packageName.replace(".", "/")).toLowerCase();
        if (!new File(dir).exists()) {
            new File(dir).mkdirs();
        }
        String fileName = dir + className + ".java";
        Writer hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("bean.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);

    }

    private static FieldModel getPkField(Class entityClass, List<FieldModel> fields) {

        for (FieldModel field : fields) {
            if (field.getPk()) {
                return field;
            }
        }

        return null;
    }

    private static void buildEvt(Class entityClass, String desc, String basePackageName, List<FieldModel> fields, String target) throws Exception {

        String packageName = basePackageName + ".req";

        FieldModel pkField = getPkField(entityClass, fields);

        Map<String, Object> params = new HashMap<>();
        params.put("desc", desc);

        params.put("packageName", packageName);
        //params.put("serialVersionUID", serialVersionUID);
        params.put("entityName", entityClass.getSimpleName());

        params.put("pkField", pkField);


        String dir = target + "/" + (packageName.replace(".", "/")) + "/";
        if (!new File(dir).exists()) {
            new File(dir).mkdirs();
        }

        //创建
        params.put("fields", filter(fields, "id", "createTime", "updateTime"));
        String className = "Create" + entityClass.getSimpleName() + "Req";
        params.put("className", className);
        String fileName = dir + className + ".java";
        Writer hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("create_evt.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);

        //查找
        params.put("fields", fields);

        className = "Find" + entityClass.getSimpleName() + "Req";
        params.put("className", className);
        fileName = dir + className + ".java";
        hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("find_evt.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);

        //编辑
        params.put("fields", filter(fields, "createTime", "updateTime"));
        className = "Edit" + entityClass.getSimpleName() + "Req";
        params.put("className", className);
        fileName = dir + className + ".java";
        hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("edit_evt.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);

        //删除
        className = "Delete" + entityClass.getSimpleName() + "Req";
        params.put("className", className);
        fileName = dir + className + ".java";
        hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("del_evt.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);

        //查询
        params.put("fields", fields);
        className = "Query" + entityClass.getSimpleName() + "Req";
        params.put("className", className);
        fileName = dir + className + ".java";
        hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("query_evt.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);

    }


    private static List<FieldModel> filter(List<FieldModel> old, String... ignoreNames) {


        ArrayList<FieldModel> fieldModels = new ArrayList<>(old.size());


        for (FieldModel fieldModel : old) {

            boolean isFiltered = false;

            for (String fieldName : ignoreNames) {
                if (fieldModel.name.equals(fieldName)) {
                    isFiltered = true;
                    break;
                }
            }

            if (!isFiltered) {
                fieldModels.add(fieldModel);
            }

        }

        return fieldModels;

    }


    private static void buildService(Class entityClass, String desc, String basePackageName, List<FieldModel> fields, String target) throws Exception {
        String packageName = basePackageName;

        FieldModel pkField = getPkField(entityClass, fields);

        Map<String, Object> params = new HashMap<>();
        params.put("desc", desc);

        params.put("packageName", packageName);
        //params.put("serialVersionUID", serialVersionUID);
        params.put("entityName", entityClass.getSimpleName());
        params.put("entityClassName", entityClass.getPackage().getName() + "." + entityClass.getSimpleName());
        params.put("fields", fields);
        params.put("pkField", pkField);

        String dir = target + "/" + (packageName.replace(".", "/")) + "/";
        if (!new File(dir).exists()) {
            new File(dir).mkdirs();
        }

        //创建服务接口
        String serviceName = entityClass.getSimpleName() + "Service";
        //packageName.substring(packageName.lastIndexOf(".") + 1);
//        serviceName = serviceName.substring(0, 1).toUpperCase() + serviceName.substring(1) + "Service";
        params.put("className", serviceName);
        String fileName = dir + serviceName + ".java";
        Writer hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("service.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);

        params.put("serviceName", serviceName);
        //创建服务实现
        serviceName = serviceName + "Impl";
        params.put("className", serviceName);
        fileName = dir + serviceName + ".java";
        hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("service_impl.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);
    }

    private static void buildServiceTest(Class entityClass, String desc, String basePackageName, List<FieldModel> fields, String target) throws Exception {
        String packageName = basePackageName;

        FieldModel pkField = getPkField(entityClass, fields);

        Map<String, Object> params = new HashMap<>();
        params.put("desc", desc);

        params.put("packageName", packageName);
        //params.put("serialVersionUID", serialVersionUID);
        params.put("entityName", entityClass.getSimpleName());
        params.put("entityClassName", entityClass.getPackage().getName() + "." + entityClass.getSimpleName());
        params.put("fields", filter(fields, "createTime"));
        params.put("pkField", pkField);

        String dir = target + "/" + (packageName.replace(".", "/")) + "/";
        if (!new File(dir).exists()) {
            new File(dir).mkdirs();
        }

        //创建服务测试类
        String serviceName = entityClass.getSimpleName() + "Service";//packageName.substring(packageName.lastIndexOf(".") + 1);
//        serviceName = serviceName.substring(0, 1).toUpperCase() + serviceName.substring(1) + "Service";
        params.put("className", serviceName);
        String fileName = dir + serviceName + "Test.java";
        Writer hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("service_test.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);

    }

    private static void buildController(Class entityClass, String desc, String basePackageName, List<FieldModel> fields, String target) throws Exception {

        String packageName = basePackageName;

        String controllerPackageName = basePackageName.replace(".services", ".admin.controller");

        controllerPackageName = controllerPackageName.replace("provide.", "");


      //  String serviceName = packageName.substring(packageName.lastIndexOf(".") + 1);
      //  serviceName = serviceName.substring(0, 1).toUpperCase() + serviceName.substring(1) + "Service";
        String serviceName = entityClass.getSimpleName() + "Service";
        FieldModel pkField = getPkField(entityClass, fields);

        Map<String, Object> params = new HashMap<>();
        params.put("desc", desc);

        params.put("packageName", packageName);
        params.put("serviceName", serviceName);
        params.put("serviceFullName", packageName + "." + serviceName);
        params.put("controllerPackageName", controllerPackageName);
        params.put("entityName", entityClass.getSimpleName());
        params.put("entityClassName", entityClass.getPackage().getName() + "." + entityClass.getSimpleName());
        params.put("fields", fields);
        params.put("pkField", pkField);

        String dir = target + "/" + (controllerPackageName.replace(".", "/")) + "/";
        if (!new File(dir).exists()) {
            new File(dir).mkdirs();
        }

        //创建控制器
        String className = entityClass.getSimpleName() + "Controller";
        params.put("className", className);
        String fileName = dir + className + ".java";
        Writer hWriter = new OutputStreamWriter(new FileOutputStream(fileName), "utf-8");
        getTemplate("controller.ftl").process(params, hWriter);
        System.out.println("--------------------" + fileName);
    }

    private static List<FieldModel> buildFieldModel(Class clzss, Map<String, Class> entityMapping, boolean excess/*是否生成约定处理字段，如：枚举新增以Desc结尾的字段*/) throws Exception {

        Object obj = clzss.newInstance();

        List<FieldModel> list = new ArrayList<>();

        final List<Field> declaredFields = new LinkedList<>();


        ResolvableType resolvableTypeForClass = ResolvableType.forClass(clzss);


        ReflectionUtils.doWithFields(clzss, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(Field field) throws IllegalArgumentException, IllegalAccessException {


                declaredFields.add(field);

                //  System.out.println("found " + clzss + " : " + field);

            }
        });


        // Field.setAccessible(declaredFields, true);

        for (Field field : declaredFields) {

            field.setAccessible(true);


            if (Modifier.isStatic(field.getModifiers())) {
                continue;
            }

            if (field.isAnnotationPresent(Ignore.class)) {
                continue;
            }

            Class<?> fieldType = ResolvableType.forField(field, resolvableTypeForClass).resolve(field.getType());


            if (field.getType() != fieldType) {
                System.out.println("*** " + clzss + " 发现泛型字段 : " + field + " --> " + fieldType);
            }


            if (collectionsTypes.contains(fieldType)) {
                //暂不支持集合类型
                continue;
            }


            FieldModel fieldModel = new FieldModel();
            fieldModel.setName(field.getName());
            fieldModel.setLength(field.isAnnotationPresent(Column.class) ? field.getAnnotation(Column.class).length() : -1);

            fieldModel.setType(fieldType.getSimpleName());

            fieldModel.setClassType(fieldType);

            fieldModel.setBaseType(baseTypes.contains(fieldType));

            fieldModel.setEnums(fieldType.isEnum());
            fieldModel.setCollections(collectionsTypes.contains(fieldType));

            fieldModel.setComplex(!fieldType.isPrimitive()
                    && !fieldModel.getBaseType()
                    && !fieldModel.getEnums()
                    && !fieldModel.getCollections());

            if (fieldModel.getComplex()) {
                //得到包名 com.oaknt.udf.entities - com.oaknt.udf.servicess.sample.info;

                String typePackageName = fieldType.getPackage().getName();

                typePackageName = typePackageName.replace("entities", "services") + "."
                        + fieldType.getSimpleName().toLowerCase() + ".info";

                fieldModel.setComplexClassPackageName(typePackageName);

                //  fieldModel.getImports().add(typePackageName);


                //  fieldModel.infoClassName =  typePackageName + "." + fieldType.getSimpleName() + "Info";

            }

            fieldModel.setDesc(field.isAnnotationPresent(Schema.class) ? field.getAnnotation(Schema.class).description() : field.getName());
            fieldModel.setDescDetail(field.isAnnotationPresent(Schema.class) ? field.getAnnotation(Schema.class).description() : "");
            fieldModel.setPk(field.isAnnotationPresent(Id.class));
            fieldModel.setLike(field.isAnnotationPresent(Like.class));
            fieldModel.setNotUpdate(fieldModel.getPk() || notUpdateNames.contains(fieldModel.getName()) || fieldModel.getComplex());
            if (fieldModel.getPk()) {
                fieldModel.setRequired(true);
                fieldModel.setIdentity(field.isAnnotationPresent(GeneratedValue.class)
                        && !field.getAnnotation(GeneratedValue.class).strategy().equals(GenerationType.AUTO));
            } else {
                fieldModel.setUk(field.isAnnotationPresent(Column.class) && field.getAnnotation(Column.class).unique());
                fieldModel.setRequired(field.isAnnotationPresent(Column.class) && !field.getAnnotation(Column.class).nullable());
            }
            if (field.isAnnotationPresent(ManyToOne.class) || field.isAnnotationPresent(OneToOne.class)) {
                fieldModel.setComplex(true);
                if (field.isAnnotationPresent(ManyToOne.class)) {
                    fieldModel.setLazy(field.getAnnotation(ManyToOne.class).fetch().equals(FetchType.LAZY));
                } else if (field.isAnnotationPresent(OneToOne.class)) {
                    fieldModel.setLazy(field.getAnnotation(OneToOne.class).fetch().equals(FetchType.LAZY));
                }
                Class aClass = entityMapping.get(field.getName());
                if (aClass != null) {
                    fieldModel.setInfoClassName(aClass.getPackage().getName() + "." + aClass.getSimpleName());
                }
                fieldModel.setTestValue("null");
            }

            //生成注解
            ArrayList<String> annotations = new ArrayList<>();

            if (fieldModel.getRequired()) {
                annotations.add("@NotNull");
            }

            //
            if (field.isAnnotationPresent(InjectDomain.class)) {
                annotations.add("@" + InjectDomain.class.getSimpleName() + "");
                fieldModel.getImports().add(InjectDomain.class.getName());
            }

            if (field.isAnnotationPresent(Secured.class)) {
                annotations.add("@" + Secured.class.getSimpleName());
                fieldModel.getImports().add(Secured.class.getName());
            }


            if (fieldModel.getClassType().equals(String.class)
                    && fieldModel.getLength() != -1
                    && !fieldModel.getName().endsWith("Body")) {
                boolean isLob = field.isAnnotationPresent(Lob.class);
                if (isLob) {
                    fieldModel.setLength(4000);
                    fieldModel.setTestValue("\"这是长文本正文\"");
                }
                if (fieldModel.getLength() != 255) {
                    annotations.add("@Size(max = " + fieldModel.getLength() + ")");
                    fieldModel.setTestValue("\"这是文本" + fieldModel.getLength() + "\"");
                }
            }
            //是否约定
            if (fieldModel.getName().endsWith("Pct")) {
                annotations.add("@Min(0)");
                annotations.add("@Max(100)");
                fieldModel.setTestValue("50");
            } else if (fieldModel.getName().endsWith("Ppt")) {
                annotations.add("@Min(0)");
                annotations.add("@Max(1000)");
                fieldModel.setTestValue("500");
            } else if (field.isAnnotationPresent(Pattern.class)) {
                String regexp = field.getAnnotation(Pattern.class).regexp();
                if (!StringUtils.isEmpty(regexp)) {
                    regexp = regexp.replace("\\", "\\\\");
                    annotations.add("@Pattern(regexp = \"" + regexp + "\")");
                }
            } else if (field.isAnnotationPresent(Size.class)) {
                annotations.add("@Size(min = " + field.getAnnotation(Size.class).min() + " , max = " + field.getAnnotation(Size.class).max() + ")");
            } else if (field.isAnnotationPresent(Min.class)) {
                annotations.add("@Min(" + field.getAnnotation(Min.class).value() + ")");
                fieldModel.setTestValue(field.getAnnotation(Min.class).value() + "");
            } else if (field.isAnnotationPresent(Max.class)) {
                annotations.add("@Max(" + field.getAnnotation(Max.class).value() + ")");
                fieldModel.setTestValue(field.getAnnotation(Max.class).value() + "");
            }

            fieldModel.setAnnotations(annotations);

            if (excess) {
                buildExcess(fieldModel);
            }

            String fieldValue = getFieldValue(field.getName(), obj);
            if (fieldValue != null) {
                fieldModel.setHasDefValue(true);
                fieldModel.setTestValue(fieldValue);
            }

            if (fieldModel.getTestValue() == null) {
                if (fieldModel.getName().equals("sn")) {
                    String sn = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 10).toUpperCase();
                    fieldModel.setTestValue("\"" + sn + "\"");
                } else if (fieldModel.getName().equals("areaId")) {
                    fieldModel.setTestValue("\"1\"");
                } else if (fieldModel.enums) {
                    fieldModel.setTestValue(fieldType.getSimpleName() + "." + getEnumByVal(fieldType, 0).name());
                } else if (fieldModel.getClassType().equals(Boolean.class)) {
                    fieldModel.setTestValue("true");
                } else if (fieldModel.getClassType().equals(String.class)) {
                    fieldModel.setTestValue("\"" + fieldModel.getDesc() + "_1\"");
                } else if (fieldModel.getClassType().equals(Integer.class) || fieldModel.getClassType().equals(Long.class)) {
                    fieldModel.setTestValue(fieldModel.getName().endsWith("Id")
                            ? "null" : ("1" + (fieldModel.getClassType().equals(Long.class) ? "L" : "")));
                } else if (fieldModel.getClassType().equals(Double.class)) {
                    fieldModel.setTestValue("0.1d");
                } else if (fieldModel.getClassType().equals(Float.class)) {
                    fieldModel.setTestValue("0.1f");
                } else if (fieldModel.getClassType().equals(Date.class)) {
                    fieldModel.setTestValue("new Date()");
                }
            }

            list.add(fieldModel);
        }
        return list;
    }


    public static String getFieldValue(String fieldName, Object obj) {
        if (fieldName == null || obj == null) {
            return null;
        }
        Field field = ReflectionUtils.findField(obj.getClass(), fieldName);
        assert field != null;
        Object value = ReflectionUtils.getField(field, obj);
        if (value == null) {
            return null;
        }
        return value.toString();
    }

    private static void buildExcess(FieldModel fieldModel) {

        String name = fieldModel.getName();
        Class type = fieldModel.getClassType();

        if (fieldModel.getEnums()
                && DescriptiveEnum.class.isAssignableFrom(type)) {
            //枚举描述
            fieldModel.setExcessSuffix("Desc");
            fieldModel.setExcessReturnType("String");
            fieldModel.setExcessReturn("return " + name + " != null ? " + name + ".getDesc() : \"\";");
        } else if ((type.equals(Integer.class) || type.equals(Long.class))
                && name.endsWith("Fen")) {
            //分转元
            fieldModel.setExcessSuffix("2Yuan");
            fieldModel.setExcessReturnType("Double");
            fieldModel.setExcessReturn("return " + name + " != null ? new java.math.BigDecimal(" + name + ")\n" +
                    "                .divide(new java.math.BigDecimal(100), 2, java.math.BigDecimal.ROUND_HALF_UP)\n" +
                    "                .doubleValue() : null;");
        } else if ((type.equals(Integer.class) || type.equals(Long.class))
                && name.endsWith("Ppt")) {
            //千分比转百分比
            fieldModel.setExcessSuffix("2Pct");
            fieldModel.setExcessReturnType("Double");
            fieldModel.setExcessReturn("return " + name + " != null ? new java.math.BigDecimal(" + name + ")\n" +
                    "                .divide(new java.math.BigDecimal(10), 1, java.math.BigDecimal.ROUND_HALF_UP)\n" +
                    "                .doubleValue() : null;");
        } else if (fieldModel.getComplex()) {
            String returnName = type.getSimpleName().substring(0, 1).toUpperCase() + type.getSimpleName().substring(1)
                    + "Info";
            String complexName = name.substring(0, 1).toUpperCase() + name.substring(1)
                    + "Info";

            fieldModel.setExcessSuffix("Info");
            fieldModel.setExcessReturnType(returnName);
            fieldModel.setExcessReturn("return " + name + " != null ? " + name + ".get" + complexName + "() : null;");
        }

    }

    private static Template getTemplate(String templatePath) throws Exception {
        //创建一个合适的Configration对象
        Configuration configuration = new Configuration(Configuration.VERSION_2_3_0);
        configuration.setObjectWrapper(new DefaultObjectWrapperBuilder(Configuration.VERSION_2_3_0).build());
        configuration.setDefaultEncoding("UTF-8");
        configuration.setClassForTemplateLoading(ServiceModelUtil.class.getClassLoader().getClass(), "/template");

        //获取页面模版。
        return configuration.getTemplate(templatePath);
    }

    private static Enum getEnumByVal(Class ec, int i) {
        Iterator iter = EnumSet.allOf(ec).iterator();

        Enum e;
        do {
            if (!iter.hasNext()) {
                return null;
            }

            e = (Enum) iter.next();
        } while (e.ordinal() != i);

        return e;
    }

    @Data
    @NoArgsConstructor
    @EqualsAndHashCode(of = "name")
    @ToString()
    @Accessors(chain = true)
    public static class FieldModel {

        private String name;

        String prefix;

        private String type;

        private Integer length = -1;

        private Class classType;

        private String desc;

        private String descDetail;

        private Set<String> imports = new LinkedHashSet<>();

        private List<String> annotations = new ArrayList<>();

        private Boolean pk = false;//是否主键字段

        private Boolean uk = false;//是否唯一键

        private Boolean baseType = true;//基础封装类型

        private Boolean enums = false;//是否enum

        private Boolean complex = false;//是否复杂对象

        private String complexClassPackageName;//复杂对象包名

        private Boolean collections = false;//是否集合

        private Boolean required = false;//是否必填

        private Boolean identity; //是否自动增长主键

        private Boolean notUpdate = false;//是否不需要更新

        private Boolean hasDefValue = false;//是否有默认值

        private Boolean lazy;//是否lazy

        private String excessSuffix;//生成额外的字段后缀

        private String excessReturnType;//生成额外的返回类型

        private String excessReturn;//生成额外的返回

        private String infoClassName;

        private String testValue;

        private Boolean like;//是否生成模糊查询


    }

}
