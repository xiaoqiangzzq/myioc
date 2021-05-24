package org.springframework.mvc.servlet;

import com.google.gson.Gson;
import org.springframework.mvc.annotation.RequestBody;
import org.springframework.mvc.annotation.RequestParam;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

public class HandleAdapter {

    private HandlerMapping handlerMapping;

    private HttpServletRequest httpServletRequest;

    private HttpServletResponse httpServletResponse;

    //必须传参的集合
    private Map<String,Class<?>> requiredMap = new HashMap<>();
    //非必须参数
    private Map<String,Class<?>> unRequiredMap = new TreeMap<>();

    //所有参数集合
    private Set<String> paramtersMap = new TreeSet<>();

    public HandleAdapter(HandlerMapping handlerMapping, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) {
        this.handlerMapping = handlerMapping;
        this.httpServletRequest = httpServletRequest;
        this.httpServletResponse = httpServletResponse;
    }

    public void handle() throws Exception {
        System.out.println("调用控制器方法。。。");
        Method method = handlerMapping.getMethod();
        Object controllerObject = handlerMapping.getControllerObject();
        getMethodParameterInfo(method);
        //暂时未考虑参数为一个对象
        Object[] args = getArgs();
        //反射调用控制器方法
        Object invoke = method.invoke(controllerObject, args);
        System.out.println("控制器方法的返回值："+invoke);
        boolean annotationPresent = method.isAnnotationPresent(RequestBody.class);
        if(annotationPresent){
            //返回json数据格式
            responseJson(invoke);
        }else if(invoke instanceof String){
            //返回是字符串，调整json视图
            responseJsp(invoke);
        }else {
            System.out.println("控制方法没有返回值.");
        }
    }

    private void responseJsp(Object result){
        try {
            //转发路径因该从springmvc配置文件读取
            httpServletRequest.getRequestDispatcher("/WEB-INF/jsp/" + result + ".jsp").forward(httpServletRequest,httpServletResponse);
        } catch (ServletException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void responseJson(Object result){
        Gson gson = new Gson();
        String s = gson.toJson(result);
        httpServletResponse.setContentType("application/json;charset=utf-8");
        try {
            httpServletResponse.getWriter().write(s);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 封装参数
     *
     * @return java.lang.Object[]
     * @author Zeng zhiqiang 2021/5/21 15:15
     */
    public Object[] getArgs() throws Exception {
        //参数列表值的集合
        Object[] args = new Object[paramtersMap.size()];
        int i = 0;
        for (String paramter : paramtersMap) {
            //参数列表
            Map<String, String[]> parameterMap = httpServletRequest.getParameterMap();
            String[] values = parameterMap.get(paramter);
            boolean b = requiredMap.containsKey(paramter);
            if(Boolean.TRUE.equals(b)){
                Class<?> aClass = requiredMap.get(paramter);
                if(null == values){
                    //这个参数必须要传入，但是没有
                    httpServletResponse.sendError(500,paramter + "没有传入");
                    //throw new Exception(paramter + "没有传入");
                }else {
                    //传入了必要的参数
                    args[i++] = convertValue(aClass,values);
                }
            }else {
                //非必须参数
                Class<?> aClass = unRequiredMap.get(paramter);
                if(values == null){
                    //默认值
                }else {
                    args[i++] = convertValue(aClass,values);
                }
            }

        }
        return args;
    }


    private Object convertValue(Class<?> c,String[] values){
        if(c.isArray()){
            //数组类转换
            String simpleName = c.getSimpleName();
            if(simpleName.equalsIgnoreCase("String[]")){
                return values;
            }else{
                return null;
            }
        }else{
            return this.convertSingleValue(c,values[0]);
        }
    }

    /**
     * 单个的类型参数
     * @param c
     * @param value
     * @return
     */
    private Object convertSingleValue(Class<?> c,String value){
        if(c==int.class){
            return Integer.valueOf(value);
        }else if(c==double.class){
            return Double.valueOf(value);
        }else if(c==float.class){
            return Float.valueOf(value);
        }else{
            //剩下就是字符串
            return value;
        }
    }


    /**
     * 获取参数信息
     *
     * @param method
     * @return void
     * @author Zeng zhiqiang 2021/5/21 15:10
     */
    private void getMethodParameterInfo(Method method){
        Parameter[] parameters = method.getParameters();
        if(null != parameters){
            for (Parameter parameter : parameters) {
                //形参的参数名称
                String name = parameter.getName();
                //形参的参数类型
                Class<?> type = parameter.getType();
                if(parameter.isAnnotationPresent(RequestParam.class)){
                    RequestParam requestParam = parameter.getAnnotation(RequestParam.class);
                    boolean required = requestParam.required();
                    String name1 = requestParam.name();
                    if(!name1.isEmpty()){
                        name = name1;
                    }
                    if(Boolean.TRUE.equals(required)){
                        requiredMap.put(name,type);
                    }else {
                        unRequiredMap.put(name,type);
                    }
                }else {
                    unRequiredMap.put(name,type);
                }
                paramtersMap.add(name);
            }
        }
    }

    public HandlerMapping getHandlerMapping() {
        return handlerMapping;
    }

    public void setHandlerMapping(HandlerMapping handlerMapping) {
        this.handlerMapping = handlerMapping;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public HttpServletResponse getHttpServletResponse() {
        return httpServletResponse;
    }

    public void setHttpServletResponse(HttpServletResponse httpServletResponse) {
        this.httpServletResponse = httpServletResponse;
    }
}
