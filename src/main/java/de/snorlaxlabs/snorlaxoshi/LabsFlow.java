package de.snorlaxlabs.snorlaxoshi;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import oshi.SystemInfo;
import oshi.hardware.Baseboard;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.NetworkParams;
import oshi.software.os.OperatingSystem;

@SpringBootApplication
public class LabsFlow {

    private static SystemInfo sys = new SystemInfo();
    private static HardwareAbstractionLayer abstractionLayer;
    private static CentralProcessor cpu;
    private static GlobalMemory memory;
    private static Baseboard motherboard;
    private static OperatingSystem operatingSystem;
    private static NetworkParams networkParams;

    public static void main(String[] args) {
        SpringApplication.run(LabsFlow.class, args);
         abstractionLayer = sys.getHardware();
         cpu = abstractionLayer.getProcessor();
         memory = abstractionLayer.getMemory();
         motherboard = abstractionLayer.getComputerSystem().getBaseboard();
         operatingSystem = sys.getOperatingSystem();
         networkParams = sys.getOperatingSystem().getNetworkParams();

        MetricsSocket socket = new MetricsSocket();
        socket.sendMetrics();
    }

    public static GlobalMemory getMemory() {
        return memory;
    }

    public static CentralProcessor getCpu() {
        return cpu;
    }

    public static HardwareAbstractionLayer getAbstractionLayer() {
        return abstractionLayer;
    }

    public static SystemInfo getSys() {
        return sys;
    }

    public static Baseboard getMotherboard() {
        return motherboard;
    }

    public static OperatingSystem getOperatingSystem() {
        return operatingSystem;
    }

    public static NetworkParams getNetworkParams() {
        return networkParams;
    }
}
