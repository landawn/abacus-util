/**
 * Copyright (C) 2006-2009 Dustin Sallings
 * Copyright (C) 2009-2011 Couchbase, Inc.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALING
 * IN THE SOFTWARE.
 */

package com.landawn.abacus.util;

import java.net.InetSocketAddress;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

/**
 * The Class AddrUtil.
 */
public final class AddrUtil {

    /** The Constant URL_SEPERATOR. */
    private static final String URL_SEPERATOR = "(?:\\s|,)+";

    /** The Constant URL_SPLITTER. */
    private static final Splitter URL_SPLITTER = Splitter.pattern(URL_SEPERATOR).omitEmptyStrings().trimResults();

    /**
     * Instantiates a new addr util.
     */
    private AddrUtil() {
        // singleton
    }

    /**
     * Split a string containing whitespace or comma separated host or IP addresses and port numbers of the form
     * "host:port host2:port" or "host:port, host2:port" into a List of server.
     *
     * @param servers
     * @return
     */
    public static List<String> getServerList(String servers) {
        final List<String> serverList = URL_SPLITTER.split(servers);

        if (N.isNullOrEmpty(serverList)) {
            throw new IllegalArgumentException("Invlid serverUrl: " + servers);
        }

        return serverList;
    }

    /**
     * Split a string containing whitespace or comma separated host or IP addresses and port numbers of the form
     * "host:port host2:port" or "host:port, host2:port" into a List of InetSocketAddress instances suitable for
     * instantiating a MemcachedClient.
     * 
     * Note that colon-delimited IPv6 is also supported. For example: ::1:11211
     *
     * @param servers
     * @return
     */
    public static List<InetSocketAddress> getAddressList(String servers) {
        if (N.isNullOrEmpty(servers)) {
            throw new IllegalArgumentException("Null or empty host list");
        }

        String[] hoststuffs = servers.split(URL_SEPERATOR);

        if (N.isNullOrEmpty(hoststuffs)) {
            throw new IllegalArgumentException("Invlid addresses: " + servers);
        }

        final List<InetSocketAddress> addrs = new ArrayList<>();

        for (String hoststuff : hoststuffs) {
            if (hoststuff.equals("")) {
                continue;
            }

            String[] strs = hoststuff.split(":");

            if (strs.length < 1) {
                throw new IllegalArgumentException("Invalid server ``" + hoststuff + "'' in list:  " + servers);
            }

            String hostPart = strs[0];
            String portNum = strs[1];

            addrs.add(new InetSocketAddress(hostPart, Integer.parseInt(portNum)));
        }
        assert !addrs.isEmpty() : "No addrs found";

        return addrs;
    }

    /**
     * Gets the address list.
     *
     * @param servers
     * @return
     */
    public static List<InetSocketAddress> getAddressList(List<String> servers) {
        List<InetSocketAddress> addrs = new ArrayList<>(servers.size());

        for (String url : servers) {
            String[] strs = url.split(":");

            if (strs.length < 1) {
                throw new IllegalArgumentException("Invalid server ``" + url + "'' in list:  " + servers);
            }

            String hostPart = strs[0];
            String portNum = strs[1];

            addrs.add(new InetSocketAddress(hostPart, Integer.parseInt(portNum)));
        }

        if (addrs.isEmpty()) {
            // servers was passed in empty, and shouldn't have been
            throw new IllegalArgumentException("servers cannot be empty");
        }

        return addrs;
    }

    /**
     * Gets the address from URL.
     *
     * @param url
     * @return
     */
    public static InetSocketAddress getAddressFromURL(URL url) {
        return new InetSocketAddress(url.getHost(), url.getPort());
    }

    /**
     * Gets the address list from URL.
     *
     * @param urls
     * @return
     */
    public static List<InetSocketAddress> getAddressListFromURL(List<URL> urls) {
        List<InetSocketAddress> addrs = new ArrayList<>(urls.size());

        for (URL server : urls) {
            addrs.add(new InetSocketAddress(server.getHost(), server.getPort()));
        }

        return addrs;
    }
}
