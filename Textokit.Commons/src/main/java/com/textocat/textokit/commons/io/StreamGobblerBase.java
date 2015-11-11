/*
 *    Copyright 2015 Textocat
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */


package com.textocat.textokit.commons.io;

import java.io.InputStream;

/**
 * Borrowed from {@link TreeTaggerWrapper}
 *
 * @author Rinat Gareev
 */
public abstract class StreamGobblerBase implements Runnable {

    private final InputStream in;
    private boolean done = false;
    private Throwable exception;
    public StreamGobblerBase(InputStream in) {
        this.in = in;
    }

    public static StreamGobblerBase toSystemOut(InputStream in) {
        return new StreamGobblerBase(in) {
            @Override
            protected void write(String str) {
                System.out.print(str);
            }

            @Override
            protected void onDone() {
                System.out.println();
            }
        };
    }

    public void done() {
        done = true;
    }

    @Override
    public void run() {
        byte[] buffer = new byte[1024];
        try {
            while (!done) {
                while (in.available() > 0) {
                    int br = in.read(buffer, 0, Math.min(buffer.length, in.available()));
                    if (br > 0) {
                        write(new String(buffer, 0, br));
                    }
                }
                Thread.sleep(100);
            }
            onDone();
        } catch (final Throwable e) {
            exception = e;
        }
    }

    public Throwable getException() {
        return exception;
    }

    protected abstract void write(String str);

    protected abstract void onDone();
}
