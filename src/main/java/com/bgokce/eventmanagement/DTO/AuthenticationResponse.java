package com.bgokce.eventmanagement.DTO;

import java.io.Serializable;


public class AuthenticationResponse implements Serializable {

        private final String jwt;
        private final String type;
        private final String tcNo;

        public AuthenticationResponse(String jwt, String type, String tcNo) {
            this.jwt = jwt;
            this.type = type;
            this.tcNo = tcNo;
        }

        public String getJwt() {
            return jwt;
        }

        public String getType() {
            return type;
        }

        public String getTcNo(){
            return tcNo;
        }
}

