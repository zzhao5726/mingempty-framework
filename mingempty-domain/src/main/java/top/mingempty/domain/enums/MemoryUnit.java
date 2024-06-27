package top.mingempty.domain.enums;

import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;

/**
 * 存储大小单位枚举
 *
 * @author zzhao
 */
@Getter
public enum MemoryUnit {

    /**
     * Bytes.
     */
    B("B", 0),
    /**
     * Kilobytes.
     */
    KB("kB", 10),
    /**
     * Megabytes.
     */
    MB("MB", 20),
    /**
     * Gigabytes.
     */
    GB("GB", 30),
    /**
     * Terabytes.
     */
    TB("TB", 40),
    /**
     * Petabytes.
     */
    PB("PB", 50);

    /**
     * 本单位的索引
     */
    @Schema(title = "本单位的索引")
    private final int index;

    /**
     * 字符串格式
     */
    @Schema(title = "字符串格式")
    private final String strFormatter;

    /**
     * Internal constructor
     */
    MemoryUnit(String strFormatter, int index) {
        this.strFormatter = strFormatter;
        this.index = index;
    }

    /**
     * Computes <pre>amount * 2^delta</pre>.
     * <p>
     * The result is always rounded toward zero.
     *
     * @param delta  log<sub>2</sub>(divisor)
     * @param amount dividend
     * @throws ArithmeticException if the result overflows
     */
    private static long doConvert(int delta, long amount) throws ArithmeticException {
        if (delta == 0 || amount == 0) {
            return amount;
        } else if (delta < 0) {
            // Hacker's Delight : 10-1
            long t = amount >> (-delta - 1);
            t >>>= 64 + delta;
            t += amount;
            return t >> -delta;
        } else if (delta >= Long.numberOfLeadingZeros(amount < 0 ? ~amount : amount)) {
            throw new ArithmeticException("Conversion overflows");
        } else {
            return amount << delta;
        }
    }

    /**
     * Converts {@code quantity} in this unit to bytes.
     *
     * @param quantity the quantity
     * @return the quantity in bytes
     */
    public long toBytes(long quantity) {
        return doConvert(index - B.index, quantity);
    }

    /**
     * Converts {@code quantity} in {@code unit} into this unit.
     *
     * @param quantity quantity to convert
     * @param unit     {@code quantity}'s unit
     * @return the quantity in this unit
     */
    public long convert(long quantity, MemoryUnit unit) {
        return doConvert(unit.index - index, quantity);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public String toString() {
        return strFormatter;
    }
}