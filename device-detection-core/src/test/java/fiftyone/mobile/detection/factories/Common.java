package fiftyone.mobile.detection.factories;

import fiftyone.mobile.detection.Match;
import fiftyone.mobile.detection.Provider;
import fiftyone.mobile.detection.TrieProvider;
import fiftyone.properties.MatchMethods;

import java.io.IOException;

import static org.junit.Assert.assertEquals;

/**
 * @author jo
 */
class Common {
    static final String TEST_USER_AGENT =
            "Mozilla/5.0 (iPhone; CPU iPhone OS 6_0 like Mac OS X) AppleWebKit/536.26 (KHTML, like Gecko) Version/6.0 Mobile/10A5376e Safari/8536.25 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)";

    /**
     * Tests that a provider can match a couple of properties as a basic test
     * of viability - for Pattern
     * @param provider to test
     * @throws IOException - because provider.match can throw an exception
     */
    static void ensureViableProvider(Provider provider) throws IOException {
        Match match = provider.match(TEST_USER_AGENT);
        System.out.println(TEST_USER_AGENT);
        assertEquals("Match method should be exact", MatchMethods.EXACT, match.getMethod());
        assertEquals("Is a mobile device", true, match.getValues("IsMobile").toBool());
        assertEquals("Screen width should be 640", 640.0, match.getValues("ScreenPixelsWidth").toDouble(),0);
    }

    /**
     * Tests that a provider can match a couple of properties as a basic test
     * of viability - for Trie
     * @param provider the TrieProvider to test
     * @throws Exception - because provider.getDeviceIndex can throw an exception
     */
    static void ensureViableProvider(TrieProvider provider) throws Exception {
        int deviceIndex = provider.getDeviceIndex(TEST_USER_AGENT);
        System.out.println(TEST_USER_AGENT);
        assertEquals("Is a mobile device", "True", provider.getPropertyValue(deviceIndex, "IsMobile"));
        assertEquals("Screen width should be 640", "640", provider.getPropertyValue(deviceIndex, "ScreenPixelsWidth"));
    }
}
