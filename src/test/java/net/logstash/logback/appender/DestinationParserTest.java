/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package net.logstash.logback.appender;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.Test;

public class DestinationParserTest {
    
    @Test
    public void testParse_Single_WithPort() {
        List<Destination> destinations = DestinationParser.parse(" localhost : 2 ", 1);
        
        assertThat(destinations).containsExactly(
                new Destination("localhost", 2)
            );
    }

    @Test
    public void testParse_Single_DefaultPort() {
        List<Destination> destinations = DestinationParser.parse(" localhost ", 1);
        
        assertThat(destinations).containsExactly(
                new Destination("localhost", 1)
            );
    }

    @Test(expected=IllegalArgumentException.class)
    public void testParse_Single_AlphaPort() {
        DestinationParser.parse("localhost:a", 1);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testParse_Single_NegativePort() {
        DestinationParser.parse("localhost:-1", 1);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testParse_Single_ZeroPort() {
        DestinationParser.parse("localhost:0", 1);
    }
    
    @Test
    public void testParse_Multiple() {
        List<Destination> destinations = DestinationParser.parse("localhost:2, localhost, localhost : 5 ", 1);
        
        assertThat(destinations).containsExactly(
                new Destination("localhost", 2),
                new Destination("localhost", 1),
                new Destination("localhost", 5)
            );
    }

    @Test(expected=IllegalArgumentException.class)
    public void testParse_Multiple_AlphaPort() {
        DestinationParser.parse("localhost:10000, localhost:a", 1);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testParse_Multiple_NegativePort() {
        DestinationParser.parse("localhost:10000, localhost:-1", 1);
    }
    
    @Test(expected=IllegalArgumentException.class)
    public void testParse_Multiple_ZeroPort() {
        DestinationParser.parse("localhost:10000, localhost:0", 1);
    }
}
