package com.github.fabienrenaud.jjb.databind;

import com.alibaba.fastjson2.JSON;
import com.badlogic.gdx.utils.JsonWriter;
import com.bluelinelabs.logansquare.LoganSquare;
import com.github.fabienrenaud.jjb.JsonBench;
import com.github.fabienrenaud.jjb.JsonUtils;
import com.github.fabienrenaud.jjb.data.JsonSource;
import io.github.wycst.wast.json.options.WriteOption;
import okio.BufferedSink;
import okio.Okio;
import org.openjdk.jmh.annotations.Benchmark;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.nio.charset.StandardCharsets;

public class Serialization extends JsonBench {
    public JsonSource JSON_SOURCE() {
        return CLI_JSON_SOURCE;
    }

    @Benchmark
    @Override
    public Object gson() {
        return JSON_SOURCE().provider().gson().toJson(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object jackson() throws Exception {
   	  return JSON_SOURCE().provider().jackson().writeValueAsString(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object jackson_afterburner() throws Exception {
        return JSON_SOURCE().provider().jacksonAfterburner().writeValueAsString(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object jackson_blackbird() throws Exception {
        return JSON_SOURCE().provider().jacksonBlackbird().writeValueAsString(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object genson() {
        return JSON_SOURCE().provider().genson().serialize(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object yasson() {
        return JSON_SOURCE().provider().yasson().toJson(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object fastjson() throws Exception {
        return JSON.toJSONString(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object fastjson_features() throws Exception {
        return JSON.toJSONString(JSON_SOURCE().nextPojo(), JSON_SOURCE().fastjsonFeatures().writerContext());
    }

    @Benchmark
    @Override
    public Object flexjson() {
        return JSON_SOURCE().provider().flexjsonSer().exclude("*.class").deepSerialize(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object boon() {
        return JSON_SOURCE().provider().boon().writeValueAsString(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object johnzon() {
        return JSON_SOURCE().provider().johnzon().writeObjectAsString(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object jsonsmart() throws Exception {
        StringWriter writer = JsonUtils.stringWriter();
        net.minidev.json.JSONValue.writeJSONString(JSON_SOURCE().nextPojo(), writer);
        return writer.toString();
    }

    @Benchmark
    @Override
    public Object dsljson() throws Exception {
        ByteArrayOutputStream baos = JsonUtils.byteArrayOutputStream();
        JSON_SOURCE().provider().dsljson().serialize(JSON_SOURCE().nextPojo(), baos);
        return baos.toString();
    }

    @Benchmark
    @Override
    public Object dsljson_reflection() throws Exception {
        ByteArrayOutputStream baos = JsonUtils.byteArrayOutputStream();
        JSON_SOURCE().provider().dsljson_reflection().serialize(JSON_SOURCE().nextPojo(), baos);
        return baos.toString();
    }

    @Benchmark
    @Override
    public Object avajejsonb_jackson() {
        return JSON_SOURCE().provider().avajeJsonb_jackson().toJson(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object avajejsonb() {
        return JSON_SOURCE().provider().avajeJsonb_default().toJson(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object logansquare() throws Exception {
        ByteArrayOutputStream baos = JsonUtils.byteArrayOutputStream();
        LoganSquare.serialize(JSON_SOURCE().nextPojo(), baos);
        return baos.toString();
    }

    @Benchmark
    @Override
    public Object jodd() throws Exception {
        return JSON_SOURCE().provider().joddSer().serialize(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object moshi() throws Exception {
        ByteArrayOutputStream baos = JsonUtils.byteArrayOutputStream();
        BufferedSink sink = Okio.buffer(Okio.sink(baos));
        JSON_SOURCE().provider().moshi().toJson(sink, JSON_SOURCE().nextPojo());
        sink.flush();
        return baos.toString();
    }

    @Benchmark
    @Override
    public Object qson() throws Exception {
        return JSON_SOURCE().provider().qson().writeString(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object quickbuf_json() throws Exception {
        return JSON_SOURCE().provider().quickbufSink().clear().writeMessage(JSON_SOURCE().nextQuickbufPojo());
    }

    @Benchmark
    @Override
    public Object wast() throws Exception {
        StringWriter writer = JsonUtils.stringWriter();
        io.github.wycst.wast.json.JSON.writeJsonTo(JSON_SOURCE().nextPojo(), writer);
        return writer.toString();
    }

    @Benchmark
    @Override
    public Object libgdx_Json() throws Exception {
        return JSON_SOURCE().provider().libgdx_Json().toJson(JSON_SOURCE().nextPojo());
    }
}
