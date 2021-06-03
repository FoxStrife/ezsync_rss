package br.com.dw2tech.ezsync.repositorio;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonNull;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import java.sql.JDBCType;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Types;
import java.util.Optional;

public class Conversor {

	public static JsonArray resultSetToJsonArray(final ResultSet resultSet) throws SQLException {
		final ResultSetMetaData metaData = resultSet.getMetaData();
		// JsonArray is a Gson built-in class to hold JSON arrays
		final JsonArray jsonArray = new JsonArray();
		while (resultSet.next()) {
			jsonArray.add(resultSetRowToJsonObject(resultSet, metaData));
		}
		return jsonArray;
	}

	private static JsonElement resultSetRowToJsonObject(final ResultSet resultSet, final ResultSetMetaData metaData)
			throws SQLException {
		final int columnCount = metaData.getColumnCount();
		// Every result set row is a JsonObject equivalent
		final JsonObject jsonObject = new JsonObject();
		// JDBC uses 1-based loops
		for (int i = 1; i <= columnCount; i++) {
			jsonObject.add(metaData.getColumnName(i), fieldToJsonElement(resultSet, metaData, i));
		}
		return jsonObject;
	}

	private static JsonElement fieldToJsonElement(final ResultSet resultSet, final ResultSetMetaData metaData,
			final int column) throws SQLException {
		final int columnType = metaData.getColumnType(column);
		final Optional<JsonElement> jsonElement;
		// Process each SQL type mapping a value to a JSON tree equivalent
		switch (columnType) {
		case Types.BIT:
		case Types.TINYINT:
		case Types.SMALLINT:
			throw new UnsupportedOperationException("TODO: " + JDBCType.valueOf(columnType));
		case Types.INTEGER:
			// resultSet.getInt() returns 0 in case of null, so it must be extracted with
			// getObject and cast, then converted to a JsonPrimitive
			jsonElement = Optional.ofNullable((Integer) resultSet.getObject(column)).map(JsonPrimitive::new);
			break;
		case Types.BIGINT:
		case Types.FLOAT:
		case Types.REAL:
		case Types.DOUBLE:
		case Types.NUMERIC:
		case Types.DECIMAL:
		case Types.CHAR:
			// throw new UnsupportedOperationException("TODO: " +
			// JDBCType.valueOf(columnType));
		case Types.VARCHAR:
			jsonElement = Optional.ofNullable(resultSet.getString(column)).map(JsonPrimitive::new);
			break;
		case Types.LONGVARCHAR:
		case Types.DATE:
		case Types.TIME:
		case Types.TIMESTAMP:
		case Types.BINARY:
		case Types.VARBINARY:
		case Types.LONGVARBINARY:
		case Types.NULL:
		case Types.OTHER:
		case Types.JAVA_OBJECT:
		case Types.DISTINCT:
		case Types.STRUCT:
		case Types.ARRAY:
		case Types.BLOB:
		case Types.CLOB:
		case Types.REF:
		case Types.DATALINK:
		case Types.BOOLEAN:
		case Types.ROWID:
		case Types.NCHAR:
		case Types.NVARCHAR:
		case Types.LONGNVARCHAR:
		case Types.NCLOB:
		case Types.SQLXML:
		case Types.REF_CURSOR:
		case Types.TIME_WITH_TIMEZONE:
		case Types.TIMESTAMP_WITH_TIMEZONE:
			// throw new UnsupportedOperationException("TODO: " +
			// JDBCType.valueOf(columnType));
		default:
			throw new UnsupportedOperationException("Unknown type: " + columnType);
		}
		// If the optional value is missing, assume it's a null
		return jsonElement.orElse(JsonNull.INSTANCE);
	}

}
