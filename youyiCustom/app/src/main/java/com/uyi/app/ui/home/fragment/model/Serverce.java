package com.uyi.app.ui.home.fragment.model;

import java.util.List;

/**
 * Created by ThinkPad on 2017/3/13.
 */

public class Serverce {

    /**
     * executeBegin : 2017-03-08 11:34:16.0
     * entryName : [{"name":"血糖数据检测(0),血糖数据检测(0)","status":0}]
     */

    private String executeBegin;
    private List<EntryNameBean> entryName;

    public String getExecuteBegin() {
        return executeBegin;
    }

    public void setExecuteBegin(String executeBegin) {
        this.executeBegin = executeBegin;
    }

    public List<EntryNameBean> getEntryName() {
        return entryName;
    }

    public void setEntryName(List<EntryNameBean> entryName) {
        this.entryName = entryName;
    }

    public static class EntryNameBean {
        /**
         * name : 血糖数据检测(0),血糖数据检测(0)
         * status : 0
         */

        private String name;
        private int status;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getStatus() {
            return status;
        }

        public void setStatus(int status) {
            this.status = status;
        }
    }
}
