package org.infosys.idp.web.uiConfig;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
public class PropertiesController {

    @Autowired
    private UIProperty uiProperty;

    @RequestMapping("properties")
    public UIProperty getProperties() {
        return uiProperty;
    }

}