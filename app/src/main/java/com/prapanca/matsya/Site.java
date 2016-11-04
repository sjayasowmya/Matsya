package com.prapanca.matsya;

/**
 * Created by jayasowmya on 4/11/16.
 */

public class Site {
        private String sitenumber, place, time;

        public Site() {
        }

        public Site(String sitenumber, String place, String time) {
            this.sitenumber = sitenumber;
            this.place = place;
            this.time = time;
        }

        public String getSitenumber() {
            return sitenumber;
        }

        public void setSitenumber(String sitenumber) {
            this.sitenumber = sitenumber;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }

        public String getPlace() {
            return place;
        }

        public void setPlace(String place) {
            this.place = place;
        }
    }

