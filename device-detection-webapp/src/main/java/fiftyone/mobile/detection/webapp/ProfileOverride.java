/* *********************************************************************
 * This Source Code Form is copyright of 51Degrees Mobile Experts Limited. 
 * Copyright © 2017 51Degrees Mobile Experts Limited, 5 Charlotte Close,
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
package fiftyone.mobile.detection.webapp;

import fiftyone.mobile.detection.Match;
import fiftyone.mobile.detection.entities.Values;
import java.io.IOException;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Used with the 51Degrees client side overrides JavaScript snippet.
 * <p>
 * Client side overrides provides improved device detection for devices like 
 * iPhone where the User-Agent on its own is not enough to determine the 
 * generation of the device, or with features that can only be retrieved using 
 * JavaScript like the screen orientation.
 * <p>
 * Overridden features are available upon upon the second request. This script 
 * is only available in the Enterprise data file.
 * <p>
 * You should not access objects of this class directly or instantiate new 
 * objects using this class as they are part of the internal logic.
 */
class ProfileOverride {

    final private static Logger logger = 
            LoggerFactory.getLogger(ProfileOverride.class);
    private static final String COOKIE_NAME = "51D_ProfileIds";

    /**
     * @param request current HttpServletRequest.
     * @return the value of the profile IDs cookie from the client.
     */
    private static String getCookieValue(HttpServletRequest request) {
        if (request.getCookies() != null) {
            for(Cookie cookie : request.getCookies()) {
                if (COOKIE_NAME.equalsIgnoreCase(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null;  
    }
    
    /**
     * Determines if the request includes override information from client
     * JavaScript that will impact the results provided.
     * 
     * @param request current HttpServletRequest.
     * @return true if overrides are available.
     */
    static boolean hasOverrides(HttpServletRequest request) {
        return getCookieValue(request) != null;
    }
    
    /**
     * Returns the JavaScript for profile override for the requesting device.
     * 
     * @param request current HttpServletRequest.
     * @return JavaScript as string.
     */
    static String getJavaScript(HttpServletRequest request) throws IOException {
        Match match = WebProvider.getMatch(request);
        if (match != null) {
            Values javascript = match.getValues("JavascriptHardwareProfile");
            if (javascript != null) {
                StringBuilder sb = new StringBuilder(
                        "function FODPO() {{ var profileIds = new Array(); ");
                for(String snippet : javascript.toStringArray()) {
                    sb.append(snippet).append("\r");
                }
                sb.append("document.cookie = \"51D_ProfileIds=\" "
                        + "+ profileIds.join(\"|\"); }}");
                return sb.toString();
            }
        }
        return null;
    }

    static void override(HttpServletRequest request, Match match) 
                                                            throws IOException {
        String cookieValue = getCookieValue(request);
        if (cookieValue != null) {
            for(String profileId : cookieValue.split("\\|")) {
                try {
                    match.updateProfile(Integer.valueOf(profileId));
                }
                catch(NumberFormatException ex) {
                    logger.debug(String.format(
                            "'%s' cookie contained invalid values '%s'",
                            COOKIE_NAME,
                            cookieValue),
                            ex);
                }
            }
        }
    }
}
