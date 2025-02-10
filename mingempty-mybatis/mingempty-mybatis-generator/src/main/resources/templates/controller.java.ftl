package ${package.Controller};

<#if importColtrollerPackages??>
<#list importColtrollerPackages as pkg>
import ${pkg};
</#list>
</#if>
import org.springframework.web.bind.annotation.RequestMapping;
<#if restControllerStyle>
import org.springframework.web.bind.annotation.RestController;
<#else>
import org.springframework.stereotype.Controller;
</#if>
<#if superControllerClassPackage??>
import ${package.Entity}.${entity};
import ${package.Service}.${table.serviceName};
import ${superControllerClassPackage};
</#if>

/**
 * <p>
 * ${table.comment!}
 * <br>
 * 前端控制器
 * </p>
 *
 * @author ${author}
 * @since ${date}
 */
<#if coltrollerClassAnnotations??>
<#list coltrollerClassAnnotations as pkg>
${pkg}
</#list>
</#if>
<#if restControllerStyle>
@RestController
<#else>
@Controller
</#if>
@RequestMapping("<#if package.ModuleName?? && package.ModuleName != "">/${package.ModuleName}</#if>/<#if controllerMappingHyphenStyle>${controllerMappingHyphen}<#else>${table.entityPath}</#if>")
<#if kotlin>
class ${table.controllerName}<#if superControllerClass??> : ${superControllerClass}<${table.serviceName}, ${entity}>()</#if>
<#else>
<#if superControllerClass??>
public class ${table.controllerName} extends ${superControllerClass}<${table.serviceName}, ${entity}> {
<#else>
public class ${table.controllerName} {
</#if>

}
</#if>
