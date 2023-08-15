package com.cust_analytic;

import java.time.Instant;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StoreQueryParameters;
import org.apache.kafka.streams.state.HostInfo;
import org.apache.kafka.streams.state.QueryableStoreTypes;
import org.apache.kafka.streams.state.ReadOnlyKeyValueStore;


import io.javalin.Javalin;
import io.javalin.http.Context;

public class RestService {
    private final HostInfo hostInfo;
    private final KafkaStreams streams;

    public RestService(HostInfo hostInfo, KafkaStreams streams) {
        this.hostInfo = hostInfo;
        this.streams = streams;
    }

    public ReadOnlyKeyValueStore<String, ProductSummary> getProductStatsStore() {
        ReadOnlyKeyValueStore<String, ProductSummary> statsStore = streams.store(
                StoreQueryParameters.fromNameAndType(
                        "sales-stats",
                        QueryableStoreTypes.keyValueStore()));
        return statsStore;
    }

    void start() {
        Javalin app = Javalin.create().start(hostInfo.port());
        /** Local window store query: all entries */
        app.get("/sales/range/:key", this::getRange);
    }

    void getRange(Context ctx) {
        List<Map<String, String>> bpms = new ArrayList<>();
        Map<String, String> bpm = new HashMap<>();

        String key = ctx.pathParam("key");

        ProductSummary v = getProductStatsStore().get(key);

        bpm.put("product", key);
        bpm.put("sales", String.valueOf(v.value()));
        bpms.add(bpm);

        // return a JSON response
        ctx.json(bpms);
    }

}
