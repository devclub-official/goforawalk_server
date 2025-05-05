package side.flab.goforawalk.app.support

import com.p6spy.engine.logging.Category
import com.p6spy.engine.spy.appender.MessageFormattingStrategy
import org.hibernate.engine.jdbc.internal.FormatStyle

class P6SpyFormatter : MessageFormattingStrategy {
    override fun formatMessage(
        connectionId: Int,
        now: String,
        elapsed: Long,
        category: String,
        prepared: String,
        sql: String?,
        url: String
    ): String {
        return if (sql.isNullOrEmpty() || Category.STATEMENT.name != category) {
            now + "|" + elapsed + "ms|" + category + "|connection " + connectionId + "|" + prepared
        } else {
            val formattedSql = formatSql(sql)
            now + "\n" + formattedSql + "\n"
        }
    }

    private fun formatSql(sql: String): String {
        val formattedSql = if (sql.trim().startsWith("create") || sql.trim().startsWith("alter") ||
            sql.trim().startsWith("comment")) {
            FormatStyle.DDL.formatter.format(sql)
        } else {
            FormatStyle.BASIC.formatter.format(sql)
        }

        return formattedSql.replace("\\n", "\n")
    }
}