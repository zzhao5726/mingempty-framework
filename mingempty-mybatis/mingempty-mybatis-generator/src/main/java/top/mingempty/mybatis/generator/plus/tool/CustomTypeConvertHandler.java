package top.mingempty.mybatis.generator.plus.tool;

import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.po.TableField;
import com.baomidou.mybatisplus.generator.config.rules.DbColumnType;
import com.baomidou.mybatisplus.generator.config.rules.IColumnType;
import com.baomidou.mybatisplus.generator.type.ITypeConvertHandler;
import com.baomidou.mybatisplus.generator.type.TypeRegistry;

public class CustomTypeConvertHandler implements ITypeConvertHandler {

    @Override
    public IColumnType convert(GlobalConfig globalConfig, TypeRegistry typeRegistry, TableField.MetaInfo metaInfo) {
        IColumnType columnType = typeRegistry.getColumnType(metaInfo);
        if (DbColumnType.DOUBLE.equals(columnType) || DbColumnType.FLOAT.equals(columnType)) {
            return DbColumnType.BIG_DECIMAL;
        } else if (DbColumnType.BIG_INTEGER.equals(columnType)) {
            return DbColumnType.LONG;
        }
        return columnType;
    }
}
