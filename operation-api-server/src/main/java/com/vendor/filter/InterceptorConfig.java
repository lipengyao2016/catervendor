package com.vendor.filter;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.common.io.Files;
import com.vendor.config.entity.ConfigPlan;
import com.vendor.config.entity.ConfigRule;
import com.vendor.utils.GsonUtils;
import com.vendor.utils.StrUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import com.vendor.utils.ReflectUtils;
import org.springframework.util.ResourceUtils;
import redis.clients.jedis.Jedis;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.nio.charset.Charset;
import java.util.*;
import java.util.function.Predicate;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Component
public class InterceptorConfig {
    private static Logger log = LoggerFactory.getLogger(InterceptorConfig.class);



    private Map<String,List<ConfigRule>>  ruleConfigMap = new HashMap<>();
    private Map<String,List<ConfigPlan>>  planConfigMap = new HashMap<>();

    private Map<String,Pattern>  urlPatternMap = new HashMap<>();


    public InterceptorConfig()
    {
        String patternStr = "/merchantsdfs$";
        Pattern pattern = Pattern.compile(patternStr);
        Matcher matcher = pattern.matcher("/api/v1/merchants");
        boolean bRet = matcher.find();
       // System.out.println("找到字符串：" + matcher.group(0));

        try {
            File file = ResourceUtils.getFile("classpath:interceptor_config.json");
            String  interceptorConfigStr = Files.toString( file, Charset.defaultCharset());
            log.info(interceptorConfigStr);
            JSONObject interceptorJson = JSONObject.parseObject(interceptorConfigStr);

            JSONObject ruleConfigJson = interceptorJson.getJSONObject("rules");


            for ( String key: ruleConfigJson.keySet()) {
                    JSONArray serverRules =  ruleConfigJson.getJSONArray(key);
                    List<ConfigRule> configRuleList = new ArrayList<>();
                    for(int i = 0;i < serverRules.size();i++)
                    {
                        ConfigRule configRule = new ConfigRule();
                        configRule.setUrl( serverRules.getJSONObject(i).getString("url"));
                        List<String> urlPlans = JSONArray.parseArray(serverRules.getJSONObject(i).getString("plan"),String.class);
                        configRule.setPlanKeys(urlPlans);
                        configRuleList.add(configRule);
                        log.info(GsonUtils.ToJson(configRule,ConfigRule.class));
                    }
                   // List<ConfigRule> configRuleList = JSONArray.parseArray(serverRules.toString(),ConfigRule.class);

                    if(ruleConfigMap.containsKey(key))
                    {
                        ruleConfigMap.get(key).addAll(configRuleList);
                    }
                    else
                    {
                        ruleConfigMap.put(key,configRuleList);
                    }
            }

            JSONObject planConfigJson = interceptorJson.getJSONObject("plans");
            for ( String key: planConfigJson.keySet()) {
                JSONArray serverPlans =  planConfigJson.getJSONArray(key);
                List<ConfigPlan> configPlanList = new ArrayList<>();
                for(int i = 0;i < serverPlans.size();i++)
                {
                    ConfigPlan configPlan = new ConfigPlan();
                    configPlan.setSrc( serverPlans.getJSONObject(i).getString("src"));
                    configPlan.setDest( serverPlans.getJSONObject(i).getString("dest"));
                    List<String> methods = JSONArray.parseArray(serverPlans.getJSONObject(i).getString("method"),String.class);
                    configPlan.setMethod(methods);
                    configPlanList.add(configPlan);
                    //log.info(GsonUtils.ToJson(configPlan,ConfigPlan.class));
                }

                //List<ConfigPlan> configPlanList = JSONArray.parseArray(serverPlans.toString(),ConfigPlan.class);

                if(planConfigMap.containsKey(key))
                {
                    planConfigMap.get(key).addAll(configPlanList);
                }
                else
                {
                    planConfigMap.put(key,configPlanList);
                }
            }

            log.info("init interceptor config ok..");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<ConfigPlan> getServerRules(String path)
    {
        List<ConfigPlan> serverConfigPlan = new ArrayList<>();

        for (String key: ruleConfigMap.keySet()) {
            if(path.toLowerCase().contains(key))
            {
                List<ConfigRule> configRuleList = ruleConfigMap.get(key);



                Optional<ConfigRule> configRuleOptions = configRuleList.stream().filter(

                        new Predicate<ConfigRule>() {
                            @Override
                            public boolean test(ConfigRule rule) {
                                Pattern pattern = null;
                                if(InterceptorConfig.this.urlPatternMap.containsKey(rule.getUrl()))
                                {
                                    pattern = InterceptorConfig.this.urlPatternMap.get(rule.getUrl());
                                }
                                else
                                {
                                    pattern =  Pattern.compile(rule.getUrl());
                                    InterceptorConfig.this.urlPatternMap.put(rule.getUrl(),pattern);
                                }
                                return pattern.matcher(path.toLowerCase()).find();
                            }
                        }

                        //Pattern.matches(item.getUrl(), path.toLowerCase())
                        //path.toLowerCase().contains(item.getUrl())

                ).findFirst();
                 if(configRuleOptions.isPresent())
                 {
                     ConfigRule configRule = configRuleOptions.get();
                     for (String planKey:configRule.getPlanKeys()) {
                         if(this.planConfigMap.containsKey(planKey))
                         {
                             serverConfigPlan.addAll(this.planConfigMap.get(planKey));
                         }
                     }
                 }

                break;
            }

        }

        return  serverConfigPlan;
    }


    public void addParams(JSONObject requestEntityJson,Object srcObj,
                             String method,List<ConfigPlan> serverPlans)
    {
        for (ConfigPlan configPlan: serverPlans) {
            if(configPlan.getMethod().contains(method))
            {
                try {
                    Object fieldVal = ReflectUtils.getField(srcObj,configPlan.getSrc());
                    requestEntityJson.put(configPlan.getDest(),fieldVal);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    public void addGetParams(Map<String, List<String>> requestQueryParams,Object srcObj,
                          String method,List<ConfigPlan> serverPlans)
    {
        for (ConfigPlan configPlan: serverPlans) {
            if(configPlan.getMethod().contains(method))
            {
                try {
                    //Field f = ReflectUtils.getFieldInfo(srcObj.getClass(),configPlan.getSrc());

                    Object fieldVal = ReflectUtils.getField(srcObj,configPlan.getSrc());
                    StrUtils.emObjType fieldObjType = StrUtils.getObjectType(fieldVal);
                    String strVal = "";
                    if(fieldObjType == StrUtils.emObjType.Obj_Long)
                    {
                        strVal = String.valueOf((Long)fieldVal);
                    }
                    else
                    {
                        strVal = (String) fieldVal;
                    }

                    List<String> arrayList = new ArrayList<>();

                    arrayList.add(strVal);
                    requestQueryParams.put(configPlan.getDest(), arrayList);
                } catch (NoSuchFieldException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }

            }
        }
    }




}
