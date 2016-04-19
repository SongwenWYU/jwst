package com.jwst.manage.util;

import java.lang.reflect.Type;
import java.util.Date;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import com.jwst.manage.model.AuditWrapper;

public class SessionOutputSerializer implements JsonSerializer<Object> {
    @Override
    public JsonElement serialize(Object src, Type typeOfSrc, JsonSerializationContext context) {
        JsonObject object = new JsonObject();
        if (typeOfSrc.equals(AuditWrapper.class)) {
            AuditWrapper auditWrapper = (AuditWrapper) src;
            object.addProperty("session_id", auditWrapper.getSessionOutput().getSessionId());
            object.addProperty("instance_id", auditWrapper.getSessionOutput().getInstanceId());
            object.addProperty("host_id", auditWrapper.getSessionOutput().getId());
            object.addProperty("host", auditWrapper.getSessionOutput().getDisplayLabel());
            object.addProperty("output", auditWrapper.getSessionOutput().getOutput().toString());
            object.addProperty("timestamp", new Date().getTime());
        }
        return object;
    }
}
