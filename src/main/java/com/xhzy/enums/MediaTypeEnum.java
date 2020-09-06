package com.xhzy.enums;

import lombok.Getter;

/**
 * @author xhzy
 */

@Getter
public enum MediaTypeEnum {

    /**
     * 媒资类型
     */
    VIDEO("video","视频"),
    AUDIO("audio","音频"),
    ;
    private final String key;

    private final String value;

    MediaTypeEnum(String key, String value){
        this.key = key;
        this.value = value;
    }

    public static String getValue(String key){
        for(MediaTypeEnum e: MediaTypeEnum.values()){
            if(e.key.equals(key)){
                return e.value;
            }
        }
        return null;
    }
}
