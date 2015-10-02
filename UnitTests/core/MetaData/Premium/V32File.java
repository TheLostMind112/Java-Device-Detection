package MetaData.Premium;

import MetaData.FileBase;
import Properties.Constants;
import java.io.IOException;
import org.junit.Test;

/* *********************************************************************
 * This Source Code Form is copyright of 51Degrees Mobile Experts Limited. 
 * Copyright © 2014 51Degrees Mobile Experts Limited, 5 Charlotte Close,
 * Caversham, Reading, Berkshire, United Kingdom RG4 7BY
 * 
 * This Source Code Form is the subject of the following patent 
 * applications, owned by 51Degrees Mobile Experts Limited of 5 Charlotte
 * Close, Caversham, Reading, Berkshire, United Kingdom RG4 7BY: 
 * European Patent Application No. 13192291.6; and
 * United States Patent Application Nos. 14/085,223 and 14/085,301.
 *
 * This Source Code Form is subject to the terms of the Mozilla Public
 * License, v. 2.0.
 * 
 * If a copy of the MPL was not distributed with this file, You can obtain
 * one at http://mozilla.org/MPL/2.0/.
 * 
 * This Source Code Form is "Incompatible With Secondary Licenses", as
 * defined by the Mozilla Public License, v. 2.0.
 * ********************************************************************* */

public class V32File extends FileBase {
    public V32File() {
        super(Constants.PREMIUM_PATTERN_V32);
    }
    
    @Test
    public void PremiumV32File_RetrieveComponents() throws IOException { 
        super.retrieveComponents(); 
    }

    @Test
    public void PremiumV32File_RetrieveProperties() throws IOException { 
        super.retrieveProperties(); 
    }

    @Test
    public void PremiumV32File_RetrieveValues() throws IOException {
        super.retrieveValues(); 
    }

    @Test
    public void PremiumV32File_CheckPropertyCount() { 
        super.checkPropertyCount(130); 
    }

    @Test
    public void PremiumV32File_ValidatePropertiesHaveDescription() throws IOException { 
        super.validatePropertiesHaveDescription(); 
    }    
}