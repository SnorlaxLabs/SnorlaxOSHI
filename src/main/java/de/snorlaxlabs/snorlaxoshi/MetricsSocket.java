package de.snorlaxlabs.snorlaxoshi;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import oshi.SystemInfo;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/metrics")
public class MetricsSocket {

    private final SystemInfo systemInfo = LabsFlow.getSys();

    @GetMapping("")
    public List<Map<String, Object>> sendMetrics() {
        var hw = systemInfo.getHardware();
        var cpu = hw.getProcessor();
        var mem = hw.getMemory();

        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        return List.of(
                Map.of(
                        "firmwareVersion", LabsFlow.getOperatingSystem().getVersionInfo(),
                        "firmwareManifacturer", LabsFlow.getOperatingSystem().getManufacturer(),
                        "sysProcessCount", LabsFlow.getOperatingSystem().getProcessCount(),
                        "netDnsServers", LabsFlow.getNetworkParams().getDnsServers(),
                        "netDomain", LabsFlow.getNetworkParams().getDomainName(),
                        "netHost", LabsFlow.getNetworkParams().getHostName(),
                        "netIpv4Gate", LabsFlow.getNetworkParams().getIpv4DefaultGateway(),
                        "netIpv6Gate", LabsFlow.getNetworkParams().getIpv6DefaultGateway(),
                        "netIpv4ConStats", LabsFlow.getOperatingSystem().getInternetProtocolStats().getTCPv4Stats()
                ),
                Map.of(
                        "cpuLoad", cpu.getSystemCpuLoad(1000) * 100,
                        "baseboardModel", LabsFlow.getMotherboard().getModel(),
                        "baseboardVersion", LabsFlow.getMotherboard().getVersion(),
                        "baseboardSerialNumber", LabsFlow.getMotherboard().getSerialNumber(),
                        "cpuMaxFreq", LabsFlow.getCpu().getMaxFreq(),
                        "cpuCurrentFreq", LabsFlow.getCpu().getCurrentFreq()[0],
                        "cpuIdentifier", LabsFlow.getCpu().getProcessorIdentifier(),
                        "ramUsedGB", (mem.getTotal() - mem.getAvailable()) / (1024.00 * 1024.00 * 1024.00),
                        "ramMaxGB", mem.getTotal() / (1024.00 * 1024.00 * 1024.00),
                        "ramAvailableGB", mem.getAvailable() / (1024.00 * 1024.00 * 1024.00)
                        ),
                Map.of(
                        "timestamp", dateTime.format(dateFormatter),
                        "status", 505,
                        "error", "none"
                )
        );
    }
}

