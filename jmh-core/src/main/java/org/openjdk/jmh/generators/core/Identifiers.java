/*
 * Copyright (c) 2005, 2013, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.jmh.generators.core;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class Identifiers {

    private final Map<String, String> collapsedTypes = new HashMap<String, String>();
    private int collapsedIndex = 0;

    private final Set<String> claimedJmhTypes = new HashSet<String>();
    private final Map<String, String> jmhTypes = new HashMap<String, String>();

    public String getJMHtype(String type) {
        String jmhType = jmhTypes.get(type);
        if (jmhType == null) {
            int v = 1;
            do {
                jmhType = getBaseType(type) + "_" + v + "_jmh";
                v++;
            } while (!claimedJmhTypes.add(jmhType));
            jmhTypes.put(type, jmhType);
        }
        return jmhType;
    }

    private String getBaseType(String type) {
        return type.substring(type.lastIndexOf(".") + 1);
    }

    public String collapseTypeName(String e) {
        if (collapsedTypes.containsKey(e)) {
            return collapsedTypes.get(e);
        }

        String[] strings = e.split("\\.");
        String name = strings[strings.length - 1].toLowerCase();

        String collapsedName = name + (collapsedIndex++) + "_";
        collapsedTypes.put(e, collapsedName);
        return collapsedName;
    }

}
