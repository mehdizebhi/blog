package ir.weblog.blog.config;

import java.lang.reflect.InvocationTargetException;
import org.apache.commons.beanutils.BeanUtilsBean;

public class BeanCopyConfig extends BeanUtilsBean {

    @Override
    public void copyProperty(Object bean, String name, Object value) throws IllegalAccessException, InvocationTargetException {
        if (value == null || value.toString().isEmpty()) {
            return;
        }
        super.copyProperty(bean, name, value);
    }

}
