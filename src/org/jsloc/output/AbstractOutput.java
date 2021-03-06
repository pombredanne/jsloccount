/*
# Copyright (c) 2013, Vassilios Karakoidas (vassilios.karakoidas@gmail.com)
 All rights reserved.
 Redistribution and use in source and binary forms, with or without
 modification, are permitted provided that the following conditions are met:
    * Redistributions of source code must retain the above copyright

    * Redistributions in binary form must reproduce the above copyright
      notice, this list of conditions and the following disclaimer in the
      documentation and/or other materials provided with the distribution.
    * The names of its contributors may not be used to endorse or promote products
      derived from this software without specific prior written permission.

 THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS" AND
 ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED
 WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
 DISCLAIMED. IN NO EVENT SHALL Vassilios Karakoidas BE LIABLE FOR ANY
 DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES
 (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES;
 LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
 ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT
 (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS
 SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.
*/
package org.jsloc.output;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.jsloc.project.ProjectStatistics;
import org.jsloc.resources.Resource;

/**
 *
 * 
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 *
 */
public abstract class AbstractOutput {
    protected ProjectStatistics projectStatistics;
    protected ArrayList<ResourceValue> sortedLoC;
    protected ArrayList<ResourceValue> sortedFiles;
     
    protected AbstractOutput(ProjectStatistics ps) {
        this.projectStatistics = ps;
        Resource[] resources = ps.getResources();
        
        // sort by loc 
        sortedLoC = new ArrayList<ResourceValue>();
        sortedFiles = new ArrayList<ResourceValue>();
        for ( Resource r : resources ) {
            if(r.isText()) {
                sortedLoC.add(new ResourceValue(r, ps.getLOC(r)));
            }
            sortedFiles.add(new ResourceValue(r, ps.getFileCount(r)));
        }
        Collections.sort(sortedLoC, new ResourceValue());
        Collections.sort(sortedFiles, new ResourceValue());
    }
    
    public abstract void produce();
}

class ResourceValue implements Comparator<ResourceValue> {
    private Resource resource;
    private long value;
    
    // Used only for comparator
    ResourceValue() {
        this.resource = null;
        this.value = 0;
    }
    
    ResourceValue(Resource l, long value) {
        this.resource = l;
        this.value = value;
    }
    
    public Resource getResource() {
        return resource;
    }
    
    public long getValue() {
        return value;
    }

    @Override
    public int compare(ResourceValue lvalone, ResourceValue lvaltwo) {
        return (new Long(lvaltwo.value)).compareTo(lvalone.value);
    }
}