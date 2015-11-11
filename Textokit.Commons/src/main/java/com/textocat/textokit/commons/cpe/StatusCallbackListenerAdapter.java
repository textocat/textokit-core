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


package com.textocat.textokit.commons.cpe;

import org.apache.uima.cas.CAS;
import org.apache.uima.collection.EntityProcessStatus;
import org.apache.uima.collection.StatusCallbackListener;

/**
 * @author Rinat Gareev
 */
public class StatusCallbackListenerAdapter implements StatusCallbackListener {

    /**
     * {@inheritDoc}
     */
    @Override
    public void initializationComplete() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void batchProcessComplete() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void collectionProcessComplete() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void paused() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void resumed() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void aborted() {
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public void entityProcessComplete(CAS aCas, EntityProcessStatus aStatus) {
    }

}