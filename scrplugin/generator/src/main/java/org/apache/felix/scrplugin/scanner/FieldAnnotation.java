/*
 * Licensed to the Apache Software Foundation (ASF) under one
 * or more contributor license agreements.  See the NOTICE file
 * distributed with this work for additional information
 * regarding copyright ownership.  The ASF licenses this file
 * to you under the Apache License, Version 2.0 (the
 * "License"); you may not use this file except in compliance
 * with the License.  You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing,
 * software distributed under the License is distributed on an
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 * KIND, either express or implied.  See the License for the
 * specific language governing permissions and limitations
 * under the License.
 */
package org.apache.felix.scrplugin.scanner;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.Map;

import org.apache.felix.scrplugin.SCRDescriptorException;
import org.apache.felix.scrplugin.SCRDescriptorFailureException;

public class FieldAnnotation extends ScannedAnnotation {

    private final Field annotatedField;

    public FieldAnnotation(final String name, final Map<String, Object> values, final Field f) {
        super(name, values);
        this.annotatedField = f;
    }

    public Field getAnnotatedField() {
        return this.annotatedField;
    }

    public Object getAnnotatedFieldValue()
    throws SCRDescriptorFailureException, SCRDescriptorException {
        if ( Modifier.isStatic(annotatedField.getModifiers()) ) {
            try {
                final Object value = annotatedField.get(null);
                return value;
            } catch (final IllegalArgumentException e) {
                throw new SCRDescriptorFailureException("Unable to get initial field value from: " + annotatedField, e);
            } catch (final IllegalAccessException e) {
                throw new SCRDescriptorFailureException("Unable to get initial field value from: " + annotatedField, e);
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return "FieldAnnotationDescription [name=" + name + ", values="
                + values + ", annotatedField=" + annotatedField + "]";
    }
}
