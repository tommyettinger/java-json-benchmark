package com.github.fabienrenaud.jjb.stream;

import java.io.ByteArrayOutputStream;
import java.io.OutputStreamWriter;
import java.io.StringWriter;
import java.io.Writer;

import com.badlogic.gdx.utils.JsonString;
import com.badlogic.gdx.utils.JsonWriter.OutputType;
import com.cedarsoftware.io.JsonIo;
import com.cedarsoftware.io.WriteOptionsBuilder;
import org.openjdk.jmh.annotations.Benchmark;

import com.fasterxml.jackson.core.JsonGenerator;
import com.github.fabienrenaud.jjb.JsonBench;
import com.github.fabienrenaud.jjb.JsonUtils;
import com.github.fabienrenaud.jjb.data.JsonSource;
import com.grack.nanojson.JsonAppendableWriter;
import com.owlike.genson.stream.ObjectWriter;

import io.github.senthilganeshs.parser.json.Generator;
import io.github.senthilganeshs.parser.json.Parser.Value;
import okio.BufferedSink;
import okio.Okio;

/**
 * @author Fabien Renaud
 */
public class Serialization extends JsonBench {

    public JsonSource JSON_SOURCE() {
        return CLI_JSON_SOURCE;
    }

    @Benchmark
    @Override
    public Object orgjson() throws Exception {
        return JSON_SOURCE().streamSerializer().orgjson(JSON_SOURCE().nextPojo()).toString();
    }

    @Benchmark
    @Override
    public Object jakartajson() throws Exception {
        StringWriter writer = JsonUtils.stringWriter();
        try (jakarta.json.stream.JsonGenerator jGenerator = JSON_SOURCE().provider().jakartajsonFactory().createGenerator(writer)) {
            JSON_SOURCE().streamSerializer().jakartajson(jGenerator, JSON_SOURCE().nextPojo());
        }
        return writer.toString();
    }

    @Benchmark
    @Override
    public Object jackson() throws Exception {
        StringWriter writer = JsonUtils.stringWriter();
        try (JsonGenerator jGenerator = JSON_SOURCE().provider().jacksonFactory().createGenerator(writer)) {
            JSON_SOURCE().streamSerializer().jackson(jGenerator, JSON_SOURCE().nextPojo());
        }
        return writer.toString();
    }

    @Benchmark
    @Override
    public Object avajejsonb_jackson() throws Exception {
        StringWriter writer = JsonUtils.stringWriter();
        JSON_SOURCE().provider().avajeJsonb_jackson().toJson(JSON_SOURCE().nextPojo(), writer);
        return writer.toString();
    }

    @Benchmark
    @Override
    public Object avajejsonb() throws Exception {
        StringWriter writer = JsonUtils.stringWriter();
        JSON_SOURCE().provider().avajeJsonb_default().toJson(JSON_SOURCE().nextPojo(), writer);
        return writer.toString();
    }

    @Benchmark
    @Override
    public Object gson() throws Exception {
        StringWriter writer = JsonUtils.stringWriter();
        try (com.google.gson.stream.JsonWriter jw = new com.google.gson.stream.JsonWriter(writer)) {
            JSON_SOURCE().streamSerializer().gson(jw, JSON_SOURCE().nextPojo());
        }
        return writer.toString();
    }

    @Benchmark
    @Override
    public Object genson() throws Exception {
        StringWriter writer = JsonUtils.stringWriter();
        ObjectWriter ow = JSON_SOURCE().provider().genson().createWriter(writer);
        JSON_SOURCE().streamSerializer().genson(ow, JSON_SOURCE().nextPojo());
        ow.close();
        return writer.toString();
    }

    @Benchmark
    @Override
    public Object jsonio() {
        // showTypeInfoNever maps to old TYPE=false behavior see {@link JsonIo#getWriteOptionsBuilder(java.util.Map)}
        return JsonIo.toJson(JSON_SOURCE().nextPojo(), new WriteOptionsBuilder().showTypeInfoNever().build());
    }

