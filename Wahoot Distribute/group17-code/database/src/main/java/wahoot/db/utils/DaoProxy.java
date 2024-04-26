package wahoot.db.utils;

import wahoot.db.dao.DefaultDaoImpl;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class DaoProxy implements InvocationHandler {
    private final Map<String, Method> methodMap = new HashMap<>();

    private final DefaultDaoImpl<?> target;

    <T extends DefaultDaoImpl<?>> DaoProxy(T target) {
        this.target = target;
        for(Method method:target.getClass().getDeclaredMethods()) {
            methodMap.put(method.getName(), method);
        }
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        return methodMap.get(method.getName()).invoke(target, args);
    }
}
