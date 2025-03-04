package com.yxkj.utils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Date;
import java.util.List;

import org.apache.commons.beanutils.BeanUtilsBean;
import org.apache.commons.beanutils.ConvertUtilsBean;
import org.apache.commons.beanutils.Converter;
import org.apache.commons.beanutils.converters.ArrayConverter;
import org.apache.commons.beanutils.converters.DateConverter;
import org.apache.commons.io.IOUtils;
import org.dom4j.Attribute;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.OutputFormat;
import org.dom4j.io.SAXReader;
import org.dom4j.io.XMLWriter;
import org.springframework.core.io.ClassPathResource;

import com.yxkj.beans.CommonAttributes;
import com.yxkj.beans.EnumConverter;
import com.yxkj.beans.Setting;



/**
 * Utils - 系统设置
 * 
 */
@SuppressWarnings("unchecked")
public final class SettingUtils {

  /** BeanUtilsBean */
  private static final BeanUtilsBean beanUtils;

  static {
    ConvertUtilsBean convertUtilsBean = new ConvertUtilsBean() {
      @Override
      public String convert(Object value) {
        if (value != null) {
          Class<?> type = value.getClass();
          if (type.isEnum() && super.lookup(type) == null) {
            super.register(new EnumConverter(type), type);
          } else if (type.isArray() && type.getComponentType().isEnum()) {
            if (super.lookup(type) == null) {
              ArrayConverter arrayConverter =
                  new ArrayConverter(type, new EnumConverter(type.getComponentType()), 0);
              arrayConverter.setOnlyFirstToString(false);
              super.register(arrayConverter, type);
            }
            Converter converter = super.lookup(type);
            return ((String) converter.convert(String.class, value));
          }
        }
        return super.convert(value);
      }

      @SuppressWarnings("rawtypes")
      @Override
      public Object convert(String value, Class clazz) {
        if (clazz.isEnum() && super.lookup(clazz) == null) {
          super.register(new EnumConverter(clazz), clazz);
        }
        return super.convert(value, clazz);
      }

      @SuppressWarnings("rawtypes")
      @Override
      public Object convert(String[] values, Class clazz) {
        if (clazz.isArray() && clazz.getComponentType().isEnum()
            && super.lookup(clazz.getComponentType()) == null) {
          super.register(new EnumConverter(clazz.getComponentType()), clazz.getComponentType());
        }
        return super.convert(values, clazz);
      }

      @SuppressWarnings("rawtypes")
      @Override
      public Object convert(Object value, Class targetType) {
        if (super.lookup(targetType) == null) {
          if (targetType.isEnum()) {
            super.register(new EnumConverter(targetType), targetType);
          } else if (targetType.isArray() && targetType.getComponentType().isEnum()) {
            ArrayConverter arrayConverter =
                new ArrayConverter(targetType, new EnumConverter(targetType.getComponentType()), 0);
            arrayConverter.setOnlyFirstToString(false);
            super.register(arrayConverter, targetType);
          }
        }
        return super.convert(value, targetType);
      }
    };

    DateConverter dateConverter = new DateConverter();
    dateConverter.setPatterns(CommonAttributes.DATE_PATTERNS);
    convertUtilsBean.register(dateConverter, Date.class);
    beanUtils = new BeanUtilsBean(convertUtilsBean);
  }

  /**
   * 不可实例化
   */
  private SettingUtils() {}

  /**
   * 获取系统设置
   * 
   * @return 系统设置
   */
  public static Setting get() {
    Setting setting = new Setting();
    try {
      File commonXmlFile = new ClassPathResource(CommonAttributes.COMMON_CONFIG_XML_PATH).getFile();
      Document document = new SAXReader().read(commonXmlFile);
      List<Element> elements = document.selectNodes("/yxkj/setting");
      for (Element element : elements) {
        String name = element.attributeValue("name");
        String value = element.attributeValue("value");
        try {
          beanUtils.setProperty(setting, name, value);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        }
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
    return setting;
  }

  /**
   * 设置系统设置
   * 
   * @param setting 系统设置
   */
  public static void set(Setting setting) {
    try {
      File CommonXmlFile = new ClassPathResource(CommonAttributes.COMMON_CONFIG_XML_PATH).getFile();
      Document document = new SAXReader().read(CommonXmlFile);
      List<Element> elements = document.selectNodes("/yxkj/setting");
      for (Element element : elements) {
        try {
          String name = element.attributeValue("name");
          String value = beanUtils.getProperty(setting, name);
          Attribute attribute = element.attribute("value");
          attribute.setValue(value);
        } catch (IllegalAccessException e) {
          e.printStackTrace();
        } catch (InvocationTargetException e) {
          e.printStackTrace();
        } catch (NoSuchMethodException e) {
          e.printStackTrace();
        }
      }

      FileOutputStream fileOutputStream = null;
      XMLWriter xmlWriter = null;
      try {
        OutputFormat outputFormat = OutputFormat.createPrettyPrint();
        outputFormat.setEncoding("UTF-8");
        outputFormat.setIndent(true);
        outputFormat.setIndent("	");
        outputFormat.setNewlines(true);
        fileOutputStream = new FileOutputStream(CommonXmlFile);
        xmlWriter = new XMLWriter(fileOutputStream, outputFormat);
        xmlWriter.write(document);
      } catch (Exception e) {
        e.printStackTrace();
      } finally {
        if (xmlWriter != null) {
          try {
            xmlWriter.close();
          } catch (IOException e) {
          }
        }
        IOUtils.closeQuietly(fileOutputStream);
      }

    } catch (Exception e) {
      e.printStackTrace();
    }
  }

}