    @Benchmark
    @Override
    public Object jsonsimple() throws Exception {
        org.json.simple.JSONObject jso = JSON_SOURCE().streamSerializer().jsonsimple(JSON_SOURCE().nextPojo());

        StringWriter writer = JsonUtils.stringWriter();
        org.json.simple.JSONValue.writeJSONString(jso, writer);
        return writer.toString();
    }

    @Benchmark
    @Override
    public Object nanojson() throws Exception {
        StringWriter w = JsonUtils.stringWriter();
        JsonAppendableWriter writer = com.grack.nanojson.JsonWriter.on(w);
        JSON_SOURCE().streamSerializer().nanojson(writer, JSON_SOURCE().nextPojo());
        writer.done();
        return w.toString();
    }

    @Benchmark
    @Override
    public Object tapestry() throws Exception {
        return JSON_SOURCE().streamSerializer().tapestry(JSON_SOURCE().nextPojo()).toString();
    }

    @Benchmark
    @Override
    public Object minimaljson() throws Exception {
        StringWriter writer = JsonUtils.stringWriter();
        JSON_SOURCE().streamSerializer().minimaljson(JSON_SOURCE().nextPojo()).writeTo(writer);
        return writer.toString();
    }

    @Benchmark
    @Override
    public Object moshi() throws Exception {
        ByteArrayOutputStream baos = JsonUtils.byteArrayOutputStream();
        BufferedSink sink = Okio.buffer(Okio.sink(baos));
        try (com.squareup.moshi.JsonWriter jw = com.squareup.moshi.JsonWriter.of(sink)) {
            JSON_SOURCE().streamSerializer().moshi(jw, JSON_SOURCE().nextPojo());
        }
        sink.close();
        return baos.toString();
    }

    @Benchmark
    @Override
    public Object mjson() throws Exception {
        return JSON_SOURCE().streamSerializer().mjson(JSON_SOURCE().nextPojo()).toString();
    }

    @Benchmark
    @Override
    public Object underscore_java() throws Exception {
        return JSON_SOURCE().streamSerializer().underscore_java(JSON_SOURCE().nextPojo());
    }

    @Benchmark
    @Override
    public Object purejson() throws Exception {
        Value purejson = JSON_SOURCE().streamSerializer().purejson(JSON_SOURCE().nextPojo());
        return Generator.create().generate(purejson);
    }

    @Benchmark
    @Override
    public Object antons() throws Exception {
        sk.antons.json.JsonValue jso = JSON_SOURCE().streamSerializer().antons(JSON_SOURCE().nextPojo());

        StringWriter writer = JsonUtils.stringWriter();
        jso.writeCompact(writer);
        return writer.toString();
    }

    @Benchmark
    @Override
    public Object libgdx_JsonValue () throws Exception {
        return JSON_SOURCE().streamSerializer().libgdx_JsonValue(JSON_SOURCE().nextPojo()).toJson(OutputType.json);
    }

    @Benchmark
    @Override
    public Object libgdx_JsonString () throws Exception {
        JsonString writer = JSON_SOURCE().provider().libgdx_JsonString();
        JSON_SOURCE().streamSerializer().libgdx_JsonString(writer, JSON_SOURCE().nextPojo());
        return writer.toString();
    }

    @Benchmark
    @Override
    public Object libgdx_JsonWriter () throws Exception {
        StringWriter output = JsonUtils.stringWriter();
        // TODO: Use this after next libgdx build.
        // com.badlogic.gdx.utils.JsonWriter writer = JSON_SOURCE().provider().libgdx_JsonWriter();
        // writer.setWriter(output);
        com.badlogic.gdx.utils.JsonWriter writer = new com.badlogic.gdx.utils.JsonWriter(output);
        JSON_SOURCE().streamSerializer().libgdx_JsonWriter(writer, JSON_SOURCE().nextPojo());
        writer.close();
        return output.toString();
    }
}
