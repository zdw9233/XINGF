package com.uyi.app.ui.home.fragment.model;

import java.util.List;

/**
 * Created by ThinkPad on 2017/3/13.
 */

public class MyServerce {


    private String year;

    public List<ServerceMonth> getServerceMonths() {
        return serverceMonths;
    }

    public void setServerceMonths(List<ServerceMonth> serverceMonths) {
        this.serverceMonths = serverceMonths;
    }

    private List<ServerceMonth> serverceMonths;

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }



    public static class ServerceMonth {

        private String month;
        private String data;

        public String getData() {
            return data;
        }

        public void setData(String data) {
            this.data = data;
        }

        public String getMonth() {
            return month;
        }

        public void setMonth(String month) {
            this.month = month;
        }
    }
}
