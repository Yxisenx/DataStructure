package cn.onecolour.util;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.*;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.util.StdDateFormat;

import javax.annotation.Nonnull;
import java.io.IOException;
import java.text.ParseException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.*;

/**
 * @author yang
 * @date 2023/2/8
 * @description
 */
public class JsonUtils {
    private final static ZoneId ZONE_ID = ZoneId.of("Asia/Shanghai");

    private final static String SERIALIZE_FAILED_MSG = "Serialize object to json failed.";
    private final static String DESERIALIZE_FAILED_MSG = "Deserialize json to object failed.";
    private final static String DESERIALIZE_ARRAY_FAILED_MSG = "Deserialize json to list failed.";
    private final static String DESERIALIZE_JSON_NODE_FAILED_MSG = "Deserialize json to tree failed.";
    private final static StdDateFormat stdDateFormat = new StdDateFormat().withTimeZone(TimeZone.getTimeZone(ZONE_ID)).withColonInTimeZone(true);

    static {
        objectMapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_NULL)
                .enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT)
                .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)
                .disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, SerializationFeature.FAIL_ON_EMPTY_BEANS)
                .setDateFormat(stdDateFormat)
                .registerModule(getLocalDateTimeSerializeModule());
    }

    private final static ObjectMapper objectMapper;

    private static Module getLocalDateTimeSerializeModule() {
        SimpleModule module = new SimpleModule();
        JsonSerializer<LocalDateTime> localDateTimeJsonSerializer = new JsonSerializer<LocalDateTime>() {
            @Override
            public void serialize(LocalDateTime value, JsonGenerator gen, SerializerProvider serializers) throws IOException {
                long timestamp = 0;
                if (value != null) {
                    timestamp = value.toInstant(ZoneOffset.of("+8")).toEpochMilli();
                }
                gen.writeString(stdDateFormat.format(timestamp));
            }
        };
        JsonDeserializer<LocalDateTime> localDateTimeJsonDeserializer = new JsonDeserializer<LocalDateTime>() {
            @Override
            public LocalDateTime deserialize(JsonParser parser, DeserializationContext context) {
                Date parse = null;
                try {
                    String timeStr = parser.getText().trim();
                    parse = stdDateFormat.parse(timeStr);
                } catch (IOException | ParseException e) {
                    throw new RuntimeException(e);
                }
                return LocalDateTime.ofInstant(parse.toInstant(), ZONE_ID);
            }
        };
        KeyDeserializer localDateTimeKeyDeserializer = new KeyDeserializer() {
            @Override
            public Object deserializeKey(String key, DeserializationContext context) {
                LocalDateTime localDateTime;
                try {
                    localDateTime = LocalDateTime.ofInstant(stdDateFormat.parse(key).toInstant(), ZONE_ID);
                } catch (ParseException e) {
                    throw new RuntimeException(e);
                }
                return localDateTime;
            }
        };
        module.addSerializer(LocalDateTime.class, localDateTimeJsonSerializer);
        module.addDeserializer(LocalDateTime.class, localDateTimeJsonDeserializer);
        module.addKeySerializer(LocalDateTime.class, localDateTimeJsonSerializer);
        module.addKeyDeserializer(LocalDateTime.class, localDateTimeKeyDeserializer);
        return module;
    }

    public static <T> String toJsonString(T obj) {
        String jsonString;
        try {
            jsonString = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(SERIALIZE_FAILED_MSG, e);
        }
        return jsonString;
    }

    public static <T> T parseObject(TypeReference<T> typeReference, String jsonStr) {
        T obj;
        try {
            obj = objectMapper.readValue(jsonStr, typeReference);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(DESERIALIZE_FAILED_MSG, e);
        }
        return obj;
    }

    public static <T> T parseObject(String jsonStr, Class<T> clazz) {
        T obj;
        try {
            obj = objectMapper.readValue(jsonStr, clazz);
        } catch (JsonProcessingException e) {

            throw new RuntimeException(DESERIALIZE_FAILED_MSG, e);
        }
        return obj;
    }

    public static <T> List<T> parseArray(String jsonStr, Class<T> clazz) {
        List<T> list = Collections.emptyList();
        try {
            JsonNode jsonNode = objectMapper.readTree(jsonStr);
            if (jsonNode.isArray()) {
                int size = jsonNode.size();
                list = new ArrayList<>(size);
                for (int i = 0; i < size; i++) {
                    JsonNode node = jsonNode.get(i);
                    list.add(objectMapper.treeToValue(node, clazz));
                }
            }
        } catch (JsonProcessingException e) {
            throw new RuntimeException(DESERIALIZE_ARRAY_FAILED_MSG, e);
        }
        return list;
    }


    public static JsonNode parseTree(@Nonnull String jsonStr) {
        try {
            return objectMapper.readTree(jsonStr);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(DESERIALIZE_JSON_NODE_FAILED_MSG, e);
        }
    }


}
