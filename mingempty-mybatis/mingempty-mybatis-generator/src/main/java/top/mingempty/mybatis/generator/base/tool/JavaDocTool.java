package top.mingempty.mybatis.generator.base.tool;


/**
 * java doc工具类
 *
 * @author zzhao
 */
public class JavaDocTool {

    public static String generatorClassComment(String comment) {
        String[] remarkLines = comment.split("\n" );
        StringBuilder sb = new StringBuilder(remarkLines[0].replace("\r", "" ));
        if (remarkLines.length > 1) {
            sb.append("\n" );
            sb.append(" * <pre class=\"code\">" );
            for (int i = 1; i < remarkLines.length; i++) {
                sb.append("\n" );
                sb.append(" * " );
                sb.append(remarkLines[i].replace("\r", "" ));
            }
            sb.append("\n" );
            sb.append(" * </pre>" );
        }

        return sb.toString();
    }

    public static String generatorFieldComment(String comment) {
        String[] remarkLines = comment.split("\n" );
        StringBuilder sb = new StringBuilder(remarkLines[0].replace("\r", "" ));
        if (remarkLines.length > 1) {
            sb.append("\n" );
            sb.append("     * <pre class=\"code\">" );
            for (int i = 1; i < remarkLines.length; i++) {
                sb.append("\n" );
                sb.append("     * " );
                sb.append(remarkLines[i].replace("\r", "" ));
            }
            sb.append("\n" );
            sb.append("     * </pre>" );
        }

        return sb.toString();
    }

    public static String generatorSpringDocSchema(String comment) {
        String[] remarkLines = comment.split("\n" );
        StringBuilder sb = new StringBuilder("@Schema(title = \"" );
        sb.append(remarkLines[0].replace("\r", "" ));
        sb.append("\"" );
        if (remarkLines.length > 1) {
            sb.append(", description = \"" );
            for (int i = 1; i < remarkLines.length; i++) {
                sb.append(remarkLines[i].replace("\r", "" ));
                if (i < remarkLines.length - 1) {
                    //说明不是最后一行，做换行拼接
                    sb.append("\" +" );
                    sb.append("\n" );
                    sb.append("\t\t\t\"" );
                }
            }
            sb.append("\"" );
        }
        sb.append(")" );
        return sb.toString();
    }

    public static String generatorSpringDocTag(String comment) {
        String[] remarkLines = comment.split("\n" );
        StringBuilder sb = new StringBuilder("@Tag(name = \"" );
        sb.append(remarkLines[0].replace("\r", "" ));
        sb.append("\"" );
        if (remarkLines.length > 1) {
            sb.append(", description = \"" );
            for (int i = 1; i < remarkLines.length; i++) {
                sb.append(remarkLines[i].replace("\r", "" ));
                if (i < remarkLines.length - 1) {
                    //说明不是最后一行，做换行拼接
                    sb.append("\" +" );
                    sb.append("\n" );
                    sb.append("\t\t\"" );
                }
            }
            sb.append("\"" );
        }
        sb.append(")" );
        return sb.toString();
    }
}
