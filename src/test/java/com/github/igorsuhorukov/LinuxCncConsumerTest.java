package com.github.igorsuhorukov;

import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.component.mock.MockEndpoint;
import org.apache.camel.test.junit4.CamelTestSupport;
import org.junit.Test;

public class LinuxCncConsumerTest extends CamelTestSupport {

    @Test
    public void testLinuxCnc() throws Exception {
        MockEndpoint mock = getMockEndpoint("mock:result");
        mock.expectedMinimumMessageCount(1);       
        sendBody("direct:foo","get abs_act_pos");
        assertMockEndpointsSatisfied();
    }

    @Override
    protected RouteBuilder createRouteBuilder() throws Exception {
        return new RouteBuilder() {
            public void configure() {
                from("direct:foo")
                  .to("gcode:?host=beaglebone.local&port=5007&autoHomeAxisCount=4")
                  .to("mock:result");
            }
        };
    }
}
