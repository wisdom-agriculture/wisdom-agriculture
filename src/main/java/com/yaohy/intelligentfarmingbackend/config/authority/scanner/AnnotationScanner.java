package com.yaohy.intelligentfarmingbackend.config.authority.scanner;

import com.yaohy.intelligentfarmingbackend.config.authority.annotation.App;
import com.yaohy.intelligentfarmingbackend.config.authority.annotation.Web;
import com.yaohy.intelligentfarmingbackend.config.authority.decorator.EndAuthorityDecorator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.File;
import java.lang.reflect.Method;
import java.net.JarURLConnection;
import java.net.URL;
import java.util.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;

@Slf4j
@Component
public class AnnotationScanner {

    private AnnotationScanner() {
    }

    static {
        Enumeration<URL> resources = null;
        try {
            resources = Thread.currentThread().getContextClassLoader().getResources("com.yaohy.intelligentfarmingbackend.controller".replaceAll("\\.", "/"));
            while (resources.hasMoreElements()) {
                URL url = resources.nextElement();

                if (url.getProtocol().equals("jar")) {
                    JarFile jar;
                    jar = ((JarURLConnection) url.openConnection()).getJarFile();
                    Enumeration<JarEntry> entries = jar.entries();

                    Set<String> webSourceSet = new LinkedHashSet<>();
                    Set<String> appSourceSet = new LinkedHashSet<>();
                    while (entries.hasMoreElements()) {
                        JarEntry jarEntry = entries.nextElement();
                        String name = jarEntry.getName();
                        if (name.startsWith("com.yaohy.intelligentfarmingbackend.controller".replaceAll("\\.", "/")) &&
                                name.endsWith(".class")) {
                            String className = name.replaceAll("/", "\\.").substring(0, name.length() - 6);
//                            System.out.println(className);
                            Class<?> aClass = null;
                            try {
                                aClass = Class.forName(className);

                                String baseRequestPath = "";
                                if (aClass.isAnnotationPresent(RequestMapping.class)) {
                                    RequestMapping requestMappingClass = aClass.getAnnotation(RequestMapping.class);
                                    baseRequestPath = requestMappingClass.value()[0];
                                    if (!baseRequestPath.startsWith("/")) baseRequestPath = "/" + baseRequestPath;
                                }


                                for (Method method : aClass.getMethods()) {
                                    if (method.isAnnotationPresent(RequestMapping.class)) {
                                        RequestMapping requestMappingMethod = method.getAnnotation(RequestMapping.class);
                                        String requestMethodPath = requestMappingMethod.value()[0];
                                        if (!requestMethodPath.startsWith("/")) requestMethodPath = "/" + requestMethodPath;

                                        String requestPath = baseRequestPath + requestMethodPath;
//                                        System.out.println(requestPath);
                                        if (method.isAnnotationPresent(Web.class)) {
                                            webSourceSet.add(requestPath);
                                        }
                                        if (method.isAnnotationPresent(App.class)) {
                                            appSourceSet.add(requestPath);
                                        }
                                    }
                                }

                            } catch (Exception e) {
                                e.printStackTrace();
                            }
//                            System.out.println(aClass);
                        }
                    }

                    EndAuthorityDecorator.setWebSourceSet(webSourceSet);
                    EndAuthorityDecorator.setAppSourceSet(appSourceSet);

                    log.info("----webSourceList----");
                    log.info(webSourceSet.toString());
                    log.info("----appSourceList----");
                    log.info(appSourceSet.toString());

                }
                if (url.getProtocol().equals("file")) {
//                    System.out.println(url);
//                    System.out.println(url.getProtocol());
//                    System.out.println(url.getPath());

                    File[] files = new File(url.getPath()).listFiles();
                    assert files != null;

                    Set<String> webSourceSet = new LinkedHashSet<>();
                    Set<String> appSourceSet = new LinkedHashSet<>();
                    for (File file : files) {
//                    System.out.println(file.getName());
                        Class<?> aClass = null;
                        try {
                            aClass = Class.forName("com.yaohy.intelligentfarmingbackend.controller." + file.getName().replace(".class", ""));

//                            if (aClass.isAnnotationPresent(RequestMapping.class)){

                            String baseRequestPath = "";
                            if (aClass.isAnnotationPresent(RequestMapping.class)) {
                                RequestMapping requestMappingClass = aClass.getAnnotation(RequestMapping.class);
                                baseRequestPath = requestMappingClass.value()[0];
                                if (!baseRequestPath.startsWith("/")) baseRequestPath = "/" + baseRequestPath;
                            }


                            for (Method method : aClass.getMethods()) {
                                if (method.isAnnotationPresent(RequestMapping.class)) {
                                    RequestMapping requestMappingMethod = method.getAnnotation(RequestMapping.class);
                                    String requestMethodPath = requestMappingMethod.value()[0];
                                    if (!requestMethodPath.startsWith("/")) requestMethodPath = "/" + requestMethodPath;

                                    String requestPath = baseRequestPath + requestMethodPath;
//                                        System.out.println(requestPath);
                                    if (method.isAnnotationPresent(Web.class)) {
                                        webSourceSet.add(requestPath);
                                    }
                                    if (method.isAnnotationPresent(App.class)) {
                                        appSourceSet.add(requestPath);
                                    }
                                }
                            }
//                            }

                        } catch (Exception e) {
                            e.printStackTrace();
                        }
//                        System.out.println(aClass);
                    }

                    EndAuthorityDecorator.setWebSourceSet(webSourceSet);
                    EndAuthorityDecorator.setAppSourceSet(appSourceSet);

                    log.info("----webSourceList----");
                    log.info(webSourceSet.toString());
                    log.info("----appSourceList----");
                    log.info(appSourceSet.toString());
                }

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
