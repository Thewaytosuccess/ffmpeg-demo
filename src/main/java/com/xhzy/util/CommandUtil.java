package com.xhzy.util;

import lombok.extern.slf4j.Slf4j;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * @author xhzy
 */
@Slf4j
public class CommandUtil {

    public static List<String> execShell(String command) {
        BufferedReader reader = null;
        try {
            Process process = Runtime.getRuntime().exec(command);

            reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
            String line ;
            List<String> result = new ArrayList<>();
            while((line = reader.readLine()) != null){
                log.info("[info]"+line);
                result.add(line);
            }

            reader = new BufferedReader(new InputStreamReader(process.getErrorStream()));
            if((line = reader.readLine()) != null){
                log.error("[error]"+line);
            }

            //wait for result
            int code = process.waitFor();
            if(code != 0){
                log.error("call shell failed.code is "+code);
                return new ArrayList<>();
            }else{
                log.info("[exec success]");
            }

            return result;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if(reader != null){
                try {
                    reader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return new ArrayList<>();
    }

}
