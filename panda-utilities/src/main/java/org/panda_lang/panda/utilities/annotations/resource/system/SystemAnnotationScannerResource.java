/*
 * Copyright (c) 2015-2018 Dzikoysk
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.panda_lang.panda.utilities.annotations.resource.system;

import com.google.common.collect.Lists;
import org.panda_lang.panda.utilities.annotations.resource.AnnotationsScannerResource;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Stack;

public class SystemAnnotationScannerResource extends AnnotationsScannerResource<SystemAnnotationScannerFile> {

    private final File file;

    public SystemAnnotationScannerResource(URL url) throws URISyntaxException {
        super(url);
        this.file = new File(url.toURI());

        if (!file.isDirectory() || !file.canRead()) {
            throw new RuntimeException("Cannot use dir " + file);
        }
    }

    @Override
    public Iterator<SystemAnnotationScannerFile> iterator() {
        return new Iterator<SystemAnnotationScannerFile>() {

            Stack<File> stack = new Stack<>();

            {
                stack.addAll(listFiles(file));
            }

            @Override
            public boolean hasNext() {
                return !stack.isEmpty();
            }

            @Override
            public SystemAnnotationScannerFile next() {
                File file = stack.pop();

                if (file.isDirectory()) {
                    stack.addAll(listFiles(file));
                    return next();
                }

                return new SystemAnnotationScannerFile(SystemAnnotationScannerResource.this, file);
            }
        };
    }

    @Override
    public Iterable<SystemAnnotationScannerFile> getFiles() {
        if (file == null || !file.exists()) {
            return Collections.emptyList();
        }

        return this;
    }

    @Override
    public String getPath() {
        if (file == null) {
            return "/NO-SUCH-DIRECTORY/";
        }

        return file.getPath().replace("\\", "/");
    }

    private List<File> listFiles(final File file) {
        File[] files = file.listFiles();

        if (files != null) {
            return Lists.newArrayList(files);
        }

        return Lists.newArrayList();
    }

    @Override
    public String toString() {
        return getPath();
    }

}