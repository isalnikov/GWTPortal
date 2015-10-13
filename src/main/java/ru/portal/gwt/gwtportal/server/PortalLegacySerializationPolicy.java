/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.portal.gwt.gwtportal.server;

import com.google.gwt.user.client.rpc.SerializationException;
import com.google.gwt.user.server.rpc.SerializationPolicy;
import com.google.gwt.user.server.rpc.impl.LegacySerializationPolicy;
import com.google.gwt.user.server.rpc.impl.TypeNameObfuscator;
import java.io.Serializable;

/**
 *
 * @author Igor Salnikov 
 */
public class PortalLegacySerializationPolicy extends SerializationPolicy implements TypeNameObfuscator {

    LegacySerializationPolicy legacy = LegacySerializationPolicy.getInstance();

    @Override
    public boolean shouldDeserializeFields(Class<?> clazz) {
        if (Serializable.class.isAssignableFrom(clazz)) {
            return true;
        }
        return legacy.shouldDeserializeFields(clazz);
    }

    @Override
    public boolean shouldSerializeFields(Class<?> clazz) {
       return legacy.shouldSerializeFields(clazz);
    }

    @Override
    public void validateDeserialize(Class<?> clazz) throws SerializationException {
        legacy.validateDeserialize(clazz);
    }

    @Override
    public void validateSerialize(Class<?> clazz) throws SerializationException {
        legacy.validateSerialize(clazz);
    }

    @Override
    public String getClassNameForTypeId(String id) throws SerializationException {
        return legacy.getClassNameForTypeId(id);
    }

    @Override
    public String getTypeIdForClass(Class<?> clazz) throws SerializationException {
        return legacy.getTypeIdForClass(clazz);
    }

    
    
}
