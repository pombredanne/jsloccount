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
package org.jsloc;

import java.io.File;

import org.jsloc.logger.Logger;
import org.jsloc.output.AbstractOutputFactory;
import org.jsloc.project.ProjectStatistics;
import org.jsloc.resources.Resource;

/**
 * 
 * 
 * @author Vassilios Karakoidas (bkarak@aueb.gr)
 *
 */
public class Main {
    
    private static void help() {
        Logger logger = Configuration.getInstance().getLogger();
        
        logger.message("JSLoCcount - Vassilios Karakoidas (bkarak@aueb.gr)\n");
        logger.message("usage:\n");
        logger.message("java -jar jsloccount.jar <directory>\n");
        
        logger.message("Supported Languages:\n");
        for ( Resource r : Resource.values() ) {
            logger.message("* " + r.toString());
        }        
    }

    public static void main(String[] args) {
        Logger logger = Configuration.getInstance().getLogger();
        
        if(args.length == 2) {
            if(args[0].compareToIgnoreCase("-d") == 0) {
                File f = new File(args[1]);
                if(f.exists() && f.isDirectory()) {
                    File[] files = f.listFiles();
                    if (files == null) {
                        return;
                    }

                    for ( File ff : files ) {
                        if(ff.isDirectory()) {
                            main(new String[] { ff.getAbsolutePath() });
                        }
                    }
                }

                return;
            }
        }
        
        if(args.length == 1) {        
            File f = new File(args[0]);
            if(f.exists() && f.isDirectory()) {
                ProjectStatistics ps = new ProjectStatistics(f);
                AbstractOutputFactory.getStandardOutput(ps).produce();
            } else {
                logger.error("ERROR: " + f.getAbsolutePath() + " is not a directory");
            }
            
            return;
        }
        
        help();
    }
}
