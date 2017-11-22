package org.shunt;


import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.apache.commons.collections.CollectionUtils;

import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

/**
 * @author GuoXinYuan
 *
 */
public class TagMetadataRule extends WeightMetadataRule {

    @Override
    public Server choose(Object key) {
        List<Server> serverList = this.getPredicate().getEligibleServers(this.getLoadBalancer().getAllServers(), key);
        if (CollectionUtils.isEmpty(serverList)) {
            return null;
        }

        ArrayList<Server> tagFilteredServerList = new ArrayList<>();
        for (Server server : serverList) {
            DiscoveryEnabledServer tagFiltered = (DiscoveryEnabledServer) server;
            final Map<String, String> metadata = tagFiltered.getInstanceInfo().getMetadata();
            if (TagHolder.checkMap(metadata)) {
                tagFilteredServerList.add(tagFiltered);
            }
        }
        if (CollectionUtils.isNotEmpty(tagFilteredServerList))
            return tagFilteredServerList.get(new Random().nextInt(tagFilteredServerList.size()));

        return super.choose(key);
    }
}
