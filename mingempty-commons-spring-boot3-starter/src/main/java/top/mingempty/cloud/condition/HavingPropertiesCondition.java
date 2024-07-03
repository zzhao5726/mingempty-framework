package top.mingempty.cloud.condition;


import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationAttributes;
import org.springframework.core.annotation.Order;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.util.MultiValueMap;
import org.springframework.util.StringUtils;
import top.mingempty.cloud.util.EnvironmentUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;


/**
 * 通过配置文件中指定的前缀进行注入bean
 * <p>
 * bean注入时，若注解{@link ConditionalOnHavingProperties}存在，则进行判断，判断配置文件中是否有指定的前缀，若存在，则允许注入，反之亦然。
 */
@Order(Ordered.HIGHEST_PRECEDENCE)
public class HavingPropertiesCondition implements Condition {

    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {

        List<AnnotationAttributes> allAnnotationAttributes = annotationAttributesFromMultiValueMap(
                Objects.requireNonNull(metadata.getAllAnnotationAttributes(ConditionalOnHavingProperties.class.getName())));

        for (AnnotationAttributes allAnnotationAttribute : allAnnotationAttributes) {
            boolean matchIfMissing = allAnnotationAttribute.getBoolean("matchIfMissing");

            if (matchIfMissing) {
                return matchIfMissing;
            }

            String prefix = allAnnotationAttribute.getString("prefix").trim();

            if (StringUtils.hasText(prefix) && !prefix.endsWith(".")) {
                prefix = prefix + ".";
            }
            String[] valueS = (String[]) allAnnotationAttribute.get("value");
            if (valueS == null || valueS.length == 0) {
                continue;
            }
            String finalPrefix = prefix;
            if (Arrays.stream(valueS).anyMatch(value
                            -> !EnvironmentUtil.startsWith(
                            finalPrefix.concat(value.endsWith(".")
                                    ? value : value.concat("."))))) {
                //存在是则注入
                return Boolean.FALSE;
            }
        }
        //不存在是不注入
        return Boolean.TRUE;

    }


    private List<AnnotationAttributes> annotationAttributesFromMultiValueMap(MultiValueMap<String, Object> multiValueMap) {
        List<Map<String, Object>> maps = new ArrayList<>();
        multiValueMap.forEach((key, value) -> {
            for (int i = 0; i < value.size(); i++) {
                Map<String, Object> map;
                if (i < maps.size()) {
                    map = maps.get(i);
                } else {
                    map = new HashMap<>();
                    maps.add(map);
                }
                map.put(key, value.get(i));
            }
        });
        List<AnnotationAttributes> annotationAttributes = new ArrayList<>(maps.size());
        for (Map<String, Object> map : maps) {
            annotationAttributes.add(AnnotationAttributes.fromMap(map));
        }
        return annotationAttributes;
    }


}
