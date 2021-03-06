/*
 * Copyright 2007 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.jsefa.common.validator;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.jsefa.ObjectPathElement;

/**
 * A validation error.
 * 
 * @author Norman Lahme-Huetig
 */
public final class ValidationError implements Serializable {

	private static final long serialVersionUID = 1L;

	private final String errorCode;

    private final String errorText;

    private final List<ObjectPathElement> relativeObjectPath;

    /**
     * Constructs a new <code>ValidationError</code>.
     * @param errorCode the error code
     * @param errorText the error text
     * @param relativeObjectPathElements the relative path to the field this error refers to
     * @return a <code>ValidationError</code>
     */
    public static ValidationError create(String errorCode, String errorText,
            ObjectPathElement... relativeObjectPathElements) {
        List<ObjectPathElement> relativeObjectPath = new ArrayList<ObjectPathElement>();
        for (ObjectPathElement objectPathElement : relativeObjectPathElements) {
            relativeObjectPath.add(objectPathElement);
        }
        return new ValidationError(errorCode, errorText, relativeObjectPath);
    }

    private ValidationError(String errorCode, String errorText, List<ObjectPathElement> relativeObjectPath) {
        this.errorCode = errorCode;
        this.errorText = errorText;
        this.relativeObjectPath = relativeObjectPath;
    }

    /**
     * @return the error code
     */
    public String getErrorCode() {
        return errorCode;
    }

    /**
     * @return the error text
     */
    public String getErrorText() {
        return errorText;
    }

    /**
     * @return the relative object path
     */
    public List<ObjectPathElement> getRelativeObjectPath() {
        return Collections.unmodifiableList(relativeObjectPath);
    }

}
