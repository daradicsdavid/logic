package flow;

import nodes.*;
import nodes.base.AbstractGate;
import nodes.base.Receiver;
import nodes.base.Sender;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;
import java.util.function.Consumer;
import java.util.stream.Stream;

public class Circuit {
    private Map<String, Source> sources;
    private Map<String, Led> leds;
    private Map<String, AbstractGate> gates;

    public Circuit(String path) {
        try (Stream<String> stream = Files.lines(Paths.get(path))) {
            stream.forEach(line -> {
                if (line.startsWith("NODE")) {
                    createNode(line.split(" "));
                } else if (line.startsWith("WIRE")) {
                    connectWire(line.split(" "));
                }
            });

        } catch (IOException e) {

        }
    }

    private void connectWire(String[] params) {
        String senderName = params[1];
        int outputIndex = Integer.parseInt(params[2]);
        String receiverName = params[3];
        int inputIndex = Integer.parseInt(params[4]);

        Source source = getSource(senderName);
        Receiver receiver = getReceiver(receiverName);

        Wire.connect(source, outputIndex, receiver, inputIndex);
    }

    private void createNode(String[] params) {
        if (params.length == 3) {
            String type = params[1];
            String name = params[2];
            if (getSource(name) == null && getLed(name) == null && getGate(name) == null) {
                switch (type) {
                    case "AND":
                        gates.put(name, new And());
                        break;
                    case "NOT":
                        gates.put(name, new Not());
                        break;
                    case "JUNCTION":
                        gates.put(name, new Junction());
                        break;
                    case "SOURCE":
                        sources.put(name, new Source());
                        break;
                    case "LED":
                        leds.put(name, new Led());
                        break;
                }
            }
        }
    }

    public Source getSource(String name) {
        return sources.get(name);
    }

    public Led getLed(String name) {
        return leds.get(name);
    }

    public AbstractGate getGate(String name) {
        return gates.get(name);
    }

    public Sender getSender(String name) {
        return sources.get(name) != null ? sources.get(name) : gates.get(name);
    }

    public Receiver getReceiver(String name) {
        return leds.get(name) != null ? leds.get(name) : gates.get(name);
    }


}
