package com.example.as.uestc.Answer.beans;

import java.io.Serializable;

/**
 *
 * Created by as on 2017/11/5.
 */

public class Info implements Serializable{
        private String orderNum;
        private String classID;
        private String academy;
        private String cover;
        //private List<String> images;

        public String getClassID()
        {
            return this.classID;
        }

        public String getAcademy()
        {
            return this.academy;
        }

        public String getCover()
        {
            return this.cover;
        }

        /*public List<String> getImages()
        {
            return this.images;
        }
        */
        public String getOrderNum()
        {
            return this.orderNum;
        }
}
