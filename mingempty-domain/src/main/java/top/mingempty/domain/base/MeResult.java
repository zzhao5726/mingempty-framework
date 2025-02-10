package top.mingempty.domain.base;


/**
 * 定义返回数据结构
 * <p>
 * 系统内置编码组成介绍：
 * 1.G-D-xxxxx  系统接口内置返回信息  如接口调用成功、参数校验失败等（{@link top.mingempty.domain.enums.DefaultResultEnum}）
 * 2.G-E-xxx-xxxxx  系统内置异常返回信息  xxx：系统组件编码  xxxxx：系统内异常编码（{@link top.mingempty.commons.exception.MeBadException}）
 */
public interface MeResult {
    String getCode();

    String getMessage();
}