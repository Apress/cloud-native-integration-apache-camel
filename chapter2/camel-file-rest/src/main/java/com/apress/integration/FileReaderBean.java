package com.apress.integration;

import org.apache.camel.Exchange;
import org.jboss.logging.Logger;
import java.io.File;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;


public class FileReaderBean  {

    private static final Logger LOG = Logger.getLogger(FileReaderBean.class);

    public void listFile(Exchange exchange) throws URISyntaxException {

        File serverDir = new File(getServerDirURI());

        if (serverDir.exists()){

            List<String> fileList = new ArrayList<>();

            for (File file : serverDir.listFiles()){
                fileList.add(file.getName());
            }

            if(!fileList.isEmpty()){
                LOG.info("setting list of files");
                exchange.getMessage().setBody(fileList);
            }else{
                LOG.info("no files found");
            }
        }else{
            LOG.info("no files created yet");
        }

    }

    public static String getServerDirURI() throws URISyntaxException {
        return Paths.get(FileReaderBean.class.getResource("/").toURI()).getParent()+ "/camel-file-rest-dir" ;
    }

}
