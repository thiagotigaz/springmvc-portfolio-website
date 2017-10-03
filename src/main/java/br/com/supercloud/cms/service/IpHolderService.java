package br.com.supercloud.cms.service;


import org.springframework.stereotype.Service;

@Service
public class IpHolderService {

    private String ip;

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }
}
