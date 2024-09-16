package com.salonMaster_springBoot.salonMaster.service;

import org.owasp.html.PolicyFactory;
import org.owasp.html.Sanitizers;
import org.springframework.stereotype.Service;

//Application d'une politique de nettoyage des formats et des liens
@Service
public class XssSanitizerService {
    private static final PolicyFactory POLICY= Sanitizers.FORMATTING.and(Sanitizers.LINKS);
    public String sanitize (String input){
        return POLICY.sanitize(input);
    }


}
